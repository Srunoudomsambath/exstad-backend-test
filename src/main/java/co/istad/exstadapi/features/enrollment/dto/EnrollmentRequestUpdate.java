package co.istad.exstadapi.features.enrollment.dto;

import co.istad.exstadapi.enums.Gender;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EnrollmentRequestUpdate(
        @Size(min = 2, max = 100, message = "English name must be between 2 and 100 characters")
        String englishName,
        @Size(min = 1, max = 100, message = "Khmer name must be between 1 and 100 characters")
        String khmerName,
        Gender gender,
        @Past(message = "Date of birth must be in the past")
        LocalDate dob,
        @Email(message = "Email should be valid")
        @Size(max = 100, message = "Email must be at most 100 characters")
        String email,
        @Size(max = 255, message = "Avtar URL must be at most 255 characters")
        String avatar,
        @DecimalMin(value = "0.0", message = "Amount must be zero or positive")
        BigDecimal amount,
        Boolean isScholar,
        @Size(min = 2, max = 50, message = "Province must be between 2 and 50 characters")
        String province,
        @Size(min = 5, max = 200, message = "Current address must be between 5 and 200 characters")
        String currentAddress,
        @Size(min = 2, max = 100, message = "University must be between 2 and 100 characters")
        String university,
        @Size(min = 2, max = 100, message = "Education qualification must be between 2 and 100 characters")
        String educationQualification,
        Boolean isPaid,
        Boolean isInterviewed,
        Boolean isPassed,
        Boolean isAchieved
) {
}
