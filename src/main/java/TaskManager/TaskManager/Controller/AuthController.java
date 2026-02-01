package TaskManager.TaskManager.Controller;

import TaskManager.TaskManager.Dto.LoginRequest;
import TaskManager.TaskManager.Service.AuthService;
import TaskManager.TaskManager.entity.Task;
import TaskManager.TaskManager.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return authService.login(
                request.getEmail(),
                request.getPassword()
        );
    }

}
