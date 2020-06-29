package com.rsynytskyi.tasktracker.model;

public enum TaskStatus {
    VIEW("View"),
    IN_PROGRESS("In Progress"),
    DONE("Done");

    private String taskStatus;

    private TaskStatus(final String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getState() {
        return taskStatus;
    }

    @Override
    public String toString(){
        return this.taskStatus;
    }

    public String getName(){
        return this.name();
    }
}
