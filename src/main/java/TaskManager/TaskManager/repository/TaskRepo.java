package TaskManager.TaskManager.repository;

import TaskManager.TaskManager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId); 
    List<Task> findByUserEmail(String email); 
}
