package co.istad.exstadapi.features.classes.dto;

import co.istad.exstadapi.enums.Shift;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalTime;

public record ClassUpdate(
        @Size(max = 100, message = "Room must be at most 100 characters")
        String room,

        @Size(max = 100, message = "Instructor name must be at most 100 characters")
        String instructor,

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
    public ClassUpdate {
        if (startTime != null && endTime != null && !endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("End time must be after start time");
        }
    }
}
