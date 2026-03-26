package co.istad.exstadapi.features.certificate.dto;

import co.istad.exstadapi.audit.AuditableDto;

public record CertificateResponse(
        String uuid,
        String fileName,
        String scholarUuid,
        String openingProgramUuid,
        String tempCertificateUrl,
        String certificateUrl,
        boolean isVerified,
        AuditableDto audit
) {
}
