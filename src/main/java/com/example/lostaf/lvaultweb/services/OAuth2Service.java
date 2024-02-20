package com.example.lostaf.lvaultweb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

// TODO: check for best practices
@Service
public class OAuth2Service {
    @Autowired
    private Environment env;

    public String getGoogleClientId() {
        return env.getProperty("spring.security.oauth2.client.registration.google.client-id");
    }

    public String getGoogleClientSecret() {
        return env.getProperty("spring.security.oauth2.client.registration.google.client-secret");
    }

    public String getGoogleRedirectUri() {
        return env.getProperty("spring.security.oauth2.client.registration.google.redirect-uri");
    }
}
