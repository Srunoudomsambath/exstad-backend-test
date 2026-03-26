package co.istad.exstadapi.features.certificate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record CertificateRequestDto(
        @NotEmpty(message = "Scholar UUIDs list cannot be empty")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "Scholar UUID must be a valid UUID format")
        String scholarUuid,
        @NotBlank(message = "Opening program UUID is required")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "Opening program UUID must be a valid UUID format")
        String openingProgramUuid,
        @NotBlank(message = "Background image URL is required")
        String bgImage
) {}
