package co.istad.exstadapi.features.enrollment.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;

import java.math.BigDecimal;

public record SetScoreExamScholar(
        @DecimalMin(value = "0.0", message = "Original fee must be zero or positive")
        BigDecimal score
) {
}
