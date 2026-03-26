package co.istad.exstadapi.features.openingProgram.dto;
import co.istad.exstadapi.domain.vo.*;
import co.istad.exstadapi.enums.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;
public record OpeningProgramUpdate(
        @Size(min = 3, max = 60, message = "Title must be between 3 and 60 characters")
        String title,

        @Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$", message = "Slug must be lowercase alphanumeric with hyphens")
        @Size(max = 100, message = "Slug must not exceed 100 characters")
        String slug,

        @Size(max = 255, message = "Thumbnail URL must not exceed 255 characters")
        String thumbnail,

        @NotBlank(message = "Poster URL is required")
        @Size(max = 255, message = "Poster URL must not exceed 255 characters")
        String posterUrl,

        @Min(value = 1, message = "Total slot must be at least 1")
        Integer totalSlot,

        @NotBlank(message = "Deadline is required")
        @Size(min = 1, max = 30, message = "Deadline must be between 1 and 30 characters")
        String deadline,

        @DecimalMin(value = "0.0", message = "Original fee must be zero or positive")
        BigDecimal originalFee,

        @DecimalMin(value = "0.0", message = "Register fee must be zero or positive")
        BigDecimal registerFee,

        @DecimalMin(value = "0.0", message = "Scholarship must be zero or positive")
        BigDecimal scholarship,

        @DecimalMin(value = "0.0", message = "Price must be zero or positive")
        BigDecimal price,

        @Size(max = 100, message = "Telegram group must not exceed 100 characters")
        String telegramGroup,

        @Min(value = 1, message = "Generation must be at least 1")
        Integer generation,

        Boolean isDeleted,

        Status status,

        @Size(max = 255, message = "QR Code URL must not exceed 255 characters")
        String qrCodeUrl,

        @Valid
        List<Detail> details,

        @Valid
        List<Timeline> timelines,

        @Valid
        List<Curriculum> curricula,

        @Valid
        List<Roadmap> roadmaps,

        @Valid
        List<LearningOutcome> learningOutcomes,

        @Valid
        List<Requirement> requirements,

        @Size(min = 1, max = 50, message = "Duration must be between 1 and 50 characters")
        String duration,

        @Valid
        List<Activity> activities,

        @Size(max = 255, message = "Curriculum PDF URL must not exceed 255 characters")
        String curriculumPdfUri
) {

}
