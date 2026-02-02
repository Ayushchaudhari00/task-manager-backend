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

    private final TaskRepo taskRepository;
    private final UserRepo userRepository;

    public List<Task> getTasksByUserEmail(String email) {
        return taskRepository.findByUserEmail(email);
    }

    public Task createTask(Task task, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        task.setUser(user);
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask, String email) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        
        if (!task.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized to update this task");
        }
        
        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());
        task.setPriority(updatedTask.getPriority());
        task.setDueDate(updatedTask.getDueDate());
        
        return taskRepository.save(task);
    }

    public void deleteTask(Long id, String email) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        
        if (!task.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized to delete this task");
        }
        
        taskRepository.deleteById(id);
    }
}
