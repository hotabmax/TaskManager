package com.hotabmax.taskmanager.services;

import com.hotabmax.taskmanager.models.Priority;
import com.hotabmax.taskmanager.models.Status;
import com.hotabmax.taskmanager.repositories.PriorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriorityService {
    @Autowired
    private PriorityRepository priorityRepository;

    public PriorityService() {
    }

    public PriorityService(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    public void createStatus(Priority priority) {
        priorityRepository.save(priority);
    }

    public void deleteAll() {
        priorityRepository.deleteAll();
    }
    public void deleteByName(String name) {
        priorityRepository.deleteByName(name);
    }

    public List<Priority> findAll() {
        return priorityRepository.findAll();
    }

    public Priority findByName(String name){
        return priorityRepository.findByName(name);
    }

    public Priority findById(long id){
        return priorityRepository.findById(id).orElse(null);
    }
}
