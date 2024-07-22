package com.hayden.limg_diary.entity.user;

import com.hayden.limg_diary.entity.role.RoleEntity;
import com.hayden.limg_diary.entity.role.RoleRepository;
import com.hayden.limg_diary.entity.role.UserAndRoleEntity;
import com.hayden.limg_diary.entity.role.UserAndRoleService;
import com.hayden.limg_diary.entity.user.dto.SignupRequestDto;
import com.hayden.limg_diary.entity.user.dto.SignupResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    RoleRepository roleRepository;
    UserAndRoleService userAndRoleService;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository, UserAndRoleService userAndRoleService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
        this.userAndRoleService = userAndRoleService;
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

}
