package com.hayden.limg_diary.entity.user;

import com.hayden.limg_diary.entity.role.RoleEntity;
import com.hayden.limg_diary.entity.role.RoleRepository;
import com.hayden.limg_diary.entity.role.UserAndRoleService;
import com.hayden.limg_diary.entity.user.dto.*;
import com.hayden.limg_diary.jwt.JwtUtil;
import com.hayden.limg_diary.jwt.entity.RefreshService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public ResponseEntity<SigninResponseDto> signin(SigninRequestDto signinRequestDto, HttpServletResponse response){

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
        String accessToken = jwtUtil.createJwt("access", userEntity.getUsername(), jwtUtil.getAccessExpMinute());   // 30minute
        String refreshToken = jwtUtil.createJwt("refresh", userEntity.getUsername(), jwtUtil.getRefreshExpMinute());   // 2weeks
        refreshService.addRefresh(refreshToken);

        // create refresh cookies
        Cookie refreshCookie = new Cookie("Refresh", refreshToken);
        response.addCookie(refreshCookie);

        // Success response
        SigninResponseDto signinResponseDto = new SigninResponseDto(HttpStatus.OK.value(), true, "success");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authentication", "Bearer " + accessToken);
        return new ResponseEntity<SigninResponseDto>(signinResponseDto, httpHeaders, HttpStatus.OK);
    }

    public ResponseEntity<DefaultResponseDto> refresh(String refresh, HttpServletResponse response){
        System.out.println(refresh);
        // response dto
        DefaultResponseDto defaultResponseDto = new DefaultResponseDto();

        // check token
        defaultResponseDto.setMember(HttpStatus.BAD_REQUEST, false, "token is invalid");
        if (refresh == null)  return new ResponseEntity<DefaultResponseDto>(defaultResponseDto, HttpStatus.BAD_REQUEST);

        // check expired
        defaultResponseDto.setMember(HttpStatus.UNAUTHORIZED, false, "token is expired");
        if (jwtUtil.isExpired(refresh)) return new ResponseEntity<DefaultResponseDto>(defaultResponseDto, HttpStatus.UNAUTHORIZED);

        // category check
        defaultResponseDto.setMember(HttpStatus.UNAUTHORIZED, false, "token is not refresh");
        if (!jwtUtil.getCategory(refresh).equals("refresh")) return  new ResponseEntity<DefaultResponseDto>(defaultResponseDto, HttpStatus.UNAUTHORIZED);

        // check refresh is in server
        defaultResponseDto.setMember(HttpStatus.UNAUTHORIZED, false, "token is not match");
        if(!refreshService.isExist(refresh)) return  new ResponseEntity<DefaultResponseDto>(defaultResponseDto, HttpStatus.UNAUTHORIZED);

        // Create new token
        String username = jwtUtil.getUsername(refresh);
        String newAccess = jwtUtil.createJwt("access", username, jwtUtil.getAccessExpMinute());
        String newRefresh = jwtUtil.createJwt("refresh", username, jwtUtil.getRefreshExpMinute());

        // refresh db update
        refreshService.deleteRefresh(refresh);
        refreshService.addRefresh(newRefresh);

        defaultResponseDto.setMember(HttpStatus.OK, true, "success");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authentication", "Bearer " + newAccess);

        // create refresh cookies
        Cookie refreshCookie = new Cookie("Refresh", newRefresh);
        response.addCookie(refreshCookie);

        return new ResponseEntity<DefaultResponseDto>(defaultResponseDto, httpHeaders, HttpStatus.OK);
    }

    public ResponseEntity<DefaultResponseDto> logout(String token){
        // response dto
        DefaultResponseDto defaultResponseDto = new DefaultResponseDto();

        // check token
        defaultResponseDto.setMember(HttpStatus.BAD_REQUEST, false, "token is invalid");
        if (token == null || !token.startsWith("Bearer "))  return new ResponseEntity<DefaultResponseDto>(defaultResponseDto, HttpStatus.BAD_REQUEST);

        String refresh = token.split("Bearer ")[1];

        // check expired
        defaultResponseDto.setMember(HttpStatus.UNAUTHORIZED, false, "token is expired");
        if (jwtUtil.isExpired(refresh)) return new ResponseEntity<DefaultResponseDto>(defaultResponseDto, HttpStatus.UNAUTHORIZED);

        // category check
        defaultResponseDto.setMember(HttpStatus.UNAUTHORIZED, false, "token is not refresh");
        if (!jwtUtil.getCategory(refresh).equals("refresh")) return  new ResponseEntity<DefaultResponseDto>(defaultResponseDto, HttpStatus.UNAUTHORIZED);

        // check refresh is in server
        defaultResponseDto.setMember(HttpStatus.UNAUTHORIZED, false, "token is not match");
        if(!refreshService.isExist(refresh)) return  new ResponseEntity<DefaultResponseDto>(defaultResponseDto, HttpStatus.UNAUTHORIZED);

        // refresh db delete
        refreshService.deleteRefresh(refresh);

        defaultResponseDto.setMember(HttpStatus.OK, true, "success");
        return new ResponseEntity<DefaultResponseDto>(defaultResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<DefaultResponseDto> modify(ModifyRequestDto modifyRequestDto, UserEntity userEntity){

        DefaultResponseDto defaultResponseDto = new DefaultResponseDto();

        // Password check
        defaultResponseDto.setMember(HttpStatus.BAD_REQUEST, false, "password is invalid");
        if (modifyRequestDto.getPassword() == null || ! bCryptPasswordEncoder.matches(modifyRequestDto.getPassword(), userEntity.getPassword()))
            return new ResponseEntity<>(defaultResponseDto, HttpStatus.BAD_REQUEST);

        // Edit entity
        if (modifyRequestDto.getNickname() != null) userEntity.setNickname(modifyRequestDto.getNickname());
            userEntity.setNickname(modifyRequestDto.getNickname());
        if (modifyRequestDto.getNew_password() != null && modifyRequestDto.getNew_password_check() != null
                && modifyRequestDto.getNew_password().equals(modifyRequestDto.getNew_password_check()))
            userEntity.setPassword(bCryptPasswordEncoder.encode(modifyRequestDto.getNew_password()));

        userRepository.save(userEntity);

        defaultResponseDto.setMember(HttpStatus.OK, true, "success");
        return new ResponseEntity<>(defaultResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<GetUserResponseDto> getBySelf(UserEntity user){
        GetUserResponseDto getUserResponseDto = new GetUserResponseDto();
        List<RoleEntity> roles = userAndRoleService.getRolesByUser(user);

        getUserResponseDto.setStatus(HttpStatus.OK.value());
        getUserResponseDto.setSuccess(true);
        getUserResponseDto.setMsg("success");
        getUserResponseDto.getUserSelf().setId(user.getId());
        getUserResponseDto.getUserSelf().setCreated_date(user.getCreatedDate());
        getUserResponseDto.getUserSelf().setUpdated_date(user.getUpdatedDate());
        getUserResponseDto.getUserSelf().setUsername(user.getUsername());
        getUserResponseDto.getUserSelf().setNickname(user.getNickname());
        getUserResponseDto.getUserSelf().setRole(new ArrayList<String>());

        for (RoleEntity role:roles){
            getUserResponseDto.getUserSelf().getRole().add(role.getRole());
        }

        return new ResponseEntity<>(getUserResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<GetUserResponseDto> getByUserId(int userId){
        GetUserResponseDto getUserResponseDto = new GetUserResponseDto();

        // find user and check userid
        Optional<UserEntity> userOp = userRepository.findById(userId);
        if(userOp.isEmpty())  {
            getUserResponseDto.setUserSelf(null);
            getUserResponseDto.setStatus(HttpStatus.BAD_REQUEST.value());
            getUserResponseDto.setMsg("user not found");
            getUserResponseDto.setSuccess(false);
            return new ResponseEntity<>(getUserResponseDto, HttpStatus.BAD_REQUEST);
        }
        UserEntity user = userOp.get();

        // get role
        List<RoleEntity> roles = userAndRoleService.getRolesByUser(user);

        // set res dto
        getUserResponseDto.setStatus(HttpStatus.OK.value());
        getUserResponseDto.setSuccess(true);
        getUserResponseDto.setMsg("success");
        getUserResponseDto.getUserSelf().setId(user.getId());
        getUserResponseDto.getUserSelf().setCreated_date(user.getCreatedDate());
        getUserResponseDto.getUserSelf().setUpdated_date(user.getUpdatedDate());
        getUserResponseDto.getUserSelf().setUsername(user.getUsername());
        getUserResponseDto.getUserSelf().setNickname(user.getNickname());
        getUserResponseDto.getUserSelf().setRole(new ArrayList<String>());
        for (RoleEntity role:roles){
            getUserResponseDto.getUserSelf().getRole().add(role.getRole());
        }

        return new ResponseEntity<>(getUserResponseDto, HttpStatus.OK);
    }

    List<RoleEntity> getRoles(UserEntity user){
        return userAndRoleService.getRolesByUser(user);
    }

}
