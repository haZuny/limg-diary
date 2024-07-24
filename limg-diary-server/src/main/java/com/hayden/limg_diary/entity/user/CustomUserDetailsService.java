package com.hayden.limg_diary.entity.user;

import com.hayden.limg_diary.entity.role.UserAndRoleService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    UserRepository userRepository;
    UserAndRoleService userAndRoleService;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, UserAndRoleService userAndRoleService) {
        this.userRepository = userRepository;
        this.userAndRoleService = userAndRoleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUserEntity(userEntity);
        customUserDetails.setRoles(userAndRoleService.getRolesByUser(userEntity));
        return customUserDetails;
    }
}
