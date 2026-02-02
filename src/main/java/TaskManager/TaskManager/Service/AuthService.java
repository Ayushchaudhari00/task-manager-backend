package TaskManager.TaskManager.Service;

import TaskManager.TaskManager.Security.JwtUtil;
import TaskManager.TaskManager.entity.User;
import TaskManager.TaskManager.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

public Map<String, Object> register(User user) {

    if (user.getName() == null || user.getName().isBlank()) {
        throw new RuntimeException("Name is required");
    }

    if (user.getEmail() == null || user.getEmail().isBlank()) {
        throw new RuntimeException("Email is required");
    }

    if (user.getPassword() == null || user.getPassword().isBlank()) {
        throw new RuntimeException("Password is required");
    }

    if (userRepository.findByEmail(user.getEmail()).isPresent()) {
        throw new RuntimeException("Email already registered");
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole("USER");

    User savedUser = userRepository.save(user);

    String token = jwtUtil.generateToken(savedUser.getEmail());

    return Map.of(
        "token", token,
        "name", savedUser.getName(),
        "email", savedUser.getEmail()
    );
}



    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getEmail());
    }
}
