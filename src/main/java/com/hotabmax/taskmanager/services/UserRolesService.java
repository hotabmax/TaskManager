package com.hotabmax.taskmanager.services;

import com.hotabmax.taskmanager.models.UserRoles;
import com.hotabmax.taskmanager.repositories.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRolesService {
    @Autowired
    private UserRolesRepository userRoleRepository;


    public UserRolesService() {
    }

    public UserRolesService(UserRolesRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public void createUserRole(UserRoles userRole) {
        userRoleRepository.save(userRole);
    }

    public void deleteAll() {
        userRoleRepository.deleteAll();
    }
    public void deleteByName(String name) {
        userRoleRepository.deleteByName(name);
    }

    public List<UserRoles> findAll() {
        return userRoleRepository.findAll();
    }

    public UserRoles findByName(String name){
        return userRoleRepository.findByName(name);
    }

    public UserRoles findById(long id){
        return userRoleRepository.findById(id).orElse(null);
    }
}
