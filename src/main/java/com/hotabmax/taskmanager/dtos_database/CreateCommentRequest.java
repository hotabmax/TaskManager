package com.hotabmax.taskmanager.dtos_database;

public class CreateCommentRequest {
    private String comment;
    private String taskName;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
