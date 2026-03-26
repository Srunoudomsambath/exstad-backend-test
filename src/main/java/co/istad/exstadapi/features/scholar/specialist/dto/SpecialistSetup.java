package co.istad.exstadapi.features.scholar.specialist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SpecialistSetup(

        @NotBlank(message = "Country is required")
        @Size(min = 2, max = 50, message = "Country must be between 2 and 50 characters")
        String country,

        @NotBlank(message = "Specialist is required")
        @Size(min = 2, max = 50, message = "Specialist must be between 2 and 50 characters")
        String specialist,

        @NotBlank(message = "University name is required")
        @Size(min = 2, max = 100, message = "University name must be between 2 and 100 characters")
        String universityName,

        @NotBlank(message = "About is required")
        @Size(min = 3, max = 500, message = "About must be between 3 and 500 characters")
        String about,

        @NotBlank(message = "Degree type is required")
        @Size(min = 2, max = 50, message = "Degree type must be between 2 and 50 characters")
        String degreeType
) {
}
