package co.istad.exstadapi.features.program.roadmap.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PositionSetUp(
        @NotNull(message = "X position is required")
        int x,

        @NotNull(message = "Y position is required")
        int y
) {
}
