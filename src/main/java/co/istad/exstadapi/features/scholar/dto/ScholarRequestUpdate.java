package co.istad.exstadapi.features.scholar.dto;

import co.istad.exstadapi.enums.ScholarStatus;
import jakarta.validation.constraints.Size;

public record ScholarRequestUpdate (
        @Size(min = 2, max = 100, message = "University must be between 2 and 100 characters")
        String university,

        @Size(min = 2, max = 50, message = "Province must be between 2 and 50 characters")
        String province,

        @Size(min = 5, max = 200, message = "Current address must be between 5 and 200 characters")
        String currentAddress,

        @Size(max = 50, message = "Nickname must not exceed 50 characters")
        String nickname,

        @Size(max = 500, message = "Bio must not exceed 500 characters")
        String bio,

        @Size(max = 255, message = "Avatar URL must not exceed 255 characters")
        String avatar,

        @Size(min = 6, max = 20, message = "Phone number must be between 6 and 20 characters")
        String phoneFamilyNumber,

        Boolean isPublic,

        @Size(max = 300, message = "Quote must not exceed 300 characters")
        String quote,

        ScholarStatus status
){
}
