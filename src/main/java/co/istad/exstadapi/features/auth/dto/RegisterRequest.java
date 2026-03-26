package co.istad.exstadapi.features.auth.dto;

import co.istad.exstadapi.enums.Role;
import jakarta.validation.constraints.*;

public record RegisterRequest(
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        String username,
        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        @Size(max = 100, message = "Email must not exceed 100 characters")
        String email,
        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
                message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
        String password,
        @NotBlank(message = "Password confirmation is required")
        String cfPassword,
        @NotBlank(message = "English name is required")
        @Size(min = 2, max = 100, message = "English name must be between 2 and 100 characters")
        String englishName,
        @NotBlank(message = "Khmer name is required")
        @Size(min = 2, max = 100, message = "Khmer name must be between 2 and 100 characters")
        String khmerName,
        @NotNull(message = "Role is required")
        Role role
) {
}
