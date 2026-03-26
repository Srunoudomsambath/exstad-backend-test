package co.istad.exstadapi.features.instructorClass.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record InstructorClassUpdate(
        @NotBlank(message = "Program UUID is required")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "Program UUID must be a valid UUID format")
        String instructorUuid,
        @NotBlank(message = "Program UUID is required")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "Program UUID must be a valid UUID format")
        String classUuid
) {
}
