package co.istad.exstadapi.features.user.dto;

import co.istad.exstadapi.enums.Gender;
import co.istad.exstadapi.enums.Role;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserRequest(
        @NotBlank(message = "Username is required")
        @Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters")
        String username,

        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        @Size(max = 100, message = "Email must be at most 100 characters")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
                message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character")
        String password,

        @NotBlank(message = "Confirm password is required")
        String cfPassword,

        @NotBlank(message = "English name is required")
        @Size(min = 2, max = 100, message = "English name must be between 2 and 100 characters")
        String englishName,

        @NotBlank(message = "Khmer name is required")
        @Size(min = 2, max = 100, message = "Khmer name must be between 2 and 100 characters")
        String khmerName,

        @NotNull(message = "Gender is required")
        Gender gender,

        @NotNull(message = "Date of birth is required")
        @Past(message = "Date of birth must be in the past")
        LocalDate dob,

        @NotNull(message = "Role is required")
        Role role
) {
}
