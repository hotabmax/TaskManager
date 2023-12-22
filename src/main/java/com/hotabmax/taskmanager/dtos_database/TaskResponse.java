package com.hotabmax.taskmanager.dtos_database;

public class TaskResponse {
    private String name;
    private String description;
    private String status;
    private String priority;
    private String customer;
    private String executorEmail;

    public TaskResponse(String name, String description, String status, String priority, String customer, String executorEmail) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.customer = customer;
        this.executorEmail = executorEmail;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getExecutorEmail() {
        return executorEmail;
    }

    public void setExecutorEmail(String executorEmail) {
        this.executorEmail = executorEmail;
    }
}
