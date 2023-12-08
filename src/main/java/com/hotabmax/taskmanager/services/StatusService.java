package com.hotabmax.taskmanager.services;

import com.hotabmax.taskmanager.models.Status;
import com.hotabmax.taskmanager.models.UserRoles;
import com.hotabmax.taskmanager.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {
    @Autowired
    private StatusRepository statusRepository;

    public StatusService() {
    }

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public void createStatus(Status status) {
        statusRepository.save(status);
    }

    public void deleteAll() {
        statusRepository.deleteAll();
    }
    public void deleteByName(String name) {
        statusRepository.deleteByName(name);
    }

    public List<Status> findAll() {
        return statusRepository.findAll();
    }

    public Status findByName(String name){
        return statusRepository.findByName(name);
    }

    public Status findById(long id){
        return statusRepository.findById(id).orElse(null);
    }
}
