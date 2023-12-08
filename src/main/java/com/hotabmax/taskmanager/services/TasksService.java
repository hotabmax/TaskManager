package com.hotabmax.taskmanager.services;

import com.hotabmax.taskmanager.models.Tasks;
import com.hotabmax.taskmanager.repositories.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TasksService {
    @Autowired
    private TasksRepository tasksRepository;

    public TasksService(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    public void createTask(Tasks tasks) {
        tasksRepository.save(tasks);
    }

    public void deleteAll() {
        tasksRepository.deleteAll();
    }

    public void deleteByName(String name) {
        tasksRepository.deleteByName(name);
    }

    public List<Tasks> findByCustomerId(int customerid){
        return tasksRepository.findByCustomerId(customerid);
    }

    public List<Tasks> findByExecutorId(int executorid){
        return tasksRepository.findByExecutorId(executorid);
    }

    public Tasks findByName(String name){
        return tasksRepository.findByName(name);
    }

    public List<Tasks> findAll() {
        return tasksRepository.findAll();
    }

    public Tasks findById(long id){
        return tasksRepository.findById(id).orElse(null);
    }

    public void tranzactionEditName(String oldName, String newName){ tasksRepository.tranzactionEditName(oldName, newName); }

    public void tranzactionEditDescription(String name, String newDescription){ tasksRepository.tranzactionEditDescription(name, newDescription); }
    public void tranzactionEditStatus(String name, int newStatusid){ tasksRepository.tranzactionEditStatus(name, newStatusid); }

    public void tranzactionEditPriority(String name, int newPriorityid){ tasksRepository.tranzactionEditPriority(name, newPriorityid); }

    public void tranzactionEditExecutor(String name, int newExecutorid){ tasksRepository.tranzactionEditExecutor(name, newExecutorid); }
}
