package me.code;

import java.util.Date;

public class Todo {

    private String name, description;
    private boolean completed;
    private Date completedDate;

    public Todo(String name, String description) {
        this.name = name;
        this.description = description;
        this.completed = false;
        this.completedDate = null;
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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }
}
