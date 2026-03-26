package co.istad.exstadapi.features.program.faq.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FaqItemDto(
        @NotBlank(message = "Question cannot be blank")
        @Size(max = 500, message = "Question cannot exceed 500 characters")
        String question,

        @NotBlank(message = "Answer cannot be blank")
        @Size(max = 2000, message = "Answer cannot exceed 2000 characters")
        String answer
) {
}
