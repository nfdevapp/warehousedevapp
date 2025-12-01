package org.example.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.AbstractConfiguredSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Value("${app.url}")
//    private String appUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(a -> a
                                .requestMatchers("/api/auth/me").authenticated()
//                        .requestMatchers(HttpMethod.GET, "/api/auth/me").authenticated()
                        .requestMatchers("/api/secured").authenticated()
                        .anyRequest().permitAll()
                )
//                .oauth2Login(o -> o.defaultSuccessUrl(appUrl));
                .oauth2Login(o -> o.defaultSuccessUrl("http://localhost:5173"));
        return http.build();
    }


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(a -> a
//                        .requestMatchers("/api/auth/me").authenticated()
//                        .requestMatchers("/api/secured").authenticated()
////                        .requestMatchers("/api/secured").hasAnyAuthority("USER")
////                        .requestMatchers("/api/secured").hasAnyAuthority("ADMIN")
//                        .anyRequest().permitAll()
//                )
//                .oauth2Login(o -> o.defaultSuccessUrl("http://localhost:5173"));
//        return http.build();
//    }
}

