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

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRequestDto request) {
        try {
            UserResponseDto response = authService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                    "success", true,
                    "message", "User registered successfully",
                    "user", response
                )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of(
                    "success", false,
                    "message", e.getMessage()
                )
            );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            String token = authService.login(request.getEmail(), request.getPassword());
            return ResponseEntity.ok().body(
                Map.of(
                    "success", true,
                    "token", token,
                    "message", "Login successful"
                )
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                Map.of(
                    "success", false,
                    "message", e.getMessage()
                )
            );
        }
    }

    // ADD THIS FOR DEBUGGING - Simple endpoint that doesn't use DB
    @PostMapping("/test-register")
    public ResponseEntity<?> testRegister(@RequestBody Map<String, String> request) {
        System.out.println("Test register called with: " + request);
        
        return ResponseEntity.ok().body(
            Map.of(
                "success", true,
                "message", "Test endpoint works!",
                "received", request,
                "timestamp", new java.util.Date()
            )
        );
    }
}
