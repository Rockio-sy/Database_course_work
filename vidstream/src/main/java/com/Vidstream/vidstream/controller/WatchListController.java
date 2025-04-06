package com.Vidstream.vidstream.controller;

import com.Vidstream.vidstream.dto.VideoDTO;
import com.Vidstream.vidstream.service.WatchListService;
import com.Vidstream.vidstream.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/wl")
public class WatchListController {
    @Autowired
    private WatchListService watchListService;
    @Autowired
    private JwtUtil jwtUtil;

    @PreAuthorize("hasAnyRole('PLUS', 'PREMIUM')")
    @PostMapping("/add")
    public ResponseEntity<String> addToWatchList(@RequestParam @Valid Long videoId, HttpServletRequest request) {
        String token = jwtUtil.extractToken(request);
        Long userId = jwtUtil.extractUserId(token);
        watchListService.addToWatchList(videoId, userId);
        return new ResponseEntity<>("Video added successfully to the Watchlist", HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('PLUS', 'PREMIUM')")
    @GetMapping("/get")
    public ResponseEntity<List<VideoDTO>> getWatchListVideos(HttpServletRequest request) {
        String token = jwtUtil.extractToken(request);
        Long userId = jwtUtil.extractUserId(token);
        List<VideoDTO> response = watchListService.getWatchListVideos(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('PLUS', 'PREMIUM')")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteVideoFromWatchList(@Valid Long videoId, HttpServletRequest request) {
        String token = jwtUtil.extractToken(request);
        Long userId = jwtUtil.extractUserId(token);
        watchListService.deleteFromWatchList(userId, videoId);
        return ResponseEntity.ok().build();
    }
}
