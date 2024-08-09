package com.hayden.limg_diary.entity.user;

import com.hayden.limg_diary.entity.user.dto.*;
import com.hayden.limg_diary.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> postSignup(@RequestBody SignupRequestDto signupReqDto){
        boolean res = userService.signup(signupReqDto);

        SignupResponseDto signupResponseDto = new SignupResponseDto();

        // success
        signupResponseDto.setSuccess();
        if (res) return new ResponseEntity<>(signupResponseDto, HttpStatus.CREATED);

        // fail
        signupResponseDto.setFail();
        return new ResponseEntity<>(signupResponseDto, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/signin")
    public ResponseEntity postSignin(@RequestBody SigninRequestDto signinRequestDto, HttpServletResponse response){
        return userService.signin(signinRequestDto, response);
    }

    @PostMapping("/refresh")
    public ResponseEntity postRefresh(@CookieValue(name = "Refresh", required = false) String refresh, HttpServletResponse response){
        return userService.refresh(refresh, response);
    }

    @GetMapping("/check")
    public ResponseEntity<DefaultResponseDto> checkAuthorization(){
        DefaultResponseDto responseDto = new DefaultResponseDto();
        responseDto.setMember(HttpStatus.OK, true, "authorized");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<DefaultResponseDto> postLogout(@CookieValue(name = "Refresh", required = false) String refresh){
        return userService.logout(refresh);
    }

    @PatchMapping("/modify")
    public ResponseEntity<DefaultResponseDto> patchModify(@RequestBody ModifyRequestDto modifyRequestDto, @AuthenticationPrincipal CustomUserDetails userDetails){
        UserEntity user = userDetails.getUserEntity();
        return userService.modify(modifyRequestDto, user);
    }

    @GetMapping("/self")
    public ResponseEntity<GetUserResponseDto> getSelf(@AuthenticationPrincipal CustomUserDetails userDetails){
        UserEntity user = userDetails.getUserEntity();
        return userService.getBySelf(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponseDto> getByUserId(@PathVariable int id){
        return userService.getByUserId(id);
    }
}
