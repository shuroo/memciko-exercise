import com.memciko.example.job.TaskJob;
import org.junit.jupiter.api.Test;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class TaskJobTest {

    @Test
    void execute_shouldRunWithoutErrors() {
        TaskJob taskJob = new TaskJob();

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("taskId", 1L);
        jobDataMap.put("taskName", "Log Task");
        jobDataMap.put("message", "Hello from test");

        JobExecutionContext context = mock(JobExecutionContext.class);

        when(context.getMergedJobDataMap()).thenReturn(jobDataMap);

        assertDoesNotThrow(() -> taskJob.execute(context));
    }
}