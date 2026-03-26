package co.istad.exstadapi.features.document.dto;

import co.istad.exstadapi.audit.AuditableDto;
import co.istad.exstadapi.enums.DocumentType;
import lombok.Builder;

@Builder
public record DocumentResponse(
        String name,
        String mimeType,
        String programSlug,
        int gen,
        DocumentType documentType,
        Long fileSize,
        String uri,
        AuditableDto audit
) {
}
