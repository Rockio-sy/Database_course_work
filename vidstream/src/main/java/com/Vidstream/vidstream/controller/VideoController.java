package com.Vidstream.vidstream.controller;

import com.Vidstream.vidstream.dto.VideoDTO;
import com.Vidstream.vidstream.exceptions.CustomException;
import com.Vidstream.vidstream.service.VideoService;
import com.Vidstream.vidstream.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadVideoFile(
            @RequestParam("file") MultipartFile videoFile,
            HttpServletRequest request) {
        // Extract and validate the JWT
        String token = jwtUtil.extractToken(request);
        Long userId = jwtUtil.extractUserId(token);

        // Save the video file
        String generatedPath = videoService.saveTempFile(videoFile, userId);
        return new ResponseEntity<>(generatedPath, HttpStatus.CREATED);
    }

    // Create new post, take the Video path from the local storage
    @PostMapping("/post")
    public ResponseEntity<VideoDTO> postVideo(
            @RequestBody VideoDTO videoDTO,
            HttpServletRequest request) {
        // Extract and validate the JWT
        String token = jwtUtil.extractToken(request);
        Long uploaderId = jwtUtil.extractUserId(token);
        videoDTO.setUploader_id(uploaderId);
        // Post the video
        videoDTO.setScreenCap("/var/project_videos/screenCast.png");
        VideoDTO response = videoService.post(videoDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // A button to clear temp videos from non-completed post video request
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearTempVideo(
            HttpServletRequest request) {
        // Extract and validate the JWT
        String token = jwtUtil.extractToken(request);
        Long userId = jwtUtil.extractUserId(token);

        // Clear temp files
        videoService.clearTempFile(userId);
        return new ResponseEntity<>("Temp video is cleared", HttpStatus.OK);
    }

    @GetMapping("/thumbnail")
    public ResponseEntity<Resource> getThumbnail() throws IOException {
        // Construct the file path
        Path filePath = Paths.get("/var/project_videos/screenCast.png").normalize();
        // Check if the file exists
        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }

        // Load the file as a resource
        Resource resource = new UrlResource(filePath.toUri());

        // Return the file with the appropriate content type
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG) // Adjust based on your image type
                .body(resource);
    }

    // Get all videos in the database
    @GetMapping("/all")
    public ResponseEntity<List<VideoDTO>> getAllVideos(HttpServletRequest request) {


        // Fetch all videos
        List<VideoDTO> videos = videoService.getAllVideos();
        return new ResponseEntity<>(videos, HttpStatus.OK);
    }

    // To search for video by title
    @GetMapping("/by-title")
    public ResponseEntity<List<VideoDTO>> searchByTitle(
            @RequestParam @NotBlank String title,
            HttpServletRequest request) {
        // Extract and validate the JWT
        String token = jwtUtil.extractToken(request);

        // Search for videos by title
        List<VideoDTO> response = videoService.searchByTitle(title);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // After clicking on a video, the front should take the id of it, and open it in another page to play the video
    @GetMapping("/stream")
    public ResponseEntity<Resource> streamVideo(
            @RequestParam @Valid @Positive Long videoId,
            HttpServletRequest request) {

        // Stream the video
        Resource resource = videoService.streamVideo(videoId);

        // Handle range requests
        String rangeHeader = request.getHeader("Range");
        if (rangeHeader != null) {
            return handleRangeRequest(resource, rangeHeader);
        }

        // Return the full video if no range header is present
        return ResponseEntity.ok()
                .header("Content-Type", "video/mp4")
                .body(resource);
    }

    private ResponseEntity<Resource> handleRangeRequest(Resource resource, String rangeHeader) {
        try {
            long fileSize = resource.contentLength();
            long rangeStart = 0;
            long rangeEnd = fileSize - 1;

            // Parse the range header
            if (rangeHeader.startsWith("bytes=")) {
                String[] ranges = rangeHeader.substring(6).split("-");
                rangeStart = Long.parseLong(ranges[0]);
                if (ranges.length > 1) {
                    rangeEnd = Long.parseLong(ranges[1]);
                }
            }

            // Validate the range
            if (rangeStart >= fileSize || rangeEnd >= fileSize) {
                return ResponseEntity.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE)
                        .header("Content-Range", "bytes */" + fileSize)
                        .build();
            }

            // Return partial content
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .header("Content-Type", "video/mp4")
                    .header("Content-Length", String.valueOf(rangeEnd - rangeStart + 1))
                    .header("Content-Range", "bytes " + rangeStart + "-" + rangeEnd + "/" + fileSize)
                    .body(new InputStreamResource(resource.getInputStream()));
        } catch (IOException e) {
            throw new CustomException("Couldn't load video", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}