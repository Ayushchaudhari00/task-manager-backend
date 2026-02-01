package TaskManager.TaskManager.Controller;

import TaskManager.TaskManager.Dto.LoginRequest;
import TaskManager.TaskManager.Dto.UserRequestDto;
import TaskManager.TaskManager.Dto.UserResponseDto;
import TaskManager.TaskManager.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
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

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user) {
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
