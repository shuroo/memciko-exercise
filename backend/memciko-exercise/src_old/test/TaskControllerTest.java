import com.example.memciko.controller.TaskController;
import com.example.memciko.model.Task;
import com.example.memciko.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.quartz.SchedulerException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TaskControllerTest {

    private final TaskService taskService = mock(TaskService.class);
    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new TaskController(taskService))
            .build();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAllTasks_shouldReturnTasks() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setName("Log Task");
        task.setActive(true);
        task.setEditable(false);

        when(taskService.getAllTasks()).thenReturn(List.of(task));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Log Task"));
    }

    @Test
    void createTask_shouldCreateTask() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setName("New Task");
        task.setActive(true);
        task.setEditable(true);

        when(taskService.createTask(any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New Task"));
    }

    @Test
    void updateTask_shouldUpdateTask() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setName("Updated Task");
        task.setActive(true);
        task.setEditable(true);

        when(taskService.updateTask(eq(1L), any(Task.class))).thenReturn(task);

        mockMvc.perform(put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Task"));
    }

    @Test
    void deleteTask_shouldDeleteTask() throws Exception {
        doNothing().when(taskService).deleteTask(1L);

        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isOk());

        verify(taskService, times(1)).deleteTask(1L);
    }
}