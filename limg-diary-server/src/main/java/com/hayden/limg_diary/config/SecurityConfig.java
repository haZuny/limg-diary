package com.hayden.limg_diary.config;

import com.hayden.limg_diary.entity.role.RoleRepository;
import com.hayden.limg_diary.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    JwtAuthenticationFilter jwtAuthenticationFilter;
    RoleRepository roleRepository;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, RoleRepository roleRepository) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.roleRepository = roleRepository;
    }

    // password 암호화
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    // H2 콘솔 무시
    @Bean
    @ConditionalOnProperty(name = "spring.h2.console.enabled",havingValue = "true")
    public WebSecurityCustomizer configureH2ConsoleEnable() {
        return web -> web.ignoring()
                .requestMatchers("/h2-console/*");
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
            auth

                    .requestMatchers(
                            "/user/signin", "/user/signup", "/user/refresh", "/user/logout").permitAll()

                    .requestMatchers(
                            "/test"
                            , "/user/modify", "/user/self"
                            , "/challenge/achieved", "/challenge/unachieved"
                            ,"/drawstyle/list"
                            ,"/todayrate/list"
                            , "/diary/add", "/diary/today", "/diary/*", "/diary/month", "/diary/request").hasRole(roleRepository.findByLevel(1).getName())

                    .requestMatchers(
                            "/test/admin"
                            , "/user/*"
                            , "/challenge/*").hasRole(roleRepository.findByLevel(2).getName());

        });

        // add custom filter
        http.addFilterAt(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}
