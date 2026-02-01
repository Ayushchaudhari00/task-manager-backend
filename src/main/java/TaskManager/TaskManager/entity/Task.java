package TaskManager.TaskManager.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(length = 500)
    private String description;
    
    // TODO, IN_PROGRESS, COMPLETED
    @Column(nullable = false)
    private String status;
    
    // LOW, MEDIUM, HIGH
    @Column(nullable = false)
    private String priority;
    
    private LocalDate dueDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // ← FIXED: Added "user" variable name
    
    @Builder.Default
    private LocalDate createdAt = LocalDate.now();
}
