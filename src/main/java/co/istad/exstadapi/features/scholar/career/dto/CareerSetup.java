package co.istad.exstadapi.features.scholar.career.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record CareerSetup(

        @NotNull(message = "Salary is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Salary must be greater than 0")
        BigDecimal salary,

        @NotBlank(message = "Company name is required")
        @Size(min = 2, max = 100, message = "Company name must be between 2 and 100 characters")
        String company,

        @NotBlank(message = "Position is required")
        @Size(min = 2, max = 100, message = "Position must be between 2 and 100 characters")
        String position,

        @Size(max = 500, message = "Interest must not exceed 500 characters")
        String interest,

        @NotBlank(message = "Company type is required")
        @Size(min = 2, max = 50, message = "Company type must be between 2 and 50 characters")
        String companyType
) {
}