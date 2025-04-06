package com.Vidstream.vidstream.controller;

import com.Vidstream.vidstream.dto.UserDTO;
import com.Vidstream.vidstream.exceptions.CustomException;
import com.Vidstream.vidstream.model.MyUserDetails;
import com.Vidstream.vidstream.service.UserService;
import com.Vidstream.vidstream.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "Authorization")
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam(value = "username") @Valid String username,
                                   @RequestParam(value = "password") @Valid String password) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // Get user details
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();

            // Generate JWT token with user ID and other claims
            String jwtToken = jwtUtil.generateToken(userDetails);

            // Return only the token in the response
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + jwtToken) // Include token in the response header
                    .build(); // No need to return user ID in the body
        } catch (Exception e) {
            // Handle authentication errors
            throw new CustomException("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserDTO userDTO) {
        try {
            // Hash the password before saving
            String hashPass = passwordEncoder.encode(userDTO.getPassword());
            userDTO.setPassword(hashPass);

            // Register the user
            UserDTO savedUser = userService.register(userDTO);

            // Return the saved user with a 201 CREATED status
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

        } catch (Exception e) {
            // Handle registration errors
            throw new CustomException("Registration failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}