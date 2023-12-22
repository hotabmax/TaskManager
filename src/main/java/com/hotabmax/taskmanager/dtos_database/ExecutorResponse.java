package com.hotabmax.taskmanager.dtos_database;

public class ExecutorResponse {
    private String email;

    public ExecutorResponse(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
