package co.istad.exstadapi.features.scholarBadge.dto;

import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record ScholarBadgeRequestUpdate(
        @PastOrPresent(message = "Completion date cannot be in the future")
        LocalDate completionDate
) {
}
