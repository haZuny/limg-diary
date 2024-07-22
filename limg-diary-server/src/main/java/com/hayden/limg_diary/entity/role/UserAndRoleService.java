package com.hayden.limg_diary.entity.role;

import com.hayden.limg_diary.entity.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAndRoleService {
    UserAndRoleRepository userAndRoleRepository;

    @Autowired
    public UserAndRoleService(UserAndRoleRepository userAndRoleRepository) {
        this.userAndRoleRepository = userAndRoleRepository;
    }

    public boolean addRole(UserEntity user, RoleEntity role){
        try{
            UserAndRoleEntity userAndRoleEntity = new UserAndRoleEntity();
            userAndRoleEntity.setUser(user);
            userAndRoleEntity.setRole(role);
            userAndRoleRepository.save(userAndRoleEntity);
            return true;
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<RoleEntity> getRolesByUser(UserEntity user){
        ArrayList<RoleEntity> roleList = new ArrayList<>();
        for (UserAndRoleEntity userAndRole : userAndRoleRepository.findByUser(user)){
            roleList.add(userAndRole.role);
        }
        return roleList;
    }
}
