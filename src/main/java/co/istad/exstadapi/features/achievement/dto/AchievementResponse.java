package co.istad.exstadapi.features.achievement.dto;

import co.istad.exstadapi.audit.AuditableDto;
import co.istad.exstadapi.enums.AchievementType;

public record AchievementResponse(
        String uuid,
        String title,
        String description,
        String icon,
        String program,
        AchievementType achievementType,
        String tag,
        String video,
        String link,
        AuditableDto audit
) {
}
