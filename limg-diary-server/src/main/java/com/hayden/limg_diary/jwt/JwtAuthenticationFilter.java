package com.hayden.limg_diary.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hayden.limg_diary.entity.role.RoleEntity;
import com.hayden.limg_diary.entity.role.UserAndRoleService;
import com.hayden.limg_diary.entity.user.CustomUserDetails;
import com.hayden.limg_diary.entity.user.UserEntity;
import com.hayden.limg_diary.entity.user.UserRepository;
import com.hayden.limg_diary.entity.user.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    JwtUtil jwtUtil;
    UserRepository userRepository;
    UserAndRoleService userAndRoleService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository, UserAndRoleService userAndRoleService) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.userAndRoleService = userAndRoleService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 특정 경로 무시
        String path = request.getRequestURI();
        if (path.startsWith("/h2-console") ||
                path.startsWith("/user/signin") ||
                path.startsWith("/user/signup") ||
                path.startsWith("/user/logout") ||
                path.startsWith("/user/refresh")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authToken = request.getHeader("Authentication");

        // token check
        if (authToken == null || !authToken.startsWith("Bearer ")) {
            response.getWriter().write("token is empty");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

//            filterChain.doFilter(request, response);
            return;
        }

        String token = authToken.split("Bearer ")[1];

        // 토큰 만료 검증
        if (jwtUtil.isExpired(token)) {
            response.getWriter().write("access token is expired");
            response.setStatus(401);

//            filterChain.doFilter(request, response);
            return;
        }

        String category = jwtUtil.getCategory(token);

        // 카테고리 검사(access)
        if (!category.equals("access")) {
            response.getWriter().write("invalid access token");
            response.setStatus(400);

//            filterChain.doFilter(request, response);
            return;
        }


        // 임시 세션 추가
        String username = jwtUtil.getUsername(token);
        UserEntity userEntity = userRepository.findByUsername(username);
        List<RoleEntity> roles = userAndRoleService.getRolesByUser(userEntity);
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setRoles(roles);
        customUserDetails.setUserEntity(userEntity);

        // 세션 인증 토큰 생성
        Authentication authSession = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authSession);
        filterChain.doFilter(request, response);
    }
}

