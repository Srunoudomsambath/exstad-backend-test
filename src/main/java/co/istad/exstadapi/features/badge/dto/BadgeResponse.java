package co.istad.exstadapi.features.badge.dto;

import co.istad.exstadapi.audit.AuditableDto;

public record BadgeResponse(
        String uuid,
        String badgeImage,
        String title,
        String description,
        AuditableDto audit
) {
}
