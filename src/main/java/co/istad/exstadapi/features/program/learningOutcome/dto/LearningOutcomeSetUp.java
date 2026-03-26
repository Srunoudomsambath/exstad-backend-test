package co.istad.exstadapi.features.program.learningOutcome.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record LearningOutcomeSetUp(
        @NotBlank(message = "Title is required and cannot be blank")
        @Size(min = 2, max = 200, message = "Title must be between 2 and 200 characters")
        String title,

        @NotBlank(message = "Subtitle is required and cannot be blank")
        @Size(min = 2, max = 300, message = "Subtitle must be between 2 and 300 characters")
        String subtitle,

        @NotEmpty(message = "Description list cannot be empty")
        List<@NotBlank(message = "Description item cannot be blank")
        @Size(max = 500, message = "Each description item must not exceed 500 characters")
                String> description
) {
}
