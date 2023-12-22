package com.hotabmax.taskmanager.services;

import com.hotabmax.taskmanager.dtos_database.StatusResponse;
import com.hotabmax.taskmanager.exceptions_database.FieldNotFoundException;
import com.hotabmax.taskmanager.exceptions_database.StatusesNotFoundExceprion;
import com.hotabmax.taskmanager.entities.Status;
import com.hotabmax.taskmanager.repositories.StatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatusService {

    private final StatusRepository statusRepository;
    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Transactional
    public void createStatus(Status status) {
        statusRepository.save(status);
    }

    @Transactional
    public void deleteAll() {
        statusRepository.deleteAll();
    }

    @Transactional
    public void deleteByName(String name) {
        statusRepository.deleteByName(name);
    }

    @Transactional
    public List<StatusResponse> findAll() throws StatusesNotFoundExceprion {
        List<Status> statuses = statusRepository.findAll();
        if(!statuses.isEmpty()){
            List<StatusResponse> statusResponses = new ArrayList<>();
            for (Status s : statuses){
                statusResponses.add(new StatusResponse(s.getName()));
            }
            return statusResponses;
        } else {
            throw new StatusesNotFoundExceprion();
        }
    }

    @Transactional
    public Status findByName(String name) throws FieldNotFoundException {
        Status status = statusRepository.findByName(name);
        if (status != null){
            return status;
        } else {
            throw new FieldNotFoundException("status");
        }
    }

    @Transactional
    public Status findById(long id){
        return statusRepository.findById(id).orElse(null);
    }
}
