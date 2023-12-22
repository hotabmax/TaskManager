package com.hotabmax.taskmanager.exceptions_database;

public class FieldNotFoundException extends Exception{
    public FieldNotFoundException() {
    }

    public FieldNotFoundException(String message) {
        super(message);
    }
}
