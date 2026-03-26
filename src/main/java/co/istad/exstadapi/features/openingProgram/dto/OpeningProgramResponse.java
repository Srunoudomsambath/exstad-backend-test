package co.istad.exstadapi.features.openingProgram.dto;

import co.istad.exstadapi.audit.AuditableDto;
import co.istad.exstadapi.domain.vo.*;
import co.istad.exstadapi.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.mapstruct.ap.internal.conversion.ConversionUtils;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record OpeningProgramResponse(
        String programName,
        String uuid,
        String title,
        List<String> templates,
        String deadline,
        String slug,
        String thumbnail,
        String posterUrl,
        Integer totalSlot,
        BigDecimal originalFee,
        BigDecimal registerFee,
        Float scholarship,
        Float price,
        String telegramGroup,
        Integer generation,
        String qrCodeUrl,
        List<Detail> details,
        List<Timeline> timelines,
        List<Curriculum> curricula,
        List<Roadmap> roadmaps,
        List<LearningOutcome> learningOutcomes,
        List<Requirement> requirements,
        String duration,
        List<Activity> activities,
        String curriculumPdfUri,
        Status status,
        Boolean isActive,
        AuditableDto audit
) {
}
