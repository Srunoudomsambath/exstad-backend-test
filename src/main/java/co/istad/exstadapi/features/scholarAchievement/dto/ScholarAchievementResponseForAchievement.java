package co.istad.exstadapi.features.scholarAchievement.dto;

import co.istad.exstadapi.audit.AuditableDto;
import co.istad.exstadapi.features.scholar.dto.ScholarResponse;

public record ScholarAchievementResponseForAchievement(
        String uuid,
        ScholarResponse scholar,
        AuditableDto audit
) {
}
