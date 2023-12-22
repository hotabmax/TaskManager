package com.hotabmax.taskmanager.services;

import com.hotabmax.taskmanager.dtos_database.PriorityResponse;
import com.hotabmax.taskmanager.exceptions_database.FieldNotFoundException;
import com.hotabmax.taskmanager.exceptions_database.PrioritiesNotFoundException;
import com.hotabmax.taskmanager.entities.Priority;
import com.hotabmax.taskmanager.repositories.PriorityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PriorityService {

    private final PriorityRepository priorityRepository;

    public PriorityService(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    @Transactional
    public void createStatus(Priority priority) {
        priorityRepository.save(priority);
    }

    @Transactional
    public void deleteAll() {
        priorityRepository.deleteAll();
    }
    @Transactional
    public void deleteByName(String name) {
        priorityRepository.deleteByName(name);
    }

    @Transactional
    public List<PriorityResponse> findAll() throws PrioritiesNotFoundException {
        List<Priority> priorities = priorityRepository.findAll();
        if(!priorities.isEmpty()){
            List<PriorityResponse> priorityResponses = new ArrayList<>();
            for(Priority p : priorities){
                priorityResponses.add(new PriorityResponse(p.getName()));
            }
            return priorityResponses;
        } else {
            throw new PrioritiesNotFoundException();
        }
    }
    @Transactional
    public Priority findByName(String name) throws FieldNotFoundException {
        Priority priority = priorityRepository.findByName(name);
        if (priority != null){
            return priority;
        } else {
            throw new FieldNotFoundException("priority");
        }
    }

    @Transactional
    public Priority findById(long id){
        return priorityRepository.findById(id).orElse(null);
    }
}
