package com.hotabmax.taskmanager.dtos_database.transactions;

public class ChangeTaskPriorityRequest {
    private String name;
    private String priority;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
