package co.istad.exstadapi.features.openingProgram.learningOutcome.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record OPLearningOutcomeSetUp(
        @NotBlank(message = "Title is required")
        @Size(max = 50, message = "Title must not exceed 50 characters")
        String title,

        @NotBlank(message = "Subtitle is required")
        @Size(max = 250, message = "Subtitle must not exceed 250 characters")
        String subtitle,

        @NotEmpty(message = "Description list cannot be empty")
        List<@NotBlank(message = "Each description item is required") String> description
) {
}
