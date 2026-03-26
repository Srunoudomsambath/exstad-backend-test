package co.istad.exstadapi.features.province.dto;

import co.istad.exstadapi.audit.AuditableDto;

public record ProvinceResponse(
        String uuid,
        String englishName,
        String khmerName,
        Long scholars,
        AuditableDto audit
) {
}
