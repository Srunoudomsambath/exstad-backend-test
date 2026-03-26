package co.istad.exstadapi.features.achievement.dto;

import co.istad.exstadapi.enums.AchievementType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AchievementRequestUpdate(
        @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
        String title,
        @Size(min = 1, max = 500, message = "Description must be between 1 and 500 characters")
        String description,
        @Size(max = 100, message = "Icon must be max 100 characters")
        String icon,
        @Size(max = 255, message = "Video must be max 255 characters")
        String video,
        @Size(max = 255, message = "Link must be max 255 characters")
        String link,
        @Size(min = 1, max = 50, message = "Tag must be between 1 and 50 characters")
        String tag,
        @NotNull(message = "Achievement type is required")
        AchievementType achievementType
) {
}
