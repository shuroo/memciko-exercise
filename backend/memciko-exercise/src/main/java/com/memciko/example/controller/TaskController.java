package com.memciko.example.controller;

import com.memciko.example.model.Task;
import com.memciko.example.service.TaskService;
import org.quartz.SchedulerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller responsible for managing task scheduling operations.
 *
 * <p>
 * Provides API endpoints for:
 * </p>
 * <ul>
 *     <li>Retrieving tasks</li>
 *     <li>Creating new tasks</li>
 *     <li>Updating existing tasks</li>
 *     <li>Deleting tasks</li>
 * </ul>
 *
 * <p>
 * Supports both recurring Quartz cron jobs and one-time scheduled tasks.
 * </p>
 *
 * @author Shiri Rave
 * @since 05/2026
 */
@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {

    private final TaskService service;

    /**
     * Creates controller instance with injected task service.
     *
     * @param service task service implementation
     */
    public TaskController(TaskService service) {
        this.service = service;
    }

    /**
     * Handles runtime exceptions and returns HTTP 400 response.
     *
     * <p>
     * Mainly used for validation and business logic errors such as:
     * </p>
     * <ul>
     *     <li>Duplicate task creation</li>
     *     <li>Task not found</li>
     *     <li>Non-editable task modification attempts</li>
     * </ul>
     *
     * @param ex thrown runtime exception
     * @return exception message
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleRuntimeException(RuntimeException ex) {
        return ex.getMessage();
    }

    /**
     * Returns all registered tasks.
     *
     * @return list of all tasks
     */
    @GetMapping
    public List<Task> getAllTasks() {
        return service.getAllTasks();
    }

    /**
     * Returns a task by its identifier.
     *
     * @param id task identifier
     * @return matching task
     */
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return service.getTaskById(id);
    }

    /**
     * Creates a new task.
     *
     * <p>
     * If the task is marked as active, it will be immediately scheduled
     * using Quartz Scheduler.
     * </p>
     *
     * @param task task to create
     * @return created task
     * @throws SchedulerException if Quartz scheduling fails
     */
    @PostMapping
    public Task createTask(@RequestBody Task task) throws SchedulerException {
        return service.createTask(task);
    }

    /**
     * Updates an existing task.
     *
     * <p>
     * Existing scheduled jobs are replaced with updated scheduling data.
     * </p>
     *
     * @param id task identifier
     * @param task updated task data
     * @return updated task
     * @throws SchedulerException if Quartz scheduling fails
     */
    @PutMapping("/{id}")
    public Task updateTask(
            @PathVariable Long id,
            @RequestBody Task task
    ) throws SchedulerException {
        return service.updateTask(id, task);
    }

    /**
     * Deletes a task and removes its scheduled Quartz job.
     *
     * @param id task identifier
     * @throws SchedulerException if Quartz deletion fails
     */
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) throws SchedulerException {
        service.deleteTask(id);
    }
}