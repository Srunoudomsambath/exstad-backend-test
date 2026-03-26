package co.istad.exstadapi.features.program.roadmap.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record RoadmapSetUp(
        @NotEmpty(message = "Nodes list cannot be empty")
        @Size(min = 1, max = 50, message = "Roadmap must contain between 1 and 50 nodes")
        List<@Valid @NotNull NodeSetUp> nodes,

        @NotNull(message = "Edges list cannot be null")
        @Size(max = 100, message = "Roadmap cannot have more than 100 edges")
        List<@Valid @NotNull EdgeSetUp> edges
) {
}
