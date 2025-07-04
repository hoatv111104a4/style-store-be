package com.example.style_store_be.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SercurityConfig {
    private CustomJwtDecoder customJwtDecoder;
    private final String [] PUBLIC_ENDPOINTS = {
            "/auth/dang-nhap",
            "/auth/dang-xuat",
            "/auth/introspect",
            "/nguoi-dung/dang-ky",
            "/website/san-pham/**",
            "/api/upload",
            "/don-hang/dat-hang-online-chua-thanh-toan",
            "/api/admin-san-pham-chi-tiet/**",
            "/api/thuong-hieu/**",
            "/api/xuat-xu/**",
            "/api/admin-san-pham/**",
            "/api/mau-sac/**",
            "/api/kich-thuoc/**",
            "/api/hinh-anh-mau-sac/**",
            "/api/chat-lieu/**"

    };


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http.cors()
                .and()
                .csrf().disable()
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.GET, PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.PUT, PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers("/uploads/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtConfigurer -> jwtConfigurer
                                .decoder(customJwtDecoder)
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                );
        return http.build();
    }

    JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    //    @Bean
//    JwtDecoder jwtDecoder(){
//        SecretKeySpec  secretKeySpec = new SecretKeySpec(signerKey.getBytes(),"HS512");
//        return NimbusJwtDecoder
//                .withSecretKey(secretKeySpec)
//                .macAlgorithm(MacAlgorithm.HS512)
//                .build();
//    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
}
