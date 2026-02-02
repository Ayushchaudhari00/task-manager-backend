package TaskManager.TaskManager.Controller;
import TaskManager.TaskManager.Service.TaskService;
import TaskManager.TaskManager.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public Task createTask(
            @RequestBody Task task,
            Authentication authentication
    ) {
        String email = authentication.getName();
        return taskService.createTask(task, email);
    }

    @GetMapping
    public List<Task> getMyTasks(Authentication authentication) {
        String email = authentication.getName();
        return taskService.getTasksByUserEmail(email);  // ← FIXED THIS LINE
    }

    @PutMapping("/{id}")
    public Task updateTask(
            @PathVariable Long id,
            @RequestBody Task updatedTask,
            Authentication authentication
    ) {
        String email = authentication.getName();
        return taskService.updateTask(id, updatedTask, email);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String email = authentication.getName();
        taskService.deleteTask(id, email);
    }
}
