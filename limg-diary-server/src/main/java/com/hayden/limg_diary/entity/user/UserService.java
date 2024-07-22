package com.hayden.limg_diary.entity.user;

import com.hayden.limg_diary.entity.user.dto.SignupRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public boolean signup(SignupRequestDto signupRequestDto){

        // null val check
        if (signupRequestDto.getPassword() == null
            || signupRequestDto.getUsername() == null
            || signupRequestDto.getNickname() == null) return false;

        // email check
        if (userRepository.existsByUsername(signupRequestDto.getUsername()))   return false;

        // password check
        if (!signupRequestDto.getPassword().equals(signupRequestDto.getPassword_check())) return false;

        // save at DB
        UserEntity newUser = new UserEntity();
        newUser.setUsername(signupRequestDto.getUsername());
        newUser.setNickname(signupRequestDto.getNickname());
        newUser.setPassword(bCryptPasswordEncoder.encode(signupRequestDto.getPassword()));
        userRepository.save(newUser);
        return true;

    }
}
