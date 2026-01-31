package TaskManager.TaskManager.Controller;


import TaskManager.TaskManager.Service.TaskService;
import TaskManager.TaskManager.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // ✅ GET all tasks for logged-in user
    @GetMapping
    public List<Task> getUserTasks(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return taskService.getTasksByUserEmail(userDetails.getUsername());
    }

    // ✅ CREATE task (THIS FIXES YOUR 403)
    @PostMapping
    public Task createTask(
            @RequestBody Task task,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return taskService.createTask(task, userDetails.getUsername());
    }

    // ✅ UPDATE task
    @PutMapping("/{id}")
    public Task updateTask(
            @PathVariable Long id,
            @RequestBody Task task
    ) {
        return taskService.updateTask(id, task);
    }

    // ✅ DELETE task
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}