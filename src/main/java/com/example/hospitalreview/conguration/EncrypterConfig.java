package com.example.hospitalreview.conguration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class EncrypterConfig {

    // SecurityConfig에 BCryptPasswordEncoder를 넣으면 안된다! -> 나중에 문제가 발생
    @Bean
    public BCryptPasswordEncoder encodePassword(){

        return new BCryptPasswordEncoder();
    }
}
