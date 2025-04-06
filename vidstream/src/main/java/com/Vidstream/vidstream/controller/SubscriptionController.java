package com.Vidstream.vidstream.controller;

import com.Vidstream.vidstream.dto.SubscriptionDTO;
import com.Vidstream.vidstream.model.Subscription;
import com.Vidstream.vidstream.service.SubscriptionService;
import com.Vidstream.vidstream.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@CrossOrigin(origins = "http://localhost:3000")

@RestController
@RequestMapping("/plan")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/plus")
    public ResponseEntity<String> plusPlan(HttpServletRequest request) {
        return purchasePlan(request, Subscription.plan_type.Plus, BigDecimal.valueOf(100));
    }

    @PostMapping("/premium")
    public ResponseEntity<String> premiumPlan(HttpServletRequest request) {
        return purchasePlan(request, Subscription.plan_type.Premium, BigDecimal.valueOf(150));
    }

    private ResponseEntity<String> purchasePlan(HttpServletRequest request, Subscription.plan_type planType, BigDecimal amount) {
        String token = jwtUtil.extractToken(request);
        Long userId = jwtUtil.extractUserId(token);
        SubscriptionDTO dto = new SubscriptionDTO(userId, planType, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now().plusMonths(1)));
        subscriptionService.subscription(dto, amount);
        return new ResponseEntity<>("Purchase done", HttpStatus.OK);
    }
}
