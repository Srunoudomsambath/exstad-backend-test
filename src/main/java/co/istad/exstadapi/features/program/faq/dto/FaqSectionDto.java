package co.istad.exstadapi.features.program.faq.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record FaqSectionDto(
        @NotBlank(message = "FAQ section title cannot be blank")
        @Size(max = 200, message = "FAQ section title cannot exceed 200 characters")
        String title,

        @NotNull(message = "FAQ items list cannot be null")
        @NotEmpty(message = "FAQ section must contain at least one FAQ item")
        @Valid
        List<FaqItemDto> faqs
) {
}
