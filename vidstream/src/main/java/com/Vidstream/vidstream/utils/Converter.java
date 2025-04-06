package com.Vidstream.vidstream.utils;

import com.Vidstream.vidstream.dto.RateDTO;
import com.Vidstream.vidstream.dto.SubscriptionDTO;
import com.Vidstream.vidstream.dto.UserDTO;
import com.Vidstream.vidstream.dto.VideoDTO;
import com.Vidstream.vidstream.model.Rate;
import com.Vidstream.vidstream.model.Subscription;
import com.Vidstream.vidstream.model.Users;
import com.Vidstream.vidstream.model.Video;
import org.springframework.beans.factory.support.CglibSubclassingInstantiationStrategy;
import org.springframework.stereotype.Component;

@Component
public class Converter {
    public Users DTOtoUser(UserDTO dto) {
        Users model = new Users();
        model.setFullName(dto.getFullName());
        model.setUsername(dto.getUsername());
        model.setEmail(dto.getEmail());
        model.setPassword(dto.getPassword());
        return model;
    }

    public VideoDTO videoToDTO(Video model) {
        VideoDTO dto = new VideoDTO();
        dto.setId(model.getId());
        dto.setTitle(model.getTitle());
        dto.setPath(model.getPath());
        dto.setDescription(model.getDescription());
        dto.setUploader_id(model.getUploaderId());
        dto.setScreenCap(model.getScreenCap());
        return dto;
    }

    public Video DTOtoVideo(VideoDTO dto) {
        Video model = new Video();
        model.setTitle(dto.getTitle());
        model.setDescription(dto.getDescription());
        model.setPath(dto.getPath());
        model.setUploaderId(dto.getUploader_id());
        model.setScreenCap(dto.getScreenCap());
        return model;
    }

    public Rate dtoToRate(RateDTO dto) {
        Rate model = new Rate();
        model.setVideoId(dto.getVideoId());
        model.setUserId(dto.getUserId());
        model.setComments(dto.getComments());
        model.setRateValue(dto.getRateValue());
        return model;
    }

    public RateDTO rateToDTO(Rate model) {
        RateDTO dto = new RateDTO();
        dto.setId(model.getId());
        dto.setVideoId(model.getVideoId());
        dto.setRateValue(model.getRateValue());
        dto.setUserId(model.getUserId());
        dto.setComments(model.getComments());
        return dto;
    }

    public Subscription dtoToSubscription(SubscriptionDTO dto) {
        Subscription model = new Subscription();
        model.setUser_id(dto.getUser_id());
        model.setPlan(dto.getPlan());
        model.setStartDate(dto.getStartDate());
        model.setEndDate(dto.getEndDate());
        return model;
    }

}
