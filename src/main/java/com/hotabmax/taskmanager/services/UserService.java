package com.hotabmax.taskmanager.services;

import com.hotabmax.taskmanager.dtos_database.ExecutorResponse;
import com.hotabmax.taskmanager.exceptions_database.ExecutorNotFoundException;
import com.hotabmax.taskmanager.exceptions_database.FieldNotFoundException;
import com.hotabmax.taskmanager.entities.User;
import com.hotabmax.taskmanager.repositories.UserRepository;
import com.hotabmax.taskmanager.repositories.UserRolesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final UserRolesRepository userRolesRepository;

    public UserService(UserRepository userRepository, UserRolesRepository userRolesRepository) {
        this.userRepository = userRepository;
        this.userRolesRepository = userRolesRepository;
    }

    @Transactional
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Transactional
    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    @Transactional
    public List<ExecutorResponse> findExecutors(String role) throws ExecutorNotFoundException {
        List<User> users = userRepository.findByRoleId(userRolesRepository.findByName(role).getId());
        if(!users.isEmpty()){
            List<ExecutorResponse> executorResponses = new ArrayList<>();
            for (User u : users){
                executorResponses.add(new ExecutorResponse(u.getEmail()));
            }
            return executorResponses;
        } else {
            throw new ExecutorNotFoundException();
        }
    }

    @Transactional
    public User findByEmail(String email) throws FieldNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null){
            return user;
        } else {
            throw new FieldNotFoundException("email");
        }
    }

    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User findById(long id){
        return userRepository.findById(id).orElse(null);
    }



}
