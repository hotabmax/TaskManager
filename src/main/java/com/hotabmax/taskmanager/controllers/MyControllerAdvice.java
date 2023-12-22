package com.hotabmax.taskmanager.controllers;

import com.hotabmax.taskmanager.dtos_error.AppError;
import com.hotabmax.taskmanager.exceptions_database.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyControllerAdvice {
    @ExceptionHandler(value = {FieldNotFoundException.class})
    protected ResponseEntity<?> handleStatusNotFoundException(FieldNotFoundException e) {
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                "You field '"+e.getMessage()+"' not found in the database"),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {UniqueFieldException.class})
    protected ResponseEntity<?> handleUniqueFieldException() {
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),
                "Unique field 'name' have duplicate"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {TasksNotFoundException.class})
    protected ResponseEntity<?> handleTasksNotFoundException(TasksNotFoundException e) {
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                "This "+e.getMessage()+" no have tasks"),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {AllTasksNotFoundException.class})
    protected ResponseEntity<?> handleAllTasksNotFoundException() {
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                "Database no have tasks"),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {CommentsNotFoundException.class})
    protected ResponseEntity<?> handleCommentsNotFoundException() {
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                "This task no have comments"),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {StatusesNotFoundExceprion.class})
    protected ResponseEntity<?> handleStatusesNotFoundExceprion() {
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                "Statuses not found in the database"),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {PrioritiesNotFoundException.class})
    protected ResponseEntity<?> handlePrioritiesNotFoundException() {
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                "Priorities not found in the database"),
                HttpStatus.NOT_FOUND);
    }
}
