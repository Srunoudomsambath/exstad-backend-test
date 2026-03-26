package co.istad.exstadapi.features.university.dto;

import co.istad.exstadapi.audit.AuditableDto;

public record UniversityResponse(
        String uuid,
        String englishName,
        String khmerName,
        String shortName,
        Long scholars,
        AuditableDto audit
) {
}
