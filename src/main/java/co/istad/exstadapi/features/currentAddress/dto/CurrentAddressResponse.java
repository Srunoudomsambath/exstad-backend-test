package co.istad.exstadapi.features.currentAddress.dto;

import co.istad.exstadapi.audit.AuditableDto;

public record CurrentAddressResponse(
        String uuid,
        String englishName,
        String khmerName,
        String province,
        Long scholars,
        AuditableDto audit
) {
}
