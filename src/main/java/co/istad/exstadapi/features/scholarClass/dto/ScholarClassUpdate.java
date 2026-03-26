package co.istad.exstadapi.features.scholarClass.dto;

import jakarta.validation.constraints.NotNull;

public record ScholarClassUpdate(
        Boolean isPaid,

        Boolean isReminded
) {
}
