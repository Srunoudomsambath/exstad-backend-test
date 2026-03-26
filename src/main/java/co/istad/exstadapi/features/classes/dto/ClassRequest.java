package co.istad.exstadapi.features.classes.dto;

import co.istad.exstadapi.enums.Shift;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;

public record ClassRequest(
        @NotBlank(message = "Opening program UUID is required")
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                message = "Opening program UUID must be a valid UUID format")
        String openingProgramUuid,

        @Size(max = 100, message = "Room must be at most 100 characters")
        String room,

        @Size(max = 100, message = "Instructor name must be at most 100 characters")
        String instructor,

        @Size(max = 20, message = "Class code must be at most 20 characters")
        String classCode,

        @Size(max = 255, message = "Telegram link must be at most 255 characters")
        String telegram,

        @NotNull(message = "Shift is required")
        Shift shift,

        @NotNull(message = "Weekend status is required")
        Boolean isWeekend,

        @NotNull(message = "Total slot is required")
        @Positive(message = "Total slot must be positive")
        Integer totalSlot,

        @NotNull(message = "Start time is required")
        LocalTime startTime,

        @NotNull(message = "End time is required")
        LocalTime endTime
) {
    public ClassRequest {
        if (startTime != null && endTime != null && !endTime.isAfter(startTime)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End time must be after start time");
        }
    }
}
