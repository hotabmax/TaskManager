package com.hotabmax.taskmanager.dtos_database;

public class StatusResponse {
    private String name;

    public StatusResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
