package co.istad.exstadapi.features.openingProgram.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CompletedCourseResponse(
        String uuid,
        String programName,
        String slug,
        String title,
        BigDecimal scholarship,
        String thumbnail,
        String posterUrl,
        Integer generation
) {
}
