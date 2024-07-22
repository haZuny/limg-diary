package com.hayden.limg_diary.entity.user;

import com.hayden.limg_diary.entity.role.RoleEntity;
import com.hayden.limg_diary.entity.role.RoleRepository;
import com.hayden.limg_diary.entity.role.UserAndRoleService;
import com.hayden.limg_diary.entity.user.dto.SigninRequestDto;
import com.hayden.limg_diary.entity.user.dto.SigninResponseDto;
import com.hayden.limg_diary.entity.user.dto.SignupRequestDto;
import com.hayden.limg_diary.jwt.JwtUtil;
import com.hayden.limg_diary.jwt.entity.RefreshService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    RoleRepository roleRepository;
    UserAndRoleService userAndRoleService;
    JwtUtil jwtUtil;
    RefreshService refreshService;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository, UserAndRoleService userAndRoleService, JwtUtil jwtUtil, RefreshService refreshService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
        this.userAndRoleService = userAndRoleService;
        this.jwtUtil = jwtUtil;
        this.refreshService = refreshService;
    }

    public boolean signup(SignupRequestDto signupReqDto){

        // null val check
        if (signupReqDto.getPassword() == null
            || signupReqDto.getUsername() == null
            || signupReqDto.getNickname() == null) return false;

        // email check
        if (userRepository.existsByUsername(signupReqDto.getUsername()))   return false;

        // password check
        if (!signupReqDto.getPassword().equals(signupReqDto.getPassword_check())) return false;

        try{
            // save at DB
            UserEntity newUser = new UserEntity();
            newUser.setUsername(signupReqDto.getUsername());
            newUser.setNickname(signupReqDto.getNickname());
            newUser.setPassword(bCryptPasswordEncoder.encode(signupReqDto.getPassword()));
            UserEntity savedUser = userRepository.save(newUser);

            // add Role
            RoleEntity userRole = roleRepository.findByLevel(1);
            userAndRoleService.addRole(savedUser, userRole);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public ResponseEntity<SigninResponseDto> signin(SigninRequestDto signinRequestDto){



        // null check
        if(signinRequestDto.getUsername() == null || signinRequestDto.getPassword() == null)    {
            SigninResponseDto failSigninResponseDto = new SigninResponseDto(HttpStatus.BAD_REQUEST.value(), false, "request null");
            ResponseEntity<SigninResponseDto> failResponse = new ResponseEntity<SigninResponseDto>(failSigninResponseDto, HttpStatus.BAD_REQUEST);

            return failResponse;
        }

        // authorization
        UserEntity userEntity = userRepository.findByUsername(signinRequestDto.getUsername());
        if (userEntity == null) {
            SigninResponseDto failSigninResponseDto = new SigninResponseDto(HttpStatus.BAD_REQUEST.value(), false, "user not found");
            ResponseEntity<SigninResponseDto> failResponse = new ResponseEntity<SigninResponseDto>(failSigninResponseDto, HttpStatus.BAD_REQUEST);

            return failResponse;
        }

        if (!bCryptPasswordEncoder.matches(signinRequestDto.getPassword(), userEntity.getPassword()))    {
            SigninResponseDto failSigninResponseDto = new SigninResponseDto(HttpStatus.BAD_REQUEST.value(), false, "password not matching");
            ResponseEntity<SigninResponseDto> failResponse = new ResponseEntity<SigninResponseDto>(failSigninResponseDto, HttpStatus.BAD_REQUEST);

            return failResponse;
        }

        // Create token
        String accessToken = jwtUtil.createJwt("access", userEntity.getUsername(), 30);   // 30minute
        String refreshToken = jwtUtil.createJwt("refresh", userEntity.getUsername(), 60 * 24 * 14);   // 2weeks
        refreshService.addRefresh(refreshToken);


        // Success response
        SigninResponseDto signinResponseDto = new SigninResponseDto(HttpStatus.OK.value(), true, "success");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authentication", "Bearer " + accessToken);
        httpHeaders.set("Refresh",  "Bearer " + refreshToken);
        return new ResponseEntity<SigninResponseDto>(signinResponseDto, httpHeaders, HttpStatus.OK);
    }

    List<RoleEntity> getRoles(UserEntity user){
        return userAndRoleService.getRolesByUser(user);
    }

}
