package com.hotabmax.taskmanager.services;

import com.hotabmax.taskmanager.entities.UserRoles;
import com.hotabmax.taskmanager.repositories.UserRolesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserRolesService {
    private final UserRolesRepository userRoleRepository;
    public UserRolesService(UserRolesRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Transactional
    public void createUserRole(UserRoles userRole) {
        userRoleRepository.save(userRole);
    }

    @Transactional
    public void deleteAll() {
        userRoleRepository.deleteAll();
    }

    @Transactional
    public void deleteByName(String name) {
        userRoleRepository.deleteByName(name);
    }

    @Transactional
    public List<UserRoles> findAll() {
        return userRoleRepository.findAll();
    }

    @Transactional
    public UserRoles findByName(String name){
        return userRoleRepository.findByName(name);
    }

    @Transactional
    public UserRoles findById(long id){
        return userRoleRepository.findById(id).orElse(null);
    }
}
