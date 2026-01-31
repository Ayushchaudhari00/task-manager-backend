package TaskManager.TaskManager.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskRequestDto {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotBlank(message = "Status is required")
    private String status;
    @NotBlank(message = "Priority is required")
    private String priority; // LOW, MEDIUM, HIGH, URGENT

    private LocalDate dueDate;
}
