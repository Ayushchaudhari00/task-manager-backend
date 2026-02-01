package TaskManager.TaskManager.Service;

import TaskManager.TaskManager.entity.Task;
import TaskManager.TaskManager.entity.User;
import TaskManager.TaskManager.repository.TaskRepo;
import TaskManager.TaskManager.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepo taskRepo;
    private final UserRepo userRepo;

    // ✅ CREATE TASK (CRITICAL FIX: SET USER)
    public Task createTask(Task task, String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        task.setUser(user); // 🔥 REQUIRED (THIS WAS YOUR BUG)

        return taskRepo.save(task);
    }

    // ✅ GET TASKS FOR LOGGED-IN USER
    public List<Task> getTasksByUser(String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return taskRepo.findByUser(user);
    }

    // ✅ UPDATE TASK (ONLY OWNER CAN UPDATE)
    public Task updateTask(Long id, Task updatedTask, String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());
        task.setPriority(updatedTask.getPriority());
        task.setDueDate(updatedTask.getDueDate());

        return taskRepo.save(task);
    }

    // ✅ DELETE TASK (ONLY OWNER)
    public void deleteTask(Long id, String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        taskRepo.delete(task);
    }
}
