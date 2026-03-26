package co.istad.exstadapi.features.program.Technology.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TechnologySetup(
        @NotBlank(message = "Title is required")
        @Size(max = 100, message = "Title must not exceed 100 characters")
        String title,

        @NotBlank(message = "Description is required")
        String description,

        @NotBlank(message = "Image URL is required")
        @Size(max = 255, message = "Image URL must not exceed 255 characters")
        String image
) {
}
