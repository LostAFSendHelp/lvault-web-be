package com.example.lostaf.lvaultweb.configs.security;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KeycloakLogoutHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logoutFromKeyCloak((OidcUser) authentication.getPrincipal());
    }
    
    private void logoutFromKeyCloak(OidcUser user) {
        var logoutEndpoint = user.getIssuer() + "/protocol/openid-connect/logout";
        var client = WebClient.builder()
            .baseUrl(logoutEndpoint)
            .build();
        
        var response = client.get()
            .uri(uri -> uri.queryParam("id_token_hint", user.getIdToken()).build())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class)
            .block();
        
        log.info(response);
    }
}
