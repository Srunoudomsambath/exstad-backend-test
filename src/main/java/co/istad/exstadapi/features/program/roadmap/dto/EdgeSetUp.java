package co.istad.exstadapi.features.program.roadmap.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EdgeSetUp(
        @NotBlank(message = "Edge ID is required")
        String id,

        @NotBlank(message = "Source node ID is required")
        String source,

        @NotBlank(message = "Target node ID is required")
        String target,

        @NotNull(message = "Animated flag is required")
        Boolean animated
) {
}
