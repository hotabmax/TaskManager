package com.hotabmax.taskmanager.controllers;

import com.google.gson.Gson;
import com.hotabmax.taskmanager.dtos_database.*;
import com.hotabmax.taskmanager.dtos_error.AppError;
import com.hotabmax.taskmanager.exceptions_database.*;
import com.hotabmax.taskmanager.entities.*;
import com.hotabmax.taskmanager.services.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
public class CustomerController {

    private Gson gson = new Gson();
    private final TasksService tasksService;
    private final CommentsService commentsService;
    private final UserService userService;
    private final StatusService statusService;
    private final PriorityService priorityService;

    private final String executorRole = "ROLE_EXECUTOR";

    public CustomerController(TasksService tasksService, CommentsService commentsService,
                              UserService userService, StatusService statusService,
                              PriorityService priorityService) {
        this.tasksService = tasksService;
        this.commentsService = commentsService;
        this.userService = userService;
        this.statusService = statusService;
        this.priorityService = priorityService;
    }


    @PostMapping("/customer/createTask")
    @ResponseBody
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Create Task object.", required = true, content = @Content(schema = @Schema(implementation = TaskRequest.class)))
    @Operation(summary = "Create task in customer interface", responses = {
            @ApiResponse(responseCode = "400", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppError.class)), description = "Unique field 'name' have duplicate"),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppError.class)), description = "You field '{field name}' not found in the database")
    })
    public void createTask(@RequestBody String data, Principal principal) throws UniqueFieldException, FieldNotFoundException {
        tasksService.createTask(gson.fromJson(data, TaskRequest.class), principal.getName());
    }


    @PostMapping("/customer/deleteTaskByName")
    @ResponseBody
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Delete Task object.", required = true, content = @Content(schema = @Schema(implementation = DeleteTaskRequest.class)))
    @Operation(summary = "Delete task in customer interface by name", responses = {
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppError.class)), description = "You field 'task' not found in the database")
    })
    public void deleteTaskByName(@RequestBody String data) throws FieldNotFoundException {
        tasksService.deleteByName(gson.fromJson(data, DeleteTaskRequest.class).getName());
    }


    @PostMapping("/customer/findStatusList")
    @ResponseBody
    @Operation(summary = "Find statuses", responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = StatusResponse.class)))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppError.class)), description = "Statuses not found in the database")
    })
    public List<StatusResponse> findStatusList() throws StatusesNotFoundExceprion {
        return statusService.findAll();
    }


    @PostMapping("/customer/findPriorityList")
    @ResponseBody
    @Operation(summary = "Find priorities", responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PriorityResponse.class)))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppError.class)), description = "Priority not found in the database")
    })
    public List<PriorityResponse> findPriorityList() throws PrioritiesNotFoundException {
        return priorityService.findAll();
    }


    @PostMapping("/customer/findExecutorsEmailList")
    @ResponseBody
    @Operation(summary = "Find executors", responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ExecutorResponse.class)))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppError.class)), description = "Executors not found in the database")
    })
    public List<ExecutorResponse> findExecutorsEmailList() throws ExecutorNotFoundException {
        return userService.findExecutors(executorRole);
    }


    @PostMapping("/customer/findTasksOfCustomer")
    @ResponseBody
    @Operation(summary = "Find task of customer", responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TaskResponse.class)))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppError.class)), description = "'You field 'task' not found in the database' or 'This customer no have tasks'")
    })
    public List<TaskResponse> findTasksOfCustomer(@RequestParam int page,@RequestParam int size, Principal principal) throws TasksNotFoundException {
        return tasksService.findByCustomerEmail(page, size, principal.getName());
    }


    @PostMapping("/customer/findAllTasks")
    @ResponseBody
    @Operation(summary = "Find all task of customer", responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TaskResponse.class)))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppError.class)), description = "Database no have tasks")
    })
    public List<TaskResponse> findAllTasks(@RequestParam int page,@RequestParam int size) throws AllTasksNotFoundException {
        return tasksService.findAll(page, size);
    }


    @PostMapping("/customer/createComment")
    @ResponseBody
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Create customer comment object. Need fields 'comment' and 'taskName' in JSON object", required = true, content = @Content(schema = @Schema(implementation = CreateCommentRequest.class)))
    @Operation(summary = "Create customer comment", responses = {
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppError.class)), description = "You field 'task' not found in the database")
    })
    public void createComment(@RequestBody String data, Principal principal){
        commentsService.createComment(gson.fromJson(data, CreateCommentRequest.class), principal.getName());
    }


    @PostMapping("/customer/findTaskComments")
    @ResponseBody
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Find all task comments objects. Need only field 'taskName' in JSON object", required = true, content = @Content(schema = @Schema(implementation = CommentRequest.class)))
    @Operation(summary = "Find all task comments", responses = {
                    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentResponse.class)))),
                    @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppError.class)), description = "'You field 'task' not found in the database' or 'This task no have comments'")
    })
    public List<CommentResponse> findTaksComments(@RequestBody String data) throws CommentsNotFoundException {
        return commentsService.findByTaskName(gson.fromJson(data, CommentRequest.class).getTaskName());
    }
}
