package com.hotabmax.taskmanager.dtos_database;

public class PriorityResponse {
    private String name;

    public PriorityResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
