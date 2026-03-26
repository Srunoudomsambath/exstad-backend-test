package co.istad.exstadapi.features.openingProgram.detail.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DetailSetUp(
        @NotBlank(message = "Title is required")
        @Size(max = 150, message = "Title must not exceed 150 characters")
        String title,

        @NotBlank(message = "Description is required")
        String description,

        @NotNull(message = "isActive status is required")
        Boolean isActive
) {
}
