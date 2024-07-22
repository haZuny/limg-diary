package com.hayden.limg_diary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {



    // password 암호화
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    // H2 콘솔 무시
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> {
            web.ignoring().requestMatchers("/h2/*");
        };
    }

    // 필터체인 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{


        // CSRF Disable
        http.csrf(AbstractHttpConfigurer::disable);
        
        // UsernamePasswordAuthFilter disable
        http.formLogin(AbstractHttpConfigurer::disable);

        // 기본 로그인창 disable
        http.httpBasic(AbstractHttpConfigurer::disable);

        // Session Stateless
        http.sessionManagement(auth->{
            auth.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        // auth path
        http.authorizeHttpRequests(auth->{
            auth.requestMatchers(
                    "/user/signin", "/user/signup").permitAll();
        });

        return http.build();
    }
}
