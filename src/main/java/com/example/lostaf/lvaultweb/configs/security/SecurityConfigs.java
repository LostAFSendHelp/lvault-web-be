package com.example.lostaf.lvaultweb.configs.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigs {
    private static final String REALM_ACCESS_CLAIM = "realm_access";
    private static final String ROLES_CLAIM = "roles";
    private static final String GROUP_CLAIM = "groups";

    @Autowired
    private KeycloakLogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/login*").permitAll()
            .requestMatchers("/vaults", "vault/*").hasRole("basic_user")
            .anyRequest().authenticated()
        );

        http.oauth2ResourceServer(oauth2 -> oauth2
            .jwt(Customizer.withDefaults())
        );

        http.oauth2Login(Customizer.withDefaults())
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .addLogoutHandler(logoutHandler)
            );

        return http.build();
    }

    @SuppressWarnings("unchecked")
    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapperForKeycloak() {
        return authorities -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            var authority = authorities.iterator().next();
            var isOidcAuthority = authority instanceof OidcUserAuthority;

            if (isOidcAuthority) {
                var oidc = (OidcUserAuthority) authority;
                var userInfo = oidc.getUserInfo();
                if (userInfo.hasClaim(REALM_ACCESS_CLAIM)) {
                    var realmAccess = userInfo.getClaimAsMap(REALM_ACCESS_CLAIM);
                    var roles = (Collection<String>) realmAccess.get(ROLES_CLAIM);
                    mappedAuthorities.addAll(generateAuthoritiesFromClaim(roles));
                } else if (userInfo.hasClaim(GROUP_CLAIM)) {
                    var groups = (Collection<String>) userInfo.getClaim(GROUP_CLAIM);
                    mappedAuthorities.addAll(generateAuthoritiesFromClaim(groups));
                }
            } else {
                var oauth = (OAuth2UserAuthority) authority;
                var attributes = oauth.getAttributes();
                if (attributes.containsKey(REALM_ACCESS_CLAIM)) {
                    var realmAccess = (Map<String, Object>) attributes.get(REALM_ACCESS_CLAIM);
                    var roles = (Collection<String>) realmAccess.get(ROLES_CLAIM);
                    mappedAuthorities.addAll(generateAuthoritiesFromClaim(roles));
                }
            }

            return mappedAuthorities;
        };
    }

    private Collection<GrantedAuthority> generateAuthoritiesFromClaim(Collection<String> roles) {
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
            .collect(Collectors.toList());
    }
}
