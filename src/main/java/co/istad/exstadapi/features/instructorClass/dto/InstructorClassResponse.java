package co.istad.exstadapi.features.instructorClass.dto;

import co.istad.exstadapi.audit.AuditableDto;

public record InstructorClassResponse(
        String uuid,
        String instructorUuid,
        String instructorUsername,
        String classUuid,
        AuditableDto audit
) {
}
