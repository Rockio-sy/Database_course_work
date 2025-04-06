package com.Vidstream.vidstream.service;

import com.Vidstream.vidstream.dto.VideoDTO;
import com.Vidstream.vidstream.exceptions.CustomException;
import com.Vidstream.vidstream.model.Video;
import com.Vidstream.vidstream.repository.VideoRepository;
import com.Vidstream.vidstream.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VideoService {
    // Move it to ENV
    private final String DIRECTORY_PATH = "/var/project_videos";
    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private Converter converter;


    public String saveTempFile(MultipartFile videoFile, Long userId) {
        // Check content type
        checkContentType(videoFile);

        // Get Directory Path
        Path directoryPath = getStoragePath();

        // Rename the file
        String newName = renameTempFile(videoFile, userId);

        // Set the path for new temp file
        Path tempFilePath = directoryPath.resolve(newName);

        try {
            // Check if the user already has a TEMP file
            Long id = videoRepository.isExistTempPath(userId);
            if (id != null) {
                throw new CustomException("User already has a TEMP file saved", HttpStatus.FORBIDDEN);
            }

            // Copy file to temp location
            Files.copy(videoFile.getInputStream(), tempFilePath, StandardCopyOption.REPLACE_EXISTING);

            // Save file path to database
            boolean savedInDB = videoRepository.saveTempFile(tempFilePath.toString(), userId);
            if (!savedInDB) {
                // Rollback file if database save fails
                safeDelete(tempFilePath);
                throw new CustomException("Failed to save in the database, file copied and deleted", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return tempFilePath.toString();

        } catch (IOException e) {
            throw new CustomException("Error copying the file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            safeDelete(tempFilePath);
            throw new CustomException("Unexpected error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void safeDelete(Path filePath) {
        try {
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
        } catch (IOException deleteEx) {
            System.out.println("Error deleting the file after copy.");
        }
    }

    public VideoDTO post(VideoDTO videoDTO) {
        Path tempFilePath = Paths.get(videoDTO.getPath());  // Get the path from DTO

        // Ensure that the file exists
        if (!Files.exists(tempFilePath)) {
            throw new CustomException("Temporary file not found", HttpStatus.NOT_FOUND);
        }

        String finalFileName = videoDTO.getUploader_id() + "_" + videoDTO.getTitle() + ".mp4";

        // Get the final storage directory
        Path finalStoragePath = getStoragePath();

        // Define the final path to save the file
        Path newPath = finalStoragePath.resolve(finalFileName);

        try {
            // Rename and move the file to the final destination
            Files.move(tempFilePath, newPath, StandardCopyOption.REPLACE_EXISTING);
            videoDTO.setScreenCap("/var/project_videos/screenCast.png");
            if (videoRepository.savePost(videoDTO, newPath.toString())) {
                Long videoId = videoRepository.getVideoId(newPath.toString(), videoDTO.getUploader_id());
                videoDTO.setPath(newPath.toString());
                videoDTO.setId(videoId);
            } else {
                throw new CustomException("Couldn't update the video file", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return videoDTO;
        } catch (IOException e) {
            throw new CustomException("Error moving the file to final destination", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (SQLException e) {
            throw new CustomException("SQL error\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public void clearTempFile(Long userId) {
        String prefix = "TEMP_" + userId + "_";
        Path directory = Paths.get(DIRECTORY_PATH);

        try {
            videoRepository.cleanUserTemp(userId);

            File[] files = directory.toFile().listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().startsWith(prefix)) {
                        Files.delete(file.toPath());
                    }
                }
            } else {
                throw new CustomException("File not found", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            throw new CustomException("Couldn't delete the temp file :\n" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private String renameTempFile(MultipartFile videoFile, Long userId) {
        String originalFileName = videoFile.getOriginalFilename();
        return "TEMP_" + userId + "_" + originalFileName;
    }

    private Path getStoragePath() {
        try {
            Path storagePath = Paths.get(DIRECTORY_PATH);
            if (!Files.exists(storagePath)) {
                Files.createDirectories(storagePath);
            }
            return storagePath;
        } catch (IOException e) {
            throw new CustomException("PATH NOT FOUND" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void checkContentType(MultipartFile videoFile) {
        String contentType = videoFile.getContentType();
        if (!(contentType.equals("video/mp4"))) {
            throw new CustomException("Unsupported video format, only MP4 allowed.", HttpStatus.FORBIDDEN);
        }
    }



    public List<VideoDTO> getAllVideos() {
        List<Video> models = videoRepository.getAllVideos();
        if (models.isEmpty()) {
            throw new CustomException("No videos at the current time", HttpStatus.NO_CONTENT);
        }

        List<VideoDTO> dtoS = new ArrayList<>();
        for (Video v : models) {
            VideoDTO dto = converter.videoToDTO(v);
            dtoS.add(dto);
        }
        return dtoS;
    }

    public List<VideoDTO> searchByTitle(String title) {
        List<Video> models = videoRepository.getByTitle(title);
        if (models.isEmpty()) {
            throw new CustomException("No content", HttpStatus.NOT_FOUND);
        }
        List<VideoDTO> dtoS = new ArrayList<>();
        for (Video v : models) {
            dtoS.add(converter.videoToDTO(v));
        }
        return dtoS;
    }

    public Resource streamVideo(Long videoId) {
        // Fetch the video from the repository
        Optional<Video> model = videoRepository.getById(videoId);
        if (model.isEmpty()) {
            throw new CustomException("Video not available", HttpStatus.NOT_FOUND);
        }

        // Get the video path
        String filePath = model.get().getPath();
        if (filePath == null) {
            throw new CustomException("Video not available", HttpStatus.NOT_FOUND);
        }

        // Normalize the path and check if the file exists
        Path videoPath = Paths.get(filePath).normalize();
        if (!Files.exists(videoPath)) {
            throw new CustomException("Video not available", HttpStatus.NOT_FOUND);
        }

        // Load the video as a resource
        try {
            Resource resource = new UrlResource(videoPath.toUri());
            if (!resource.isReadable()) {
                throw new CustomException("Couldn't load video", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return resource;
        } catch (MalformedURLException e) {
            throw new CustomException("Couldn't load video", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public VideoDTO getById(Long id) {
        Optional<Video> model = videoRepository.getById(id);
        if (model.isPresent()) {
            return converter.videoToDTO(model.get());
        } else {
            throw new CustomException("Video file not found", HttpStatus.NOT_FOUND);
        }
    }
}


