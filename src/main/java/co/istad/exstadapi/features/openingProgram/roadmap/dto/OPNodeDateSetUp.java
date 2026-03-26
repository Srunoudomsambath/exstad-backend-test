package co.istad.exstadapi.features.openingProgram.roadmap.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OPNodeDateSetUp(
        @NotBlank(message = "Node label is required")
        @Size(min = 2, max = 200, message = "Node label must be between 2 and 200 characters")
        String label,

        @NotBlank(message = "Node description is required")
        String description
) {
}
