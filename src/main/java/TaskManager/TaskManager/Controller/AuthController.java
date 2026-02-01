package TaskManager.TaskManager.Controller;

import TaskManager.TaskManager.Dto.LoginRequest;
import TaskManager.TaskManager.Service.AuthService;
import TaskManager.TaskManager.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // ✅ REGISTER — now returns { token, name, email }
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user) {
        return authService.register(user);
    }

    // ✅ LOGIN — unchanged, returns plain JWT token string
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return authService.login(
                request.getEmail(),
                request.getPassword()
        );
    }
}
