package co.istad.exstadapi.features.scholarBadge.dto;

import co.istad.exstadapi.features.badge.dto.BadgeResponse;
import co.istad.exstadapi.features.scholar.dto.ScholarResponse;

import java.time.LocalDate;

public record ScholarBadgeResponse(
        String uuid,
        LocalDate completionDate,
        ScholarResponse scholar,
        BadgeResponse badge
) {
}


