package co.istad.exstadapi.features.scholar.dto;

import co.istad.exstadapi.audit.AuditableDto;
import co.istad.exstadapi.domain.OpeningProgram;
import co.istad.exstadapi.domain.vo.Career;
import co.istad.exstadapi.domain.vo.Specialist;
import co.istad.exstadapi.enums.Gender;
import co.istad.exstadapi.enums.Role;
import co.istad.exstadapi.enums.ScholarStatus;
import co.istad.exstadapi.features.openingProgram.dto.CompletedCourseResponse;
import co.istad.exstadapi.features.openingProgram.dto.OpeningProgramResponse;
import co.istad.exstadapi.features.scholarBadge.dto.ScholarBadgeForScholarResponse;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record ScholarResponse(
        String uuid,

        String username,

        String email,

        String englishName,

        String khmerName,

        Gender gender,

        LocalDate dob,

        Role role,

        String university,

        String province,

        String currentAddress,

        String nickname,

        String bio,

        List<Specialist> specialist,

        List<Career> careers,

        List<CompletedCourseResponse> completedCourses,

        String avatar,

        String phoneFamilyNumber,
        String phoneNumber,

        String quote,

        AuditableDto audit,

        Boolean isPublic,

        Boolean isAbroad,

        Boolean isEmployed,

        ScholarStatus status,

        List<ScholarBadgeForScholarResponse> badges
) {
}
