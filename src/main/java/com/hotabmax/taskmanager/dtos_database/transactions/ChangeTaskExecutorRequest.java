package com.hotabmax.taskmanager.dtos_database.transactions;

public class ChangeTaskExecutorRequest {
    private String name;
    private String executorEmail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExecutorEmail() {
        return executorEmail;
    }

    public void setExecutorEmail(String executorEmail) {
        this.executorEmail = executorEmail;
    }
}
