package co.istad.exstadapi.features.scholarAchievement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ScholarAchievementRequest(
        @NotBlank(message = "Achievement UUID is required")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "Achievement UUID must be a valid UUID format")
        String achievementUuid
) {
}
