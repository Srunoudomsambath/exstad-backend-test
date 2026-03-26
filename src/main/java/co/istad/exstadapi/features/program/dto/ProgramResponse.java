package co.istad.exstadapi.features.program.dto;

import co.istad.exstadapi.audit.AuditableDto;
import co.istad.exstadapi.domain.vo.*;
import co.istad.exstadapi.enums.ProgramLevel;
import co.istad.exstadapi.enums.ProgramType;
import co.istad.exstadapi.enums.Visibility;
import co.istad.exstadapi.features.program.Technology.dto.TechnologySetup;

import java.util.List;

public record ProgramResponse(
        String uuid,
        String title,
        String subtitle,
        String slug,
        String description,
        String logoUrl,
        String thumbnailUrl,
        String bgColor,
        Visibility visibility,
        List<Highlight> highlights,
        List<ProgramOverview> programOverviews,
        String curriculumPdfUrl,
        List<Roadmap> roadmaps,
        List<Faq> faqs,
        List<Requirement> requirements,
        List<LearningOutcome> learningOutcomes,
        List<Timeline> timelines,
        ProgramType programType,
        List<Curriculum> curricula,
        ProgramLevel programLevel,
        AuditableDto audit,
        List<TechnologySetup> technologies) {
}
