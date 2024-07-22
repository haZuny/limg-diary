package com.hayden.limg_diary.entity.user;

import com.hayden.limg_diary.entity.role.RoleEntity;
import com.hayden.limg_diary.entity.user.UserEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class CustomUserDetails implements UserDetails {
    UserEntity userEntity;
    List<RoleEntity> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> authList = new ArrayList<>();

        for (RoleEntity roleEntity:roles){
            authList.add(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return roleEntity.getRole();
                }
            });
        }

        return authList;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }
}
