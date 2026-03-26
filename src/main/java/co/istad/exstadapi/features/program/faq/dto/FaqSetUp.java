package co.istad.exstadapi.features.program.faq.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record FaqSetUp(
        @NotNull(message = "FAQ list cannot be null")
        @NotEmpty(message = "FAQ list cannot be empty")
        @Valid
        List<FaqSectionDto> faq
) {
}
