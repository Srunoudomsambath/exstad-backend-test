package co.istad.exstadapi.features.scholarAchievement.dto;

import co.istad.exstadapi.audit.AuditableDto;
import co.istad.exstadapi.features.achievement.dto.AchievementResponse;

public record ScholarAchievementResponseForScholar(
        String uuid,
        AchievementResponse achievement,
        AuditableDto audit
) {
}
