package com.example.memciko.model;

import java.time.LocalDateTime;
import java.util.Map;

public class Task {

    private Long id;
    private String name;
    private String description;

    // For recurring Quartz jobs
    private String cronExpression;

    // Is this task currently scheduled/running
    private boolean active;

    // Can user edit/delete this task
    private boolean editable;

    // If true -> SimpleTrigger, if false -> CronTrigger
    private boolean runOnce;

    // Used only when runOnce = true
    private LocalDateTime startAt;

    // support task parameters:
    private Map<String, String> parameters;

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    private String taskKey;
// for special tasks to be created once.
public String getTaskKey() {
    return taskKey;
}

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public Task() {
    }

    public Task(Long id,
                String name,
                String description,
                String cronExpression,
                boolean active,
                boolean editable,
                boolean runOnce,
                LocalDateTime startAt, Map<String, String> parameters)  {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cronExpression = cronExpression;
        this.active = active;
        this.editable = editable;
        this.runOnce = runOnce;
        this.startAt = startAt;
        this.parameters = parameters;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isRunOnce() {
        return runOnce;
    }

    public void setRunOnce(boolean runOnce) {
        this.runOnce = runOnce;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }
}