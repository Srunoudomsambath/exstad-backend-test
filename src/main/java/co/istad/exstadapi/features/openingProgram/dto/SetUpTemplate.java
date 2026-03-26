package co.istad.exstadapi.features.openingProgram.dto;

import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.util.List;

public record SetUpTemplate(
        @Size(max = 500, message = "Description cannot exceed 500 characters")
                @URL(message = "Invalid URL format")
        String template
) {
}
