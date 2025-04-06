package com.Vidstream.vidstream.service;

import com.Vidstream.vidstream.ApiRequest.Requester;
import com.Vidstream.vidstream.dto.SubscriptionDTO;
import com.Vidstream.vidstream.exceptions.CustomException;
import com.Vidstream.vidstream.model.ApiRequest;
import com.Vidstream.vidstream.model.Subscription;
import com.Vidstream.vidstream.repository.PaymentRepository;
import com.Vidstream.vidstream.repository.SubscriptionRepository;
import com.Vidstream.vidstream.repository.UserRepository;
import com.Vidstream.vidstream.utils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private Converter converter;
    @Autowired
    private Requester client;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;
    private static final BigDecimal PLUS_PRICE = new BigDecimal("100.00");
    private static final int BAD_REQUEST_STATUS = 400;
    private static final int INTERNAL_SERVER_ERROR_STATUS = 500;

    private Integer pay(ApiRequest request) {
        try {
            return client.getStatusCodeFromSecondApi(request)
                    .block();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomException("Payment service unavailable", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public void subscription(SubscriptionDTO dto, BigDecimal amount) {
        ApiRequest request = new ApiRequest(3, 4, amount);
        Integer statusCode = pay(request);

        if (BAD_REQUEST_STATUS == statusCode) {
            System.out.println("BAD REQUEST");
            throw new CustomException("Not enough balance", HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (INTERNAL_SERVER_ERROR_STATUS == statusCode) {
            System.out.println("INTERNAL SERVER ERROR");
            throw new CustomException("Cannot process payment", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Subscription model = converter.dtoToSubscription(dto);
        try {
            subscriptionRepository.save(model);
            userRepository.setUserPlan(model.getUser_id(), model.getPlan().name());
            paymentRepository.save(amount, model.getUser_id());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new CustomException("Payment succeeded, but subscription creation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
