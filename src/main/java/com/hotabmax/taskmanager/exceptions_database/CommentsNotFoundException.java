package com.hotabmax.taskmanager.exceptions_database;

public class CommentsNotFoundException extends Exception{
    public CommentsNotFoundException() {
    }

    public CommentsNotFoundException(String message) {
        super(message);
    }
}
