package com.hotabmax.taskmanager.controllers;

import com.google.gson.Gson;
import com.hotabmax.taskmanager.dtos_database.transactions.ChangeTaskDescriptionRequest;
import com.hotabmax.taskmanager.dtos_database.transactions.ChangeTaskExecutorRequest;
import com.hotabmax.taskmanager.dtos_database.transactions.ChangeTaskPriorityRequest;
import com.hotabmax.taskmanager.dtos_database.transactions.ChangeTaskStatusRequest;
import com.hotabmax.taskmanager.dtos_error.AppError;
import com.hotabmax.taskmanager.exceptions_database.FieldNotFoundException;
import com.hotabmax.taskmanager.services.TasksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerTransactionsController {
    private Gson gson = new Gson();

    private final TasksService tasksService;

    public CustomerTransactionsController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @PostMapping("/customer/changeDescriptionOfTask")
    @ResponseBody
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Change description in task object. Need fields 'name' and 'description' in JSON object", required = true, content = @Content(schema = @Schema(implementation = ChangeTaskDescriptionRequest.class)))
    @Operation(summary = "Change description of task", responses = {
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppError.class)), description = "You field '{field name}' not found in the database")
    })
    public void changeDescriptionOfTask(@RequestBody String data) throws FieldNotFoundException {
        tasksService.tranzactionEditDescription(gson.fromJson(data, ChangeTaskDescriptionRequest.class));
    }


    @PostMapping("/customer/changeStatusOfTask")
    @ResponseBody
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Change status in task object. Need fields 'name' and 'status' in JSON object", required = true, content = @Content(schema = @Schema(implementation = ChangeTaskStatusRequest.class)))
    @Operation(summary = "Change status of task", responses = {
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppError.class)), description = "You field '{field name}' not found in the database")
    })
    public void changeStatusOfTask(@RequestBody String data) throws FieldNotFoundException {
        tasksService.tranzactionEditStatus(gson.fromJson(data, ChangeTaskStatusRequest.class));
    }


    @PostMapping("/customer/changePriorityOfTask")
    @ResponseBody
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Change priority in task object. Need fields 'name' and 'priority' in JSON object.", required = true, content = @Content(schema = @Schema(implementation = ChangeTaskPriorityRequest.class)))
    @Operation(summary = "Change priority of task", responses = {
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppError.class)), description = "You field '{field name}' not found in the database")
    })
    public void changePriorityOfTask(@RequestBody String data) throws FieldNotFoundException {
        tasksService.tranzactionEditPriority(gson.fromJson(data, ChangeTaskPriorityRequest.class));
    }


    @PostMapping("/customer/changeExecutorOfTask")
    @ResponseBody
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Customer changed executor in task object. Need field 'name' and 'executorEmail' in JSON object.", required = true, content = @Content(schema = @Schema(implementation = ChangeTaskExecutorRequest.class)))
    @Operation(summary = "Change executor of task", responses = {
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppError.class)), description = "You field '{field name}' not found in the database")
    })
    public void changeExecutorOfTask(@RequestBody String data) throws FieldNotFoundException {
        tasksService.tranzactionEditExecutor(gson.fromJson(data, ChangeTaskExecutorRequest.class));
    }
}
