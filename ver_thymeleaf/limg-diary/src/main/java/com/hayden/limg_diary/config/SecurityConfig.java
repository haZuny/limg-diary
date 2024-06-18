package com.hayden.limg_diary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests((auth)->{
            auth
                    .requestMatchers("/", "/signup", "/signin").permitAll()
                    .requestMatchers("/today", "/diary/write", "/resource/diaryimg/**").hasRole("USER");
        });

        // Custom login
        httpSecurity.formLogin((auth)->{
            auth.loginPage("/signin")
                    .loginProcessingUrl("/signin")
                    .defaultSuccessUrl("/today", true)
                    .permitAll();
        });

        httpSecurity.csrf((auth)->auth.disable());

        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
