package co.istad.exstadapi.features.user.dto;

import co.istad.exstadapi.audit.AuditableDto;
import co.istad.exstadapi.enums.Role;

import java.time.LocalDate;

public record UserResponse (
        String uuid,
        String username,
        String email,
        String englishName,
        String khmerName,
        String gender,
        LocalDate dob,
        Role role,
        AuditableDto audit
) {
}
