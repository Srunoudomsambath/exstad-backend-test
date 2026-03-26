package co.istad.exstadapi.features.program.highlight.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record HighlightSetUp(
        @NotBlank(message = "Label cannot be blank")
        @Size(max = 100, message = "Label must not exceed 100 characters")
        String label,

        @NotBlank(message = "Value cannot be blank")
        @Size(max = 500, message = "Value must not exceed 500 characters")
        String value,

        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        String desc
) {
}
