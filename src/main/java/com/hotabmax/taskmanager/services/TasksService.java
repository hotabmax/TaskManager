package com.hotabmax.taskmanager.services;

import com.hotabmax.taskmanager.dtos_database.TaskRequest;
import com.hotabmax.taskmanager.dtos_database.TaskResponse;
import com.hotabmax.taskmanager.dtos_database.transactions.ChangeTaskDescriptionRequest;
import com.hotabmax.taskmanager.dtos_database.transactions.ChangeTaskExecutorRequest;
import com.hotabmax.taskmanager.dtos_database.transactions.ChangeTaskPriorityRequest;
import com.hotabmax.taskmanager.dtos_database.transactions.ChangeTaskStatusRequest;
import com.hotabmax.taskmanager.exceptions_database.AllTasksNotFoundException;
import com.hotabmax.taskmanager.exceptions_database.TasksNotFoundException;
import com.hotabmax.taskmanager.exceptions_database.FieldNotFoundException;
import com.hotabmax.taskmanager.exceptions_database.UniqueFieldException;
import com.hotabmax.taskmanager.entities.Priority;
import com.hotabmax.taskmanager.entities.Status;
import com.hotabmax.taskmanager.entities.Tasks;
import com.hotabmax.taskmanager.entities.User;
import com.hotabmax.taskmanager.repositories.PriorityRepository;
import com.hotabmax.taskmanager.repositories.StatusRepository;
import com.hotabmax.taskmanager.repositories.TasksRepository;
import com.hotabmax.taskmanager.repositories.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@EnableTransactionManagement
public class TasksService {
    private final TasksRepository tasksRepository;
    private final StatusRepository statusRepository;
    private final PriorityRepository priorityRepository;
    private final UserRepository userRepository;

