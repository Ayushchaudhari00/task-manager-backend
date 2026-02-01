package TaskManager.TaskManager.Service;

import TaskManager.TaskManager.Dto.UserRequestDto;
import TaskManager.TaskManager.Dto.UserResponseDto;
import TaskManager.TaskManager.Security.JwtUtil;
import TaskManager.TaskManager.entity.User;
import TaskManager.TaskManager.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // Method 1: Using DTO (Recommended)
    public UserResponseDto register(UserRequestDto requestDto) {
        // Check if email already exists
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // Create user from DTO
        User user = User.builder()
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .role("USER")
                .build();

        // Save user
        User savedUser = userRepository.save(user);

        // Return response DTO
        return UserResponseDto.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .build();
    }

    // Method 2: Keep original for backward compatibility
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        return userRepository.save(user);
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
