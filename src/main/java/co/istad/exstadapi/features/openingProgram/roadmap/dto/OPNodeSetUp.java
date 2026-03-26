package co.istad.exstadapi.features.openingProgram.roadmap.dto;

import co.istad.exstadapi.features.program.roadmap.dto.NodeDataSetUp;
import co.istad.exstadapi.features.program.roadmap.dto.PositionSetUp;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OPNodeSetUp(
        @NotBlank(message = "Node type is required")
        @Size(max = 50, message = "Node type must not exceed 50 characters")
        String type,

        @Valid
        @NotNull(message = "Node data is required")
        NodeDataSetUp data,

        @Valid
        @NotNull(message = "Node position is required")
        PositionSetUp position
) {
}
