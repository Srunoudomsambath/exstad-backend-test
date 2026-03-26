package co.istad.exstadapi.features.enrollment.dto;

import co.istad.exstadapi.enums.Gender;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public record EnrollmentRequest(

        @NotBlank(message = "English name is required")
        @Size(max = 100, message = "English name must be at most 100 characters")
        String englishName,

        @NotBlank(message = "Khmer name is required")
        @Size(max = 100, message = "Khmer name must be at most 100 characters")
        String khmerName,

        @NotBlank(message = "Opening program UUID is required")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "Opening program UUID must be a valid UUID format")
        String openingProgramUuid,

        @NotNull(message = "Gender is required")
        Gender gender,

        @NotNull(message = "Date of birth is required")
        @Past(message = "Date of birth must be in the past")
        LocalDate dob,

        @NotBlank(message = "Phone number is required")
        @Size(min = 6, max = 20, message = "Phone number must be between 6 and 20 characters")
        String phoneNumber,

        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        @Size(max = 100, message = "Email must be at most 100 characters")
        String email,

        @Size(max = 255, message = "Avatar URL must be at most 255 characters")
        String avatar,

        @NotBlank(message = "Province is required")
        @Size(min = 2, max = 50, message = "Province must be between 2 and 50 characters")
        String province,

        Boolean isScholar,

        @NotBlank(message = "Current address is required")
        @Size(min = 3, max = 200, message = "Current address must be between 3 and 200 characters")
        String currentAddress,

        @NotBlank(message = "University is required")
        @Size(min = 2, max = 100, message = "University must be between 2 and 100 characters")
        String university,

        @NotBlank(message = "Education qualification is required")
        @Size(min = 2, max = 100, message = "Education qualification must be between 2 and 100 characters")
        String educationQualification,

        Map<String, String> extra,

        String classUuid,

        @DecimalMin(value = "0.0", message = "Amount must be zero or positive")
        BigDecimal amount
) {}
