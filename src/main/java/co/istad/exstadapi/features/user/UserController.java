package co.istad.exstadapi.features.user;

import co.istad.exstadapi.features.user.dto.UserRequest;
import co.istad.exstadapi.features.user.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public UserResponse createUser(@RequestBody @Valid UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(
                Map.of("users", userService.getAllUsers()), HttpStatus.OK
        );
    }

    @GetMapping("/not-scholar")
    public ResponseEntity<?> getNotScholarUsers() {
        return new ResponseEntity<>(Map.of("users", userService.getNotScholarUsers()), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getUserByUuid(@PathVariable String uuid) {
        return new ResponseEntity<>(
                userService.getUserByUuid(uuid), HttpStatus.OK
        );
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        return new ResponseEntity<>(
                userService.getUserByEmail(email), HttpStatus.OK
        );
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        return new ResponseEntity<>(
                userService.getCurrentUser(), HttpStatus.OK
        );
    }

}
