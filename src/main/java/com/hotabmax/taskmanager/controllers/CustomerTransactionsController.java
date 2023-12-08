package com.hotabmax.taskmanager.controllers;

import com.google.gson.Gson;
import com.hotabmax.taskmanager.dtos.AppError;
import com.hotabmax.taskmanager.dtos.TaskRequest;
import com.hotabmax.taskmanager.models.Tasks;
import com.hotabmax.taskmanager.services.PriorityService;
import com.hotabmax.taskmanager.services.StatusService;
import com.hotabmax.taskmanager.services.TasksService;
import com.hotabmax.taskmanager.services.UserService;
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

@Controller
public class CustomerTransactionsController {
    private Gson gson = new Gson();

    @Autowired
    private TasksService tasksService;
    @Autowired
    private UserService userService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private PriorityService priorityService;

    @PostMapping("/customer/changeDescriptionOfTask")
    @ResponseBody
    @Operation(summary = "Change description of task", responses = {
                    @ApiResponse(responseCode = "200", description = "Response 'Great' if success"),
                    @ApiResponse(responseCode = "403", description = "You can't delete task another customer")
            })
    public ResponseEntity<?> changeDescriptionOfTask(@io.swagger.v3.oas.annotations.parameters.RequestBody(
                                            description = "Change description in task object. Need fields 'name' and 'description' in JSON object",
                                            required = true,
                                            content = @Content(
                                            schema = @Schema(implementation = TaskRequest.class)))@RequestBody String data, Principal principal){
        TaskRequest taksRequest = gson.fromJson(data, TaskRequest.class);
        Tasks DBtask = tasksService.findByName(taksRequest.getName());
        if (DBtask.getCustomerid().equals(userService.findByEmail(principal.getName()).getId())){
            tasksService.tranzactionEditDescription(taksRequest.getName(), taksRequest.getDescription());
            return ResponseEntity.ok("Great");
        } else return new ResponseEntity<>(new AppError(HttpStatus.FORBIDDEN.value(),
                                    "You can't delete task another customer"),
                                                HttpStatus.FORBIDDEN);
    }


    @PostMapping("/customer/changeStatusOfTask")
    @ResponseBody
    @Operation(summary = "Change status of task", responses = {
                    @ApiResponse(responseCode = "200", description = "Response 'Great' if success"),
                    @ApiResponse(responseCode = "403", description = "You can't delete task another customer")
            })
    public ResponseEntity<?> changeStatusOfTask(@io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    description = "Change status in task object. Need fields 'name' and 'status' in JSON object",
                                    required = true,
                                    content = @Content(
                                    schema = @Schema(implementation = TaskRequest.class)))@RequestBody String data, Principal principal){
        TaskRequest taksRequest = gson.fromJson(data, TaskRequest.class);
        Tasks DBtask = tasksService.findByName(taksRequest.getName());
        int statusid = statusService.findByName(taksRequest.getStatus()).getId();
        if (DBtask.getCustomerid().equals(userService.findByEmail(principal.getName()).getId())){
            tasksService.tranzactionEditStatus(taksRequest.getName(), statusid);
            return ResponseEntity.ok("Great");
        } else return new ResponseEntity<>(new AppError(HttpStatus.FORBIDDEN.value(),
                                    "You can't delete task another customer"),
                                                HttpStatus.FORBIDDEN);
    }


    @PostMapping("/customer/changePriorityOfTask")
    @ResponseBody
    @Operation(summary = "Change priority of task", responses = {
                    @ApiResponse(responseCode = "200", description = "Response 'Great' if success"),
                    @ApiResponse(responseCode = "403", description = "You can't delete task another customer")
            })
    public ResponseEntity<?> changePriorityOfTask(@io.swagger.v3.oas.annotations.parameters.RequestBody(
                                        description = "Change priority in task object. Need fields 'name' and 'priority' in JSON object.",
                                        required = true,
                                        content = @Content(
                                        schema = @Schema(implementation = TaskRequest.class)))@RequestBody String data, Principal principal){
        TaskRequest taksRequest = gson.fromJson(data, TaskRequest.class);
        Tasks DBtask = tasksService.findByName(taksRequest.getName());
        int priorityid = priorityService.findByName(taksRequest.getPriority()).getId();
        if (DBtask.getCustomerid().equals(userService.findByEmail(principal.getName()).getId())){
            tasksService.tranzactionEditPriority(taksRequest.getName(), priorityid);
            return ResponseEntity.ok("Great");
        } else return new ResponseEntity<>(new AppError(HttpStatus.FORBIDDEN.value(),
                                    "You can't delete task another customer"),
                                                HttpStatus.FORBIDDEN);
    }


    @PostMapping("/customer/changeExecutorOfTask")
    @ResponseBody
    @Operation(summary = "Change executor of task", responses = {
                    @ApiResponse(responseCode = "200", description = "Response 'Great' if success"),
                    @ApiResponse(responseCode = "403", description = "You can't delete task another customer")
            })
    public ResponseEntity<?> changeExecutorOfTask(@io.swagger.v3.oas.annotations.parameters.RequestBody(
                                        description = "Customer changed executor in task object. Need field 'name' and 'executorEmail' in JSON object.",
                                        required = true,
                                        content = @Content(
                                        schema = @Schema(implementation = TaskRequest.class)))@RequestBody String data, Principal principal){
        TaskRequest taksRequest = gson.fromJson(data, TaskRequest.class);
        Tasks DBtask = tasksService.findByName(taksRequest.getName());
        int executorid = userService.findByEmail(taksRequest.getExecutorEmail()).getId();
        if (DBtask.getCustomerid().equals(userService.findByEmail(principal.getName()).getId())){
            tasksService.tranzactionEditExecutor(taksRequest.getName(), executorid);
            return ResponseEntity.ok("Great");
        } else return new ResponseEntity<>(new AppError(HttpStatus.FORBIDDEN.value(),
                                    "You can't delete task another customer"),
                                                HttpStatus.FORBIDDEN);
    }
}
