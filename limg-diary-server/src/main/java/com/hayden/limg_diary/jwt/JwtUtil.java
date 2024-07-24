package com.hayden.limg_diary.jwt;

import com.hayden.limg_diary.entity.role.RoleEntity;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
@Getter
public class JwtUtil {
    private SecretKey secretKey;

    private int accessExpMinute = 30;
    private int refreshExpMinute = 60 * 24 * 14;


    // 키값을 SecretKey 객체로 반환
    public JwtUtil(@Value("${spring.jwt.secretkey}") String key) {
        this.secretKey  = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    // 토큰 생성
    public String createJwt(String category, String username, int expiredMinute){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expiredMinute);

        return Jwts.builder()
                .claim("category", category)
                .claim("username", username)
                .issuedAt(new Date())
                .expiration(calendar.getTime())
                .signWith(secretKey)
                .compact();
    }

    // 토큰 검증 - 카테고리
    public String getCategory(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
    }

    // 토큰 검증 - 아이디
    public String getUsername(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    // 토큰 검증 - 토큰 유효기간
    public Boolean isExpired(String token){
        try{
            System.out.println(Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration());
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
        } catch (Exception e){
            return true;
        }
    }



}
