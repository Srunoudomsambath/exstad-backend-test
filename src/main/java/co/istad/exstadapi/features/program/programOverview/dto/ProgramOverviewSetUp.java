package co.istad.exstadapi.features.program.programOverview.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProgramOverviewSetUp(
        @NotBlank(message = "Title is required and cannot be blank")
        @Size(min = 2, max = 200, message = "Title must be between 2 and 200 characters")
        String title,

        @NotBlank(message = "Description is required and cannot be blank")
        @Size(min = 2, max = 2000, message = "Description must be between 2 and 2000 characters")
        String description
) {
}
