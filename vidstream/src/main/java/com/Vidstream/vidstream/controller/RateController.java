package com.Vidstream.vidstream.controller;

import com.Vidstream.vidstream.dto.RateDTO;
import com.Vidstream.vidstream.service.RateService;
import com.Vidstream.vidstream.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("rate")
public class RateController {

    @Autowired
    private RateService rateService;
    @Autowired
    private JwtUtil jwtUtil;

    @PreAuthorize("hasRole('PREMIUM')")
    @PostMapping("/set")
    public ResponseEntity<String> setRate(@RequestBody RateDTO dto, HttpServletRequest request) {
        String token = jwtUtil.extractToken(request);
        Long userId = jwtUtil.extractUserId(token);
        dto.setUserId(userId);
        rateService.setRate(dto);
        return new ResponseEntity<>("Video rated successfully", HttpStatus.OK);
    }


    @PreAuthorize("hasRole('PREMIUM')")
    @GetMapping("/get")
    public ResponseEntity<List<RateDTO>> getRatesByVideo(@RequestParam Long videoId) {
        List<RateDTO> response = rateService.getRate(videoId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}