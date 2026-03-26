package co.istad.exstadapi.mapper;

import co.istad.exstadapi.domain.*;
import co.istad.exstadapi.domain.Class;
import co.istad.exstadapi.features.achievement.AchievementRepository;
import co.istad.exstadapi.features.badge.BadgeRepository;
import co.istad.exstadapi.features.classes.ClassRepository;
import co.istad.exstadapi.features.currentAddress.CurrentAddressRepository;
import co.istad.exstadapi.features.openingProgram.OpeningProgramRepository;
import co.istad.exstadapi.features.openingProgram.dto.CompletedCourseResponse;
import co.istad.exstadapi.features.openingProgram.dto.OpeningProgramResponse;
import co.istad.exstadapi.features.program.ProgramRepository;
import co.istad.exstadapi.features.province.ProvinceRepository;
import co.istad.exstadapi.features.scholar.ScholarRepository;
import co.istad.exstadapi.features.university.UniversityRepository;
import co.istad.exstadapi.features.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MapperHelper {

    private final ScholarRepository scholarRepository;
    private final BadgeRepository badgeRepository;
    private final UniversityRepository universityRepository;
    private final ProvinceRepository provinceRepository;
    private final CurrentAddressRepository currentAddressRepository;
    private final OpeningProgramRepository openingProgramRepository;
    private final ProgramRepository programRepository;
    private final AchievementRepository achievementRepository;
    private final ClassRepository classRepository;
    private final UserRepository userRepository;

    @Value("${server.public-access}")
    private String publicAccess;

    @Named("toUniversity")
    public University toUniversity(final String university) {
        return universityRepository.findByEnglishName(university).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"University not found")
        );
    }

    @Named("toProvince")
    public Province toProvince(final String province) {
        return provinceRepository.findByEnglishName(province).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Province not found")
        );
    }

    @Named("toCurrentAddress")
    public CurrentAddress toCurrentAddress(final String currentAddress) {
        return currentAddressRepository.findByEnglishName(currentAddress).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Current address not found")
        );
    }

    @Named("toScholar")
    public Scholar toScholar(final String scholarUuid) {
        return scholarRepository.findByUuid(scholarUuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Scholar not found")
        );
    }

    @Named("toBadge")
    public Badge toBadge(final String badgeUuid) {
        return badgeRepository.findByUuid(badgeUuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Badge not found")
        );
    }

    @Named("toProgramTitle")
    public Program toProgramTitle(final String uuid) {
        return programRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Program not found")
        );
    }

    @Named("toPublicAccessDocument")
    public String toPublicAccessDocument(Document document) {
        return this.publicAccess + document.getName() + "." + document.getExtension();
    }

    @Named("toFullDocumentName")
    public String toFullDocumentName(Document document) {
        return document.getName() + "." + document.getExtension();
    }

    @Named("toOpeningProgramTitle")
    public OpeningProgram toOpeningProgramTitle(final String uuid){
        return openingProgramRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Opening program not found")
        );
    }

    @Named("toOpeningProgramByUuid")
    public OpeningProgram toOpeningProgramByUuid(final String uuid){
        return openingProgramRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Opening program not found")
        );
    }


    @Named("toAchievementByUuid")
    public Achievement toAchievementByUuid(final String achievementUuid) {
        return achievementRepository.findByUuid(achievementUuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Achievement not found")
        );
    }

    @Named("toClassByUuid")
    public Class toClassByUuid(final String classUuid) {
        return classRepository.findByUuid(classUuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found")
        );
    }

    @Named("toInstructorByUuid")
    public User toInstructorByUuid(final String instructorUuid) {
        return userRepository.findByUuid(instructorUuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instructor not found")
        );
    }

    @Named("toCompletedProgramResponseList")
    public List<CompletedCourseResponse> toCompletedProgramResponseList(List<String> completedCourses) {
        if (completedCourses == null || completedCourses.isEmpty()) {
            return List.of();
        }

        return completedCourses.stream()
                .map(uuid -> openingProgramRepository.findByUuid(uuid).orElse(null))
                .filter(java.util.Objects::nonNull)
                .map(openingProgram -> CompletedCourseResponse.builder()
                        .uuid(openingProgram.getUuid())
                        .title(openingProgram.getTitle())
                        .slug(openingProgram.getSlug())
                        .thumbnail(openingProgram.getThumbnail())
                        .posterUrl(openingProgram.getPosterUrl())
                        .generation(openingProgram.getGeneration())
                        .programName(openingProgram.getProgram().getTitle())
                        .scholarship(openingProgram.getScholarship())
                        .build())
                .toList();
    }
}
