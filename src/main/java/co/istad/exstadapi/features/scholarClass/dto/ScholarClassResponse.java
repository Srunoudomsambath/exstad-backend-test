package co.istad.exstadapi.features.scholarClass.dto;

import co.istad.exstadapi.audit.AuditableDto;
import co.istad.exstadapi.features.scholar.dto.ScholarResponse;

public record ScholarClassResponse(
        String uuid,
        ScholarResponse scholar,
        String classUuid,
        String room,
        Boolean isReminded,
        Boolean isPaid,
        AuditableDto audit
) {
}
