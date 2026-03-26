package co.istad.exstadapi.features.program.curriculum.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record CurriculumSetUp(
        @NotNull(message = "Order is required")
        @Min(value = 1, message = "Order must be at least 1")
        Integer order,
        @NotBlank(message = "Title is required")
        @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
        String title,
        @Size(max = 200, message = "Subtitle cannot exceed 200 characters")
        String subtitle,
        @NotEmpty(message = "Description is required")
        List<String> description
) {
}
