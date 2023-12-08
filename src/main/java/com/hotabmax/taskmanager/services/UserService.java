package com.hotabmax.taskmanager.services;

import com.hotabmax.taskmanager.models.User;
import com.hotabmax.taskmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    public List<User> findByRoleId(int userroleid){
        return userRepository.findByRoleId(userroleid);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(long id){
        return userRepository.findById(id).orElse(null);
    }



}
