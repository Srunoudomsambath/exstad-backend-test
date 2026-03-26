package co.istad.exstadapi.features.achievement.dto;

import co.istad.exstadapi.enums.AchievementType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AchievementRequest(
        @NotBlank(message = "Title is required")
        @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
        String title,

        @NotBlank(message = "Description is required")
        @Size(min = 1, max = 500, message = "Description must be between 1 and 500 characters")
        String description,

        @NotBlank(message = "Opening program UUID is required")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "Opening program UUID must be a valid UUID format")
        String openingProgramUuid,

        @NotBlank(message = "Icon is required")
                @Size(max = 255, message = "Icon URL must be at most 255 characters")
        String icon,

        @NotNull(message = "Achievement type is required")
        AchievementType achievementType,

        @NotBlank(message = "Tag is required")
        @Size(max = 50, message = "Tag must be at most 50 characters")
        String tag,

        @NotBlank(message = "Video URL is required")
                @Size(max = 255, message = "Video URL must be at most 255 characters")
        String video,

        @NotBlank(message = "Link is required")
        @Size(max = 255, message = "Link must be at most 255 characters")
        String link
) {}
