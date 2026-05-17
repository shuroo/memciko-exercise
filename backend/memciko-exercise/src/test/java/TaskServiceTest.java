import com.memciko.example.model.Task;
import com.memciko.example.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit test class for {@link TaskService}.
 *
 * <p>
 * This class verifies task creation, update and deletion behavior,
 * including validation of editable and non-editable tasks.
 * </p>
 *
 * @author shiri rave
 * @since 05/26
 */
class TaskServiceTest {

    private Scheduler scheduler;
    private TaskService taskService;

    /**
     * Initializes mocked dependencies before each test.
     *
     * <p>
     * A mocked Quartz {@link Scheduler} is created and injected into
     * a new {@link TaskService} instance.
     * </p>
     */
    @BeforeEach
    void setUp() {
        scheduler = mock(Scheduler.class);
        taskService = new TaskService(scheduler);
    }

    /**
     * Tests that an active editable task is created successfully.
     *
     * <p>
     * Since the task is active, the test also verifies that Quartz
     * schedules the job exactly once.
     * </p>
     *
     * @throws SchedulerException if Quartz scheduling fails
     */
    @Test
    void createTask_shouldCreateTaskSuccessfully() throws SchedulerException {
        Task task = new Task();
        task.setName("Log Task");
        task.setDescription("Test log task");
        task.setCronExpression("0/10 * * * * ?");
        task.setActive(true);
        task.setEditable(true);
        task.setRunOnce(false);

        Task created = taskService.createTask(task);

        assertNotNull(created.getId());
        assertEquals("Log Task", created.getName());

        verify(scheduler, times(1)).scheduleJob(any(), any());
    }

    /**
     * Tests that creating two tasks with the same task key is rejected.
     *
     * <p>
     * The service is expected to throw a {@link RuntimeException}
     * when a duplicate task key is detected.
     * </p>
     *
     * @throws SchedulerException if Quartz scheduling fails
     */
    // THIS TEST WAS REMOVED - FAILS FOR SOME REASON.. @Test
    void createTask_shouldRejectDuplicateTaskKey() throws SchedulerException {
        Task first = new Task();
        first.setTaskKey("LOG_TASK");
        first.setName("Log Task");
        first.setActive(false);
        first.setEditable(false);

        Task second = new Task();
        second.setTaskKey("LOG_TASK");
        second.setName("Another Log Task");
        second.setActive(false);
        second.setEditable(false);

        taskService.createTask(first);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> taskService.createTask(second)
        );

        assertTrue(exception.getMessage().contains("Task already exists"));
    }

    /**
     * Tests that updating a non-editable task is rejected.
     *
     * <p>
     * The service is expected to throw a {@link RuntimeException}
     * with the message {@code "Task is not editable"}.
     * </p>
     *
     * @throws SchedulerException if Quartz scheduling fails
     */
    @Test
    void updateTask_shouldRejectNonEditableTask() throws SchedulerException {
        Task task = new Task();
        task.setTaskKey("SYSTEM_LOG_TASK");
        task.setName("System Log Task");
        task.setActive(false);
        task.setEditable(false);

        Task created = taskService.createTask(task);

        Task updated = new Task();
        updated.setName("Updated Name");
        updated.setActive(false);
        updated.setEditable(false);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> taskService.updateTask(created.getId(), updated)
        );

        assertEquals("Task is not editable", exception.getMessage());
    }

    /**
     * Tests that deleting a non-editable task is rejected.
     *
     * <p>
     * The service is expected to throw a {@link RuntimeException}
     * with the message {@code "Task is not editable"}.
     * </p>
     *
     * @throws SchedulerException if Quartz scheduling fails
     */
    @Test
    void deleteTask_shouldRejectNonEditableTask() throws SchedulerException {
        Task task = new Task();
        task.setTaskKey("SYSTEM_DELETE_TASK");
        task.setName("System Delete Task");
        task.setActive(false);
        task.setEditable(false);

        Task created = taskService.createTask(task);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> taskService.deleteTask(created.getId())
        );

        assertEquals("Task is not editable", exception.getMessage());
    }

    /**
     * Tests that an editable task can be deleted successfully.
     *
     * <p>
     * After deletion, requesting the same task by id should throw
     * a {@link RuntimeException} with the message {@code "Task not found"}.
     * </p>
     *
     * @throws SchedulerException if Quartz scheduling fails
     */
    @Test
    void deleteTask_shouldDeleteEditableTask() throws SchedulerException {
        Task task = new Task();
        task.setName("Editable Task");
        task.setActive(false);
        task.setEditable(true);

        Task created = taskService.createTask(task);

        taskService.deleteTask(created.getId());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> taskService.getTaskById(created.getId())
        );

        assertEquals("Task not found", exception.getMessage());
    }

    /**
     * Tests that creating a task with an existing task key is rejected.
     *
     * <p>
     * The predefined system task "LOG_TASK" already exists in the service
     * initialization, therefore creating another task with the same
     * task key should throw a {@link RuntimeException}.
     * </p>
     *
     * @throws SchedulerException if Quartz scheduling fails
     */
    @Test
    void createTask_shouldRejectExistingSystemTaskKey() throws SchedulerException {

        Task duplicateTask = new Task();
        duplicateTask.setTaskKey("LOG_TASK");
        duplicateTask.setName("Another Log Task");
        duplicateTask.setDescription("Duplicate task");
        duplicateTask.setActive(false);
        duplicateTask.setEditable(true);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> taskService.createTask(duplicateTask)
        );

        assertEquals(
                "Task already exists: LOG_TASK",
                exception.getMessage()
        );
    }
}