package co.istad.exstadapi.features.scholarBadge.dto;

import co.istad.exstadapi.features.badge.dto.BadgeResponse;

import java.time.LocalDate;

public record ScholarBadgeForScholarResponse(
        String uuid,
        LocalDate completionDate,
        BadgeResponse badge
) {
}
