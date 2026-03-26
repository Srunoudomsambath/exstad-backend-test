package co.istad.exstadapi.features.openingProgram.timeline.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

public record TimelineSetUp(

        @NotBlank(message = "Title is required")
        @Size(max = 150, message = "Title must not exceed 150 characters")
        String title,

        @NotNull(message = "Date is required")
        @FutureOrPresent(message = "Date must be today or in the future")
        LocalDate startDate,

        @FutureOrPresent(message = "Date must be today or in the future")
        LocalDate endDate
) {
        public TimelineSetUp {
                // Custom validation: endDate should not be before startDate
                if (endDate != null && endDate.isBefore(startDate)) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT,"End date cannot be before start date");
                }
        }
}



