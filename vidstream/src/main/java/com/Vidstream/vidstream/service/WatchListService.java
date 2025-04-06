package com.Vidstream.vidstream.service;

import com.Vidstream.vidstream.dto.VideoDTO;
import com.Vidstream.vidstream.exceptions.CustomException;
import com.Vidstream.vidstream.model.Video;
import com.Vidstream.vidstream.repository.VideoRepository;
import com.Vidstream.vidstream.repository.WatchListRepository;
import com.Vidstream.vidstream.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WatchListService {
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private WatchListRepository watchListRepository;
    @Autowired
    private Converter converter;

    public void addToWatchList(Long videoId, Long userId) {

        if (watchListRepository.isExists(videoId, userId)) {
            throw new CustomException("Video already existed", HttpStatus.CONFLICT);
        }

        watchListRepository.addVideo(videoId, userId);
    }

    public List<VideoDTO> getWatchListVideos(Long userId) {
        List<Long> ids = watchListRepository.getVideosId(userId);
        List<Video> models = new ArrayList<>();
        for (Long id : ids) {
            Optional<Video> check = videoRepository.getById(id);
            check.ifPresent(models::add);
        }

        List<VideoDTO> dto = new ArrayList<>();
        for (Video v : models) {
            dto.add(converter.videoToDTO(v));
        }

        return dto;
    }

    public void deleteFromWatchList(Long userId, Long videoId) {
        try {
            watchListRepository.deleteFromWatchList(videoId, userId);
        } catch (Exception e) {
            throw new CustomException("Error deleting the video : " + e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
