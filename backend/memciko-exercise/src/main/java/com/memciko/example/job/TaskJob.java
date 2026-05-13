package com.memciko.example.job;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;

public class TaskJob implements Job {

    @Override
    public void execute(JobExecutionContext context) {
        Long taskId = context.getMergedJobDataMap().getLong("taskId");
        String taskName = context.getMergedJobDataMap().getString("taskName");

        // Set job parameters:
        String message = context.getMergedJobDataMap().getString("message");

        System.out.println("Running task id=" + taskId + ", name=" + taskName);

        if (message != null) {
            System.out.println("Log message: " + message);
        }
    }
}