package com.hotabmax.taskmanager.controllers;

import com.google.gson.Gson;
import com.hotabmax.taskmanager.dtos_database.*;
import com.hotabmax.taskmanager.dtos_database.transactions.ChangeTaskStatusRequest;
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
public class ExecutorController {
    private Gson gson = new Gson();

    private final TasksService tasksService;
    private final CommentsService commentsService;
    private final StatusService statusService;
    private final PriorityService priorityService;

    public ExecutorController(TasksService tasksService, CommentsService commentsService,
                              StatusService statusService, PriorityService priorityService) {
        this.tasksService = tasksService;
        this.commentsService = commentsService;
        this.statusService = statusService;
        this.priorityService = priorityService;
    }


    @PostMapping("/executor/findExecutorTasks")
    @ResponseBody
    @Operation(summary = "Find tasks of executor", responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TaskResponse.class)))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppError.class)), description = "'You field 'task' not found in the database' or 'This executor no have tasks'")
    })
    public List<TaskResponse> findExecutorTasks(@RequestParam int page, @RequestParam int size, Principal principal) throws TasksNotFoundException {
        return tasksService.findByExecutorEmail(page, size, principal.getName());
    }

    @PostMapping("/executor/findStatusList")
    @ResponseBody
    @Operation(summary = "Find statuses", responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = StatusResponse.class)))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppError.class)), description = "Statuses not found in the database")
    })
    public List<StatusResponse> findStatusList() throws StatusesNotFoundExceprion {
        return statusService.findAll();
    }

    @PostMapping("/executor/findPriorityList")
    @ResponseBody
    @Operation(summary = "Find priorities", responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PriorityResponse.class)))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppError.class)), description = "Priority not found in the database")
    })
    public List<PriorityResponse> findPriorityList() throws PrioritiesNotFoundException {
        return priorityService.findAll();
    }


    @PostMapping("/executor/changeStatusOfTask")
    @ResponseBody
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Change stasus in task object. Need field 'name' and 'status' in JSON object.", required = true, content = @Content(schema = @Schema(implementation = ChangeTaskStatusRequest.class)))
    @Operation(summary = "Change stasus of task", responses = {
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppError.class)), description = "You field '{field name}' not found in the database")
    })
    public void changeStatusOfTask(@RequestBody String data) throws FieldNotFoundException {
        tasksService.tranzactionEditStatus(gson.fromJson(data, ChangeTaskStatusRequest.class));
    }


    @PostMapping("/executor/createComment")
    @ResponseBody
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Create executor comment object. Need fields 'comment' and 'taskName' in JSON object", required = true, content = @Content(schema = @Schema(implementation = CreateCommentRequest.class)))
    @Operation(summary = "Create executor comment", responses = {
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppError.class)), description = "You field 'task' not found in the database")
    })
    public void createComment(@RequestBody String data, Principal principal){
        commentsService.createComment(gson.fromJson(data, CreateCommentRequest.class), principal.getName());
    }


    @PostMapping("/executor/findTaskComments")
    @ResponseBody
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Find task comments objects. Need only field 'taskName' in JSON object", required = true, content = @Content(schema = @Schema(implementation = CommentRequest.class)))
    @Operation(summary = "Find all task comments", responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentResponse.class)))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppError.class)), description = "'You field 'task' not found in the database' or 'This task no have comments'")
    })
    public List<CommentResponse> findTaksComments(@RequestBody String data) throws CommentsNotFoundException {
        return commentsService.findByTaskName(gson.fromJson(data, CommentRequest.class).getTaskName());
    }
}
