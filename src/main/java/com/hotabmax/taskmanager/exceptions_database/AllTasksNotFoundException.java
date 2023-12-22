package com.hotabmax.taskmanager.exceptions_database;

public class AllTasksNotFoundException extends Exception{
    public AllTasksNotFoundException() {
    }

    public AllTasksNotFoundException(String message) {
        super(message);
    }
}
