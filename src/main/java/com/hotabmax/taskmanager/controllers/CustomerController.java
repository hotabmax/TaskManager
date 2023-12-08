package com.hotabmax.taskmanager.controllers;

import com.google.gson.Gson;
import com.hotabmax.taskmanager.dtos.*;
import com.hotabmax.taskmanager.models.Comments;
import com.hotabmax.taskmanager.models.Tasks;
import com.hotabmax.taskmanager.models.User;
import com.hotabmax.taskmanager.services.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Controller
public class CustomerController {

    private Gson gson = new Gson();
    @Autowired
    private TasksService tasksService;
    @Autowired
    private CommentsService commentsService;
    @Autowired
    private UserService userService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private PriorityService priorityService;


    @PostMapping("/customer/createTask")
    @ResponseBody
    @Operation(summary = "Create task in customer interface", responses = {
            @ApiResponse(responseCode = "200", description = "Response 'Great' if success"),
            @ApiResponse(responseCode = "422", description = "The server was unable to process the request")
    })
    public ResponseEntity<?> createTask(@io.swagger.v3.oas.annotations.parameters.RequestBody(
                                description = "Create Task object. Needs fields 'name', 'description', 'status', 'priority', 'executorEmail' and in JSON object",
                                required = true,
                                content = @Content(
                                schema = @Schema(implementation = TaskRequest.class))) @RequestBody String data, Principal principal){
        TaskRequest taksRequest = gson.fromJson(data, TaskRequest.class);
        int statusid = statusService.findByName(taksRequest.getStatus()).getId();
        int priorytiid = priorityService.findByName(taksRequest.getPriority()).getId();
        int customerid = userService.findByEmail(principal.getName()).getId();
        int executorid = userService.findByEmail(taksRequest.getExecutorEmail()).getId();
        tasksService.createTask(new Tasks(taksRequest.getName(), taksRequest.getDescription(),
                                            statusid, priorytiid, customerid, executorid));
        if (tasksService.findByName(taksRequest.getName()) != null){
            return ResponseEntity.ok("Great");
        } else return new ResponseEntity<>(new AppError(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                                    "The server was unable to process the request"),
                                                HttpStatus.UNPROCESSABLE_ENTITY);

    }


