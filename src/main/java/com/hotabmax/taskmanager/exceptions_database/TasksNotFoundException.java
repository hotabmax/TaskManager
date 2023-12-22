package com.hotabmax.taskmanager.exceptions_database;

public class TasksNotFoundException extends Exception{
    public TasksNotFoundException() {
    }

    public TasksNotFoundException(String message) {
        super(message);
    }
}
