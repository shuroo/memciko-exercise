package com.example.memciko.service;

import com.example.memciko.job.TaskJob;
import com.example.memciko.model.Task;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {

    private final Scheduler scheduler;
    private final Map<Long, Task> tasks = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public TaskService(Scheduler scheduler) {

        // Predefined tasks:

        this.scheduler = scheduler;

        createSystemTask("LOG_TASK", "Log Task", "Writes logs", "0/30 * * * * ?");
        createSystemTask("EMAIL_TASK", "Dummy Email Sender", "Sends dummy emails", "0 0/5 * * * ?");
        createSystemTask("DB_QUERY_TASK", "Dummy DB Query", "Runs dummy DB query", "0 0/10 * * * ?");

    }

    private void createSystemTask(
            String taskKey,
            String name,
            String description,
            String cronExpression
    ) {
        Task task = new Task();

        task.setId(idGenerator.getAndIncrement());
        task.setTaskKey(taskKey);
        task.setName(name);
        task.setDescription(description);
        task.setCronExpression(cronExpression);
        task.setActive(false);
        task.setEditable(false);
        task.setRunOnce(false);

        tasks.put(task.getId(), task);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public Task getTaskById(Long id) {
        Task task = tasks.get(id);

        if (task == null) {
            throw new RuntimeException("Task not found");
        }

        return task;
    }

    public Task createTask(Task task) throws SchedulerException {
        //Make sure that the task ( like 'log task') is not created twice. this also satisfies the pre defined tasks requirements
        boolean exists = tasks.values().stream()
                .anyMatch(existingTask ->
                        existingTask.getTaskKey() != null &&
                                existingTask.getTaskKey().equals(task.getTaskKey())
                );

        if (exists) {
            throw new RuntimeException("Task already exists: " + task.getTaskKey());
        }
        Long id = idGenerator.getAndIncrement();
        task.setId(id);

        tasks.put(id, task);

        if (task.isActive()) {
            scheduleTask(task);
        }

        return task;
    }

    public Task updateTask(Long id, Task updatedTask) throws SchedulerException {
        Task existingTask = getTaskById(id);

        if (!existingTask.isEditable()) {
            throw new RuntimeException("Task is not editable");
        }

        deleteScheduledJob(id);

        existingTask.setName(updatedTask.getName());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setCronExpression(updatedTask.getCronExpression());
        existingTask.setActive(updatedTask.isActive());
        existingTask.setEditable(updatedTask.isEditable());
        existingTask.setRunOnce(updatedTask.isRunOnce());
        existingTask.setStartAt(updatedTask.getStartAt());
        existingTask.setParameters(updatedTask.getParameters());

        if (existingTask.isActive()) {
            scheduleTask(existingTask);
        }

        return existingTask;
    }

    public void deleteTask(Long id) throws SchedulerException {
        Task existingTask = getTaskById(id);

        if (!existingTask.isEditable()) {
            throw new RuntimeException("Task is not editable");
        }

        deleteScheduledJob(id);
        tasks.remove(id);
    }

    private void scheduleTask(Task task) throws SchedulerException {

        JobDataMap jobDataMap = new JobDataMap();

        jobDataMap.put("taskId", task.getId());
        jobDataMap.put("taskName", task.getName());

        // Add dynamic task parameters
        if (task.getParameters() != null) {
            task.getParameters().forEach(jobDataMap::put);
        }

        JobDetail jobDetail = JobBuilder.newJob(TaskJob.class)
                .withIdentity("task-job-" + task.getId(), "tasks")
                .usingJobData(jobDataMap)
                .build();

        Trigger trigger;

        // One-time execution
        if (task.isRunOnce()) {

            trigger = TriggerBuilder.newTrigger()
                    .withIdentity("task-trigger-" + task.getId(), "tasks")
                    .startAt(
                            java.util.Date.from(
                                    task.getStartAt()
                                            .atZone(java.time.ZoneId.systemDefault())
                                            .toInstant()
                            )
                    )
                    .build();

        }
        // Recurring cron execution
        else {

            trigger = TriggerBuilder.newTrigger()
                    .withIdentity("task-trigger-" + task.getId(), "tasks")
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule(
                                    task.getCronExpression()
                            )
                    )
                    .build();
        }

        scheduler.scheduleJob(jobDetail, trigger);
    }

    private void deleteScheduledJob(Long id) throws SchedulerException {
        JobKey jobKey = new JobKey("task-job-" + id, "tasks");

        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
        }
    }
}