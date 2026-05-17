package com.memciko.example.model;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Represents a schedulable task managed by the application.
 *
 * @author shiri rave
 * @since 05/26
 *
 * <p>
 * A task can be executed either:
 * </p>
 * <ul>
 *     <li>As a recurring Quartz cron job</li>
 *     <li>As a one-time scheduled execution</li>
 * </ul>
 *
 * <p>
 * Tasks may also contain dynamic execution parameters that are passed
 * into the Quartz JobDataMap during execution.
 * </p>
 *
 * <p>
 * Some tasks are predefined system tasks and are marked as non-editable.
 * </p>
 *
 * @author Shiri Rave
 * @since 05/2026
 */
public class Task {

    /**
     * Unique task identifier.
     */
    private Long id;

    /**
     * Display name of the task.
     */
    private String name;

    /**
     * Task description.
     */
    private String description;

    /**
     * Quartz cron expression used for recurring executions.
     *
     * <p>
     * Used only when {@code runOnce = false}.
     * </p>
     */
    private String cronExpression;

    /**
     * Indicates whether the task is currently active and scheduled.
     */
    private boolean active;

    /**
     * Indicates whether the task can be modified or deleted by users.
     *
     * <p>
     * System tasks are typically non-editable.
     * </p>
     */
    private boolean editable;

    /**
     * Determines execution type.
     *
     * <ul>
     *     <li>{@code true} - execute once using a SimpleTrigger</li>
     *     <li>{@code false} - recurring execution using a CronTrigger</li>
     * </ul>
     */
    private boolean runOnce;

    /**
     * Start date and time for one-time execution.
     *
     * <p>
     * Used only when {@code runOnce = true}.
     * </p>
     */
    private LocalDateTime startAt;

    /**
     * Dynamic parameters passed into the Quartz JobDataMap
     * during task execution.
     */
    private Map<String, String> parameters;

    /**
     * Unique business key used to prevent duplicate task creation.
     *
     * <p>
     * Mainly used for predefined system tasks.
     * </p>
     */
    private String taskKey;

    /**
     * Default constructor.
     */
    public Task() {
    }

    /**
     * Creates a fully initialized task instance.
     *
     * @param id task identifier
     * @param name task display name
     * @param description task description
     * @param cronExpression Quartz cron expression
     * @param active indicates whether task is active
     * @param editable indicates whether task is editable
     * @param runOnce indicates whether task runs once
     * @param startAt execution date for one-time tasks
     * @param parameters dynamic execution parameters
     */
    public Task(Long id,
                String name,
                String description,
                String cronExpression,
                boolean active,
                boolean editable,
                boolean runOnce,
                LocalDateTime startAt,
                Map<String, String> parameters) {

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

    /**
     * Returns task parameters.
     *
     * @return task parameters map
     */
    public Map<String, String> getParameters() {
        return parameters;
    }

    /**
     * Sets task parameters.
     *
     * @param parameters task parameters map
     */
    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    /**
     * Returns task business key.
     *
     * @return task key
     */
    public String getTaskKey() {
        return taskKey;
    }

    /**
     * Sets task business key.
     *
     * @param taskKey unique task key
     */
    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    /**
     * Returns task identifier.
     *
     * @return task id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets task identifier.
     *
     * @param id task id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns task name.
     *
     * @return task name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets task name.
     *
     * @param name task name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns task description.
     *
     * @return task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets task description.
     *
     * @param description task description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns Quartz cron expression.
     *
     * @return cron expression
     */
    public String getCronExpression() {
        return cronExpression;
    }

    /**
     * Sets Quartz cron expression.
     *
     * @param cronExpression cron expression
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    /**
     * Returns whether task is active.
     *
     * @return true if active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets task active state.
     *
     * @param active active state
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Returns whether task is editable.
     *
     * @return true if editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * Sets task editable state.
     *
     * @param editable editable state
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * Returns whether task is configured for one-time execution.
     *
     * @return true if task runs once
     */
    public boolean isRunOnce() {
        return runOnce;
    }

    /**
     * Sets execution type.
     *
     * @param runOnce true for one-time execution
     */
    public void setRunOnce(boolean runOnce) {
        this.runOnce = runOnce;
    }

    /**
     * Returns one-time execution date.
     *
     * @return start date and time
     */
    public LocalDateTime getStartAt() {
        return startAt;
    }

    /**
     * Sets one-time execution date.
     *
     * @param startAt execution date and time
     */
    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }
}