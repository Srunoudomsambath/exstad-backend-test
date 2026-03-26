package co.istad.exstadapi.features.scholarClass.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ScholarClassRequest(
        @NotBlank(message = "Scholar UUID is required")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "Scholar UUID must be a valid UUID format")
        String scholarUuid,

        @NotBlank(message = "Class UUID is required")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "Class UUID must be a valid UUID format")
        String classUuid,

        @NotNull(message = "Payment status is required")
        Boolean isPaid,

        @NotNull(message = "Reminder status is required")
        Boolean isReminded
) {
}
