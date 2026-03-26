package co.istad.exstadapi.features.program.dto;

import co.istad.exstadapi.domain.vo.*;
import co.istad.exstadapi.enums.ProgramLevel;
import co.istad.exstadapi.enums.ProgramType;
import co.istad.exstadapi.enums.Visibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ProgramUpdate(
        @NotBlank(message = "Title is required")
        @Size(min = 3, max = 150, message = "Title must be between 3 and 150 characters")
        String title,

        @NotBlank(message = "Subtitle is required")
        @Size(min = 3, max = 150, message = "Subtitle must be between 3 and 150 characters")
        String subtitle,

        @Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$",
                message = "Slug must be lowercase alphanumeric with hyphens")
        @Size(max = 100, message = "Slug must not exceed 100 characters")
        String slug,

        @NotBlank(message = "Description is required")
        @Size(min = 5, max = 1000, message = "Description must be between 5 and 1000 characters")
        String description,

        @NotBlank(message = "Logo URL is required")
        @Size(max = 255, message = "Logo URL must not exceed 255 characters")
        String logoUrl,

        @Size(max = 500, message = "Thumbnail URL must not exceed 500 characters")
        String thumbnailUrl,

        @Size(max = 100, min = 4, message = "Background color must be min 4 and max 100 characters")
        String bgColor,

        @NotNull(message = "Visibility is required")
        Visibility visibility,

        List<Highlight> highlights,

        List<ProgramOverview> programOverviews,

        @Size(max = 255, message = "Curriculum PDF URL must be max 255 characters")
        String curriculumPdfUrl,

        List<Roadmap> roadmaps,

        List<Faq> faqs,

        List<Requirement> requirements,

        List<LearningOutcome> learningOutcomes,

        List<Timeline> timelines,

        @NotNull(message = "Program type is required")
        ProgramType programType,

        List<Curriculum> curricula,

        @NotNull(message = "Program level is required")
        ProgramLevel programLevel
) {
}
