package co.istad.exstadapi.features.scholarBadge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record ScholarBadgeRequest(
        @NotNull(message = "Scholar UUID cannot be null")
        @NotBlank(message = "Scholar UUID cannot be blank")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "Scholar UUID must be a valid UUID format")
        String scholarUuid,

        @NotNull(message = "Badge UUID cannot be null")
        @NotBlank(message = "Badge UUID cannot be blank")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "Badge UUID must be a valid UUID format")
        String badgeUuid,

        @NotNull(message = "Completion date cannot be null")
        @PastOrPresent(message = "Completion date cannot be in the future")
        LocalDate completionDate
) {
}