    public TasksService(TasksRepository tasksRepository, StatusRepository statusRepository, PriorityRepository priorityRepository, UserRepository userRepository) {
        this.tasksRepository = tasksRepository;
        this.statusRepository = statusRepository;
        this.priorityRepository = priorityRepository;
        this.userRepository = userRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public void createTask(TaskRequest taskRequest, String customerEmail) throws UniqueFieldException, FieldNotFoundException {
        if (statusRepository.findByName(taskRequest.getStatus()) == null){
            throw new FieldNotFoundException("status");
        } else if (priorityRepository.findByName(taskRequest.getPriority()) == null){
            throw new FieldNotFoundException("priority");
        } else if (userRepository.findByEmail(taskRequest.getExecutorEmail()) == null){
            throw new FieldNotFoundException("executor");
        }
        try{
            tasksRepository.save(new Tasks(
                    taskRequest.getName(), taskRequest.getDescription(),
                    statusRepository.findByName(taskRequest.getStatus()).getId(),
                    priorityRepository.findByName(taskRequest.getPriority()).getId(),
                    userRepository.findByEmail(customerEmail).getId(),
                    userRepository.findByEmail(taskRequest.getExecutorEmail()).getId()
            ));
        } catch (DataIntegrityViolationException e){
            throw new UniqueFieldException();
        }
    }

    @Transactional
    public void deleteAll() {
        tasksRepository.deleteAll();
    }

    @Transactional
    public void deleteByName(String name) throws FieldNotFoundException {
        if (tasksRepository.findByName(name) != null){
            tasksRepository.deleteByName(name);
        } else {
            throw new FieldNotFoundException("name");
        }
    }

    @Transactional
    public List<TaskResponse> findByCustomerEmail(int page, int size, String customerEmail) throws TasksNotFoundException {
        List<Tasks> tasks = tasksRepository.findByCustomerId(--page * size, size, userRepository.findByEmail(customerEmail).getId());
        if (!tasks.isEmpty()){
            List<TaskResponse> taskResponse = new ArrayList<>();
            for(Tasks t : tasks){
                taskResponse.add(new TaskResponse(t.getName(), t.getDescription(),
                        statusRepository.findById((long) t.getStatusid()).get().getName(),
                        priorityRepository.findById((long) t.getPriorityid()).get().getName(),
                        userRepository.findById((long) t.getCustomerid()).get().getEmail(),
                        userRepository.findById((long) t.getExecutorid()).get().getEmail()));
            }
            return taskResponse;
        } else {
            throw new TasksNotFoundException("customer");
        }
    }

    @Transactional
    public List<TaskResponse> findByExecutorEmail(int page, int size, String executorEmail) throws TasksNotFoundException {
        List<Tasks> tasks = tasksRepository.findByExecutorId(--page*size, size, userRepository.findByEmail(executorEmail).getId());
        if (!tasks.isEmpty()){
            List<TaskResponse> taskResponse = new ArrayList<>();
            for(Tasks t : tasks){
                taskResponse.add(new TaskResponse(t.getName(), t.getDescription(),
                        statusRepository.findById((long) t.getStatusid()).get().getName(),
                        priorityRepository.findById((long) t.getPriorityid()).get().getName(),
                        userRepository.findById((long) t.getCustomerid()).get().getEmail(),
                        userRepository.findById((long) t.getExecutorid()).get().getEmail()));
            }
            return taskResponse;
        } else {
            throw new TasksNotFoundException("executor");
        }
    }

    @Transactional
    public Tasks findByName(String name) throws FieldNotFoundException {
        Tasks task = tasksRepository.findByName(name);
        if (task != null){
            return task;
        } else {
            throw new FieldNotFoundException("task");
        }
    }

    @Transactional
    public List<TaskResponse> findAll(int page, int size) throws AllTasksNotFoundException {
        List<Tasks> tasks = tasksRepository.findAll(--page * size, size);
        if (!tasks.isEmpty()){
            List<TaskResponse> taskResponse = new ArrayList<>();
            for(Tasks t : tasks){
                taskResponse.add(new TaskResponse(t.getName(), t.getDescription(),
                        statusRepository.findById((long) t.getStatusid()).get().getName(),
                        priorityRepository.findById((long) t.getPriorityid()).get().getName(),
                        userRepository.findById((long) t.getCustomerid()).get().getEmail(),
                        userRepository.findById((long) t.getExecutorid()).get().getEmail()));
            }
            return taskResponse;
        } else {
            throw new AllTasksNotFoundException();
        }
    }

    @Transactional
    public Tasks findById(long id){
        return tasksRepository.findById(id).orElse(null);
    }

    @Transactional
    public void tranzactionEditName(String oldName, String newName){ tasksRepository.tranzactionEditName(oldName, newName); }

    @Transactional
    public void tranzactionEditDescription(ChangeTaskDescriptionRequest changeRequest) throws FieldNotFoundException {
        Tasks task = tasksRepository.findByName(changeRequest.getName());
        if(task == null){
            throw new FieldNotFoundException("task");
        }
        task.setDescription(changeRequest.getDescription());
        tasksRepository.tranzactionEditDescription(task.getName(), task.getDescription());
    }

    @Transactional
    public void tranzactionEditStatus(ChangeTaskStatusRequest changeRequest) throws FieldNotFoundException {
        Tasks task = tasksRepository.findByName(changeRequest.getName());
        Status status = statusRepository.findByName(changeRequest.getStatus());
        if(task == null){
            throw new FieldNotFoundException("task");
        } else if (status == null){
            throw new FieldNotFoundException("status");
        }
        task.setStatusid(status.getId());
        tasksRepository.tranzactionEditStatus(task.getName(), task.getStatusid());
    }

    @Transactional
    public void tranzactionEditPriority(ChangeTaskPriorityRequest changeRequest) throws FieldNotFoundException {
        Tasks task = tasksRepository.findByName(changeRequest.getName());
        Priority priority = priorityRepository.findByName(changeRequest.getPriority());
        if(task == null){
            throw new FieldNotFoundException("task");
        } else if (priority == null){
            throw new FieldNotFoundException("priority");
        }
        task.setPriorityid(priority.getId());
        tasksRepository.tranzactionEditPriority(task.getName(), task.getPriorityid());
    }

    @Transactional
    public void tranzactionEditExecutor(ChangeTaskExecutorRequest changeRequest) throws FieldNotFoundException {
        Tasks task = tasksRepository.findByName(changeRequest.getName());
        User executor = userRepository.findByEmail(changeRequest.getExecutorEmail());
        if(task == null){
            throw new FieldNotFoundException("task");
        } else if (executor == null){
            throw new FieldNotFoundException("executor");
        }
        task.setExecutorid(executor.getId());
        tasksRepository.tranzactionEditExecutor(task.getName(), task.getExecutorid());
    }
}