    @PostMapping("/customer/deleteTaskByName")
    @ResponseBody
    @Operation(summary = "Delete task in customer interface by name", responses = {
                    @ApiResponse(responseCode = "200", description = "Response 'Great' if success"),
                    @ApiResponse(responseCode = "403", description = "You can't delete task another customer"),
                    @ApiResponse(responseCode = "422", description = "The server was unable to process the request")
            })
    public ResponseEntity<?> deleteTaskByName(@io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = "Delete Task object.",
                                    required = true,
                                    content = @Content(
                                    schema = @Schema(implementation = Tasks.class))) @RequestBody String data, Principal principal){
        Tasks task = gson.fromJson(data, Tasks.class);
        Tasks DBtask = tasksService.findByName(task.getName());
        if (DBtask.getCustomerid() == userService.findByEmail(principal.getName()).getId()){
            tasksService.deleteByName(task.getName());
            if (tasksService.findByName(task.getName()) == null){
                return ResponseEntity.ok("Great");
            } else return new ResponseEntity<>(new AppError(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                                        "The server was unable to process the request"),
                                                    HttpStatus.UNPROCESSABLE_ENTITY);
        } else return new ResponseEntity<>(new AppError(HttpStatus.FORBIDDEN.value(),
                                        "You can't delete task another customer"),
                                                    HttpStatus.FORBIDDEN);
    }


    @PostMapping("/customer/findTasksOfCustomer")
    @ResponseBody
    @Operation(summary = "Find task of customer", responses = {
                    @ApiResponse(responseCode = "200", description = "Response JSON object if success"),
                    @ApiResponse(responseCode = "404", description = "No tasks created in data base")
            })
    public ResponseEntity<?> findTasksOfCustomer(Principal principal){
        List<Tasks> tasks = tasksService.findByCustomerId(userService.findByEmail(principal.getName()).getId());
        List<TaskResponse> taskResponses = new ArrayList<>();
        if (!tasks.isEmpty()){
            for(Tasks t : tasks){
                taskResponses.add(new TaskResponse(t.getName(), t.getDescription(),
                        statusService.findById(t.getStatusid()).getName(), priorityService.findById(t.getPriorityid()).getName(),
                        userService.findById(t.getCustomerid()).getEmail(), userService.findById(t.getExecutorid()).getEmail()));
            }
            return ResponseEntity.ok(taskResponses);
        } else return new  ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                                    "No tasks created"),
                                                HttpStatus.NOT_FOUND);
    }


    @PostMapping("/customer/findAllTasks")
    @ResponseBody
    @Operation(summary = "Find all task of customer", responses = {
                    @ApiResponse(responseCode = "200", description = "Response JSON object if success"),
                    @ApiResponse(responseCode = "422", description = "The server was unable to process the request")
            })
    public ResponseEntity<?> findAllTasks(){
        List<Tasks> tasks = tasksService.findAll();
        List<TaskResponse> taskResponses = new ArrayList<>();
        if (!tasks.isEmpty()){
            for(Tasks t : tasks){
                taskResponses.add(new TaskResponse(t.getName(), t.getDescription(),
                        statusService.findById(t.getStatusid()).getName(), priorityService.findById(t.getPriorityid()).getName(),
                        userService.findById(t.getCustomerid()).getEmail(), userService.findById(t.getExecutorid()).getEmail()));
            }
            return ResponseEntity.ok(taskResponses);
        } else return new ResponseEntity<>(new AppError(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                                    "The server was unable to process the request"),
                                                HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @PostMapping("/customer/createComment")
    @ResponseBody
    @Operation(summary = "Create customer comment", responses = {
                    @ApiResponse(responseCode = "200", description = "Response 'Great' if success"),
                    @ApiResponse(responseCode = "422", description = "The server was unable to process the request")
            })
    public ResponseEntity<?> createComment(@io.swagger.v3.oas.annotations.parameters.RequestBody(
                                description = "Create customer comment object. Need fields 'comment' and 'taskName' in JSON object",
                                required = true,
                                content = @Content(
                                schema = @Schema(implementation = CommentRequest.class)))@RequestBody String data, Principal principal){
        CommentRequest commentRequest = gson.fromJson(data, CommentRequest.class);
        User user = userService.findByEmail(principal.getName());
        int taskid = tasksService.findByName(commentRequest.getTaskName()).getId();
        int amount = commentsService.findByTaskId(tasksService.findByName(commentRequest.getTaskName()).getId()).size();
        commentsService.createComment(new Comments(
                                    user.getLastname(), user.getFirstname(),
                                    user.getSurname(), user.getEmail(),
                                    commentRequest.getComment(), taskid));
        if (commentsService.findAll().size() > amount){
            return ResponseEntity.ok("Great");
        } else return new ResponseEntity<>(new AppError(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                                         "The server was unable to process the request"),
                                                    HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @PostMapping("/customer/findTaskComments")
    @ResponseBody
    @Operation(summary = "Find all task comments ", responses = {
                    @ApiResponse(responseCode = "200", description = "Response JSON object if success"),
                    @ApiResponse(responseCode = "404", description = "No comments created in data base")
            })
    public ResponseEntity<?> findTaksComments(@io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = "Find all task comments objects. Need only field 'taskName' in JSON object",
                                    required = true,
                                    content = @Content(
                                    schema = @Schema(implementation = CommentRequest.class)))@RequestBody String data){
        CommentRequest commentRequest = gson.fromJson(data, CommentRequest.class);
        List<Comments> comment = commentsService.findByTaskId(tasksService.findByName(commentRequest.getTaskName()).getId());
        List<CommentResponse> commentResponses = new ArrayList<>();
        if (!comment.isEmpty()){
            for(Comments c : comment){
                commentResponses.add(new CommentResponse(c.getLastname(),c.getFirstname(), c.getSurname(), c.getEmail(), c.getComment(),
                                                            tasksService.findById(c.getTaskid()).getName()));
            }
            return ResponseEntity.ok(commentResponses);
        } else return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                                    "No tasks created"),
                                                HttpStatus.NOT_FOUND);
    }
}
