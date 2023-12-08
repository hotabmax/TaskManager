package com.hotabmax.taskmanager.controllers;

import com.google.gson.Gson;
import com.hotabmax.taskmanager.dtos.*;
import com.hotabmax.taskmanager.models.Comments;
import com.hotabmax.taskmanager.models.Priority;
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
public class ExecutorController {
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


    @PostMapping("/executor/findExecutorTasks")
    @ResponseBody
    @Operation(summary = "Find tasks of executor", responses = {
            @ApiResponse(responseCode = "200", description = "Response JSON object if success"),
            @ApiResponse(responseCode = "404", description = "No tasks created in data base")
    })
    public ResponseEntity<?> findExecutorTasks(Principal principal){
        List<Tasks> tasks = tasksService.findByExecutorId((userService.findByEmail(principal.getName()).getId()));
        List<TaskResponse> taskResponses = new ArrayList<>();
        if (!tasks.isEmpty()){
            for(Tasks t : tasks){
                taskResponses.add(new TaskResponse(t.getName(), t.getDescription(),
                        statusService.findById(t.getStatusid()).getName(), priorityService.findById(t.getPriorityid()).getName(),
                        userService.findById(t.getCustomerid()).getEmail(), userService.findById(t.getExecutorid()).getEmail()));
            }
            return ResponseEntity.ok(taskResponses);
        } else return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                                    "No tasks created"),
                                                HttpStatus.NOT_FOUND);
    }


    @PostMapping("/executor/changeStatusOfTask")
    @ResponseBody
    @Operation(summary = "Change stasus of task", responses = {
                    @ApiResponse(responseCode = "200", description = "Response 'Great' if success"),
                    @ApiResponse(responseCode = "403", description = "You can't delete task another customer")
            })
    public ResponseEntity<?> changeStatusOfTask(@io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = "Change stasus in task object. Need field 'name' and 'status' in JSON object.",
                                    required = true, content = @Content(
                                    schema = @Schema(implementation = TaskRequest.class)))@RequestBody String data, Principal principal){
        TaskRequest taksRequest = gson.fromJson(data, TaskRequest.class);
        Tasks DBtask = tasksService.findByName(taksRequest.getName());
        int statusid = statusService.findByName(taksRequest.getStatus()).getId();
        if (DBtask.getExecutorid().equals(userService.findByEmail(principal.getName()).getId())){
            tasksService.tranzactionEditStatus(taksRequest.getName(), statusid);
            return ResponseEntity.ok("Great");
        } else return new ResponseEntity<>(new AppError(HttpStatus.FORBIDDEN.value(),
                                                "You can't delete task another customer"),
                                                            HttpStatus.FORBIDDEN);
    }


    @PostMapping("/executor/createComment")
    @ResponseBody
    @Operation(summary = "Create executor comment", responses = {
                    @ApiResponse(responseCode = "200", description = "Response 'Great' if success"),
                    @ApiResponse(responseCode = "422", description = "The server was unable to process the request")
            })
    public ResponseEntity<?> createComment(@io.swagger.v3.oas.annotations.parameters.RequestBody(
                                description = "Create executor comment object. Need fields 'comment' and 'taskName' in JSON object",
                                required = true,
                                content = @Content(
                                schema = @Schema(implementation = CommentRequest.class)))@RequestBody String data, Principal principal){
        CommentRequest commentRequest = gson.fromJson(data, CommentRequest.class);
        int taskid = tasksService.findByName(commentRequest.getTaskName()).getId();
        int amount = commentsService.findByTaskId(taskid).size();
        User user = userService.findByEmail(principal.getName());
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


    @PostMapping("/executor/findTaskComments")
    @ResponseBody
    @Operation(summary = "Find task comments", responses = {
                    @ApiResponse(responseCode = "200", description = "Response JSON object if success"),
                    @ApiResponse(responseCode = "404", description = "No tasks created in data base")
            })
    public ResponseEntity<?> findTaksComments(@io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = "Find task comments objects. Need only field 'taskName' in JSON object",
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
