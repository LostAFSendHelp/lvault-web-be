package com.example.lostaf.lvaultweb.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.lostaf.lvaultweb.controllers.requests.OAuth2Request;
import com.example.lostaf.lvaultweb.models.oauth.GoogleOAuth2Result;
import com.example.lostaf.lvaultweb.services.OAuth2Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
public class AuthController {
    @Autowired
    private OAuth2Service oAuth2Service;

    @PostMapping("/login/oauth2/google/code")
    public String postMethodName(@RequestBody OAuth2Request request) {
        String code = request.getAuthCode();

        log.info(code);

        WebClient client = WebClient.builder()
            .baseUrl("https://oauth2.googleapis.com/token")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

        GoogleOAuth2Result result = client
            .method(HttpMethod.POST)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(new HashMap<String, String>() {{
                put("code", code);
                put("client_id", oAuth2Service.getGoogleClientId());
                put("client_secret", oAuth2Service.getGoogleClientSecret());
                put("grant_type", "authorization_code");
                put("redirect_uri", "postmessage");
            }})
            .retrieve()
            .bodyToMono(GoogleOAuth2Result.class)
            .onErrorResume(
                e -> e instanceof WebClientResponseException,
                e -> {
                    WebClientResponseException exc = (WebClientResponseException) e;
                    log.warn("[mine] {}", exc.getResponseBodyAsString());
                    return Mono.error(e);
                }
            ).block();

        log.info(result.toString());

        return result.toString();
    }
    
}
