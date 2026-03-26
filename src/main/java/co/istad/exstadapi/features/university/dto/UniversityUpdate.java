package co.istad.exstadapi.features.university.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UniversityUpdate(
        @Size(min = 2, max = 200, message = "English name must be between 2 and 200 characters")
        @Pattern(regexp = "^[a-zA-Z\\s.'-]+$", message = "English name can only contain letters, spaces, dots, apostrophes, and hyphens")
        String englishName,

        @Size(min = 2, max = 200, message = "Khmer name must be between 2 and 200 characters")
        String khmerName,

        @Size(min = 2, max = 20, message = "Short name must be between 2 and 20 characters")
        @Pattern(regexp = "^[A-Z0-9]+$", message = "Short name must contain only uppercase letters and numbers")
        String shortName
) {
}
