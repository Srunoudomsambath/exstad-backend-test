package co.istad.exstadapi.features.currentAddress.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CurrentAddressRequest(
        @NotBlank(message = "English name is required")
        @Size(min = 2, max = 100, message = "English name must be between 2 and 100 characters")
        String englishName,
        @NotBlank(message = "Khmer name is required")
        @Size(min = 1, max = 100, message = "Khmer name must be between 1 and 100 characters")
        String khmerName,
        @NotBlank(message = "Province is required")
        @Size(min = 2, max = 50, message = "Province must be between 2 and 50 characters")
        String province
) {
}
