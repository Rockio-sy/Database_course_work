package com.Vidstream.vidstream.ApiRequest;

import com.Vidstream.vidstream.model.ApiRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class Requester {
    @Autowired
    private WebClient webClient;

    public Mono<Integer> getStatusCodeFromSecondApi(ApiRequest request) {
        return webClient.post()
                .uri("/transfer")
                .bodyValue(request)
                .exchangeToMono(response -> {
                    HttpStatus statusCode = (HttpStatus) response.statusCode();
                    return Mono.just(statusCode.value()); // Return the status code as an integer
                })
                .onErrorResume(e -> Mono.just(500)); // Handle errors by returning 500
    }
}
