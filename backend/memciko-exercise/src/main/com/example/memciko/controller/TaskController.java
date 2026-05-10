package main.com.example.memciko.controller;

import main.com.example.memciko.model.Task;
import main.com.example.memciko.service.TaskService;
import org.quartz.SchedulerException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }


    // To avoid creating the same task with api-key twice and get an error and return "bad request":
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleRuntimeException(RuntimeException ex) {
        return ex.getMessage();
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return service.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return service.getTaskById(id);
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) throws SchedulerException {
        return service.createTask(task);
    }

    @PutMapping("/{id}")
    public Task updateTask(
            @PathVariable Long id,
            @RequestBody Task task
    ) throws SchedulerException {
        return service.updateTask(id, task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) throws SchedulerException {
        service.deleteTask(id);
    }
}