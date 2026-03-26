package co.istad.exstadapi.features.scholar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SetSpecialistToAlumniScholar(
        @NotBlank(message = "Major is required")
                @Size(min = 2, max = 50, message = "Major must be between 2 and 50 characters")
        String specialist
) {
}
