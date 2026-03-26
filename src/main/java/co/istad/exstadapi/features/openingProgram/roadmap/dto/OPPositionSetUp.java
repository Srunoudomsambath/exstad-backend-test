package co.istad.exstadapi.features.openingProgram.roadmap.dto;

import jakarta.validation.constraints.NotNull;

public record OPPositionSetUp(
        @NotNull(message = "X position is required")
        int x,

        @NotNull(message = "Y position is required")
        int y
) {
}
