package co.istad.exstadapi.features.classes.dto;


import co.istad.exstadapi.audit.AuditableDto;
import co.istad.exstadapi.enums.Shift;

import java.time.LocalTime;

public record ClassResponse(
        String room,
        String uuid,
        String classCode,
        String telegram,
        String openingProgramName,
        Shift shift,
        Boolean isWeekend,
        Integer totalSlot,
        LocalTime startTime,
        LocalTime endTime,
        Boolean isEnabled,
        AuditableDto audit
) {
}
