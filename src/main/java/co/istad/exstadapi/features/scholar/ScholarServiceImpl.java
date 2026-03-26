package co.istad.exstadapi.features.scholar;

import co.istad.exstadapi.base.BasedMessage;
import co.istad.exstadapi.domain.*;
import co.istad.exstadapi.domain.Class;
import co.istad.exstadapi.domain.vo.Career;
import co.istad.exstadapi.domain.vo.SocialLink;
import co.istad.exstadapi.domain.vo.Specialist;
import co.istad.exstadapi.enums.Role;
import co.istad.exstadapi.enums.ScholarStatus;
import co.istad.exstadapi.features.classes.ClassRepository;
import co.istad.exstadapi.features.openingProgram.OpeningProgramRepository;
import co.istad.exstadapi.features.program.ProgramRepository;
import co.istad.exstadapi.features.scholar.career.dto.CareerSetup;
import co.istad.exstadapi.features.scholar.dto.*;
import co.istad.exstadapi.features.scholar.specialist.dto.SpecialistSetup;
import co.istad.exstadapi.features.scholarClass.ScholarClassRepository;
import co.istad.exstadapi.features.user.UserRepository;
import co.istad.exstadapi.features.user.UserService;
import co.istad.exstadapi.features.user.dto.UserRequest;
import co.istad.exstadapi.features.user.dto.UserResponse;
import co.istad.exstadapi.mapper.ScholarMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScholarServiceImpl implements ScholarService {

    private final ScholarRepository scholarRepository;
    private final ScholarMapper scholarMapper;
    private final UserService userService;
    private final UserRepository userRepository;
    private final OpeningProgramRepository openingProgramRepository;
    private final ClassRepository classRepository;
    private final ProgramRepository programRepository;
    private final ScholarClassRepository scholarClassRepository;

    @Override
    public ScholarResponse createScholar(ScholarRequest scholarRequest) {
        if (scholarRepository.existsByPhoneNumber(scholarRequest.phoneNumber())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already exists");
        }
        if (userRepository.existsByUsername(scholarRequest.username())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        if (userRepository.existsByEmail(scholarRequest.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        if (!scholarRequest.password().equals(scholarRequest.cfPassword())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Password does not match");
        }
        UserRequest userRequest = new UserRequest(
                scholarRequest.username(),
                scholarRequest.email(),
                scholarRequest.password(),
                scholarRequest.cfPassword(),
                scholarRequest.englishName(),
                scholarRequest.khmerName(),
                scholarRequest.gender(),
                scholarRequest.dob(),
                Role.SCHOLAR
        );

        UserResponse userResponse = userService.createUser(userRequest);
        User user = userRepository.findByUuid(userResponse.uuid()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );

        Scholar scholar = scholarMapper.toScholar(scholarRequest);
        scholar.setIsEmployed(false);
        scholar.setSpecialist(null);
        scholar.setCareers(null);
        scholar.setCompletedCourses(new ArrayList<>());
        scholar.setUser(user);
        scholar.setUuid(user.getUuid());
        scholar.setIsDeleted(false);
        scholar.setStatus(ScholarStatus.ACTIVE);
        scholar.setIsAbroad(false);
        scholar.setSocialLink(List.of());
        return scholarMapper.fromScholar(scholarRepository.save(scholar));
    }

    @Override
    public ScholarResponse updateScholar(String uuid, ScholarRequestUpdate scholarRequestUpdate) {
        Scholar scholar = scholarRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scholar not found")
        );
        scholarMapper.toScholarPartially(scholarRequestUpdate, scholar);
        return scholarMapper.fromScholar(scholarRepository.save(scholar));
    }

    @Override
    public List<ScholarResponse> createMultipleScholars(List<ScholarRequest> scholarRequests) {
        List<ScholarResponse> scholarResponses = new ArrayList<>();
        scholarRequests.forEach(
                scholarRequest -> scholarResponses.add(createScholar(scholarRequest))
        );
        return scholarResponses;
    }

    @Override
    public List<ScholarResponse> findAllScholars() {
        return scholarRepository.findAllByIsDeletedFalse().stream().map(
                scholarMapper::fromScholar
        ).toList();
    }

    @Override
    public ScholarResponse findByUuid(String uuid) {
        return scholarMapper.fromScholar(
                scholarRepository.findByUuid(uuid).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scholar not found")
                )
        );
    }

    @Override
    public ScholarResponse findByUsername(String username) {
        return scholarMapper.fromScholar(
                userRepository.findByUsernameAndRole(username, Role.SCHOLAR).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scholar not found")
                ).getScholar()
        );
    }

    @Override
    public List<ScholarResponse> searchByEnglishName(String englishName) {
        return userRepository.findAllByEnglishNameAndRole(englishName, Role.SCHOLAR).stream().map(
                user -> scholarMapper.fromScholar(user.getScholar())
        ).toList();
    }

    @Override
    public List<ScholarResponse> searchByUsername(String username) {
        return userRepository.findAllByUsernameAndRole(username, Role.SCHOLAR).stream().map(
                user -> scholarMapper.fromScholar(user.getScholar())
        ).toList();
    }

    @Override
    public Long countScholars() {
        return scholarRepository.count();
    }

    @Transactional
    @Override
    public BasedMessage softDeleteScholarByUuid(String uuid) {
       if (!scholarRepository.existsByUuid(uuid)) {
              throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Scholar not found");
         }
              scholarRepository.softDeleteByUuid(uuid);
              return new BasedMessage("Scholar deleted successfully");
    }

    @Transactional
    @Override
    public BasedMessage restoreScholarByUuid(String uuid) {
       if (!scholarRepository.existsByUuid(uuid)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Scholar not found");
             }
                scholarRepository.restoreByUuid(uuid);
                return new BasedMessage("Scholar restored successfully");
    }

    @Transactional
    @Override
    public BasedMessage hardDeleteScholarByUuid(String uuid) {
        if (!scholarRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Scholar not found");
        }
            scholarRepository.deleteByUuid(uuid);
            return new BasedMessage("Scholar hard deleted successfully");
    }

    @Override
    public BasedMessage markIsEmployed(String uuid) {
        Scholar scholar = scholarRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scholar not found")
        );
        scholar.setIsEmployed(true);
        scholarRepository.save(scholar);
        return new BasedMessage("Scholar marked as employed");
    }

    @Override
    public BasedMessage unmarkIsEmployed(String uuid) {
        Scholar scholar = scholarRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scholar not found")
        );
        scholar.setIsEmployed(false);
        scholarRepository.save(scholar);
        return new BasedMessage("Scholar unmarked as employed");
    }

    @Override
    public BasedMessage markIsAbroad(String uuid) {
        Scholar scholar = scholarRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scholar not found")
        );
        scholar.setIsAbroad(true);
        scholarRepository.save(scholar);
        return new BasedMessage("Scholar marked as abroad");
    }

    @Override
    public BasedMessage unmarkIsAbroad(String uuid) {
        Scholar scholar = scholarRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scholar not found")
        );
        scholar.setIsAbroad(false);
        scholarRepository.save(scholar);
        return new BasedMessage("Scholar unmarked as abroad");
    }

    @Override
    public List<ScholarResponse> getAllScholarsByOpeningProgramUuid(String openingProgramUuid) {
        openingProgramRepository.findByUuid(openingProgramUuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program not found")
        );

        List<Scholar> scholars = scholarRepository.findAllByOpeningProgramUuid(openingProgramUuid);

        return scholars.stream()
                .map(scholarMapper::fromScholar)
                .toList();
    }

    @Override
    public ScholarResponse getCurrentScholar() {
        String username = userService.getUsernameFromAccessToken();
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );
        if(!user.getRole().equals(Role.SCHOLAR)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to update this role");
        }
        return scholarMapper.fromScholar(user.getScholar());
    }

    @Override
    public ScholarResponse updateCurrentScholar(ScholarRequestUpdate scholarRequestUpdate) {
        String username = userService.getUsernameFromAccessToken();
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );
        if(!user.getRole().equals(Role.SCHOLAR)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to update this role");
        }
        return updateScholar(user.getUuid(), scholarRequestUpdate);
    }


    @Override
    public SocialLinkResponse setUpScholarSocialLink(String uuid, SocialLinkRequest socialLinkRequest) {
        Scholar scholar = scholarRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scholar not found")
        );
        SocialLink socialLink = new SocialLink();
        socialLink.setUuid(UUID.randomUUID().toString());
        socialLink.setType(socialLinkRequest.type());
        socialLink.setLink(socialLinkRequest.link());
        socialLink.setTitle(socialLinkRequest.title());
        socialLink.setIsActive(true);
        socialLink.setIsDeleted(false);
        scholar.getSocialLink().add(socialLink);
        scholarRepository.save(scholar);
        return SocialLinkResponse.builder()
                .uuid(socialLink.getUuid())
                .title(socialLink.getTitle())
                .type(socialLink.getType())
                .link(socialLink.getLink())
                .isActive(socialLink.getIsActive())
                .build();
    }

    @Override
    public List<SocialLinkResponse> getScholarSocialLink(String uuid) {
        return scholarRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scholar not found")
        ).getSocialLink().stream().map(
                socialLink -> SocialLinkResponse.builder()
                        .uuid(socialLink.getUuid())
                        .title(socialLink.getTitle())
                        .type(socialLink.getType())
                        .link(socialLink.getLink())
                        .isActive(socialLink.getIsActive())
                        .build()
        ).toList();
    }

    @Override
    public SocialLinkResponse updateSocialLinkStatus(String scholarUuid, String socialLinkUuid, boolean status) {
        Scholar scholar = scholarRepository.findByUuid(scholarUuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scholar not found")
        );
        SocialLink socialLink = scholar.getSocialLink().stream().filter(
                s -> s.getUuid().equals(socialLinkUuid)
        ).findFirst().orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SocialLink not found")
        );
        socialLink.setIsActive(status);
        scholarRepository.save(scholar);
        return SocialLinkResponse.builder()
                .uuid(socialLink.getUuid())
                .title(socialLink.getTitle())
                .type(socialLink.getType())
                .link(socialLink.getLink())
                .isActive(socialLink.getIsActive())
                .build();
    }

    @Override
    public void deleteSocialLink(String scholarUuid, String socialLinkUuid) {
        Scholar scholar = scholarRepository.findByUuid(scholarUuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scholar not found")
        );
        SocialLink socialLink = scholar.getSocialLink().stream().filter(
                s -> s.getUuid().equals(socialLinkUuid)
        ).findFirst().orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SocialLink not found")
        );
        socialLink.setIsActive(true);
        scholarRepository.save(scholar);
    }

    @Override
    public ScholarResponse setUpSpecialist(String uuid, List<SpecialistSetup> specialistSetups) {
        Scholar scholar = scholarRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scholar not found")
        );
        List<Specialist> specialists = specialistSetups.stream()
                .map(specialistSetup -> {
                    Specialist specialist = new Specialist();
                    specialist.setUuid(UUID.randomUUID().toString());
                    specialist.setCountry(specialistSetup.country());
                    specialist.setSpecialist(specialistSetup.specialist());
                    specialist.setUniversityName(specialistSetup.universityName());
                    specialist.setAbout(specialistSetup.about());
                    specialist.setDegreeType(specialistSetup.degreeType());
                    return specialist;
                }).toList();
        scholar.setSpecialist(specialists);
        scholar = scholarRepository.save(scholar);
        return scholarMapper.fromScholar(scholar);
    }

    @Override
    public List<Specialist> getSpecialistSetups(String uuid) {
        Scholar scholar = scholarRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scholar not found")
        );
        return scholar.getSpecialist();
    }

    @Override
    public ScholarResponse setUpCareer(String uuid, List<CareerSetup> careerSetups) {
        Scholar scholar = scholarRepository.findByUuid(uuid).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Scholar not found")
        );
        List<Career> careers = careerSetups.stream()
                .map(careerSetup -> {
                    Career career = new Career();
                    career.setUuid(UUID.randomUUID().toString());
                    career.setCompany(careerSetup.company());
                    career.setInterest(careerSetup.interest());
                    career.setSalary(careerSetup.salary());
                    career.setCompanyType(careerSetup.companyType());
                    career.setPosition(careerSetup.position());
                    return career;
                }).toList();
        scholar.setCareers(careers);
        scholar = scholarRepository.save(scholar);
        return scholarMapper.fromScholar(scholar);
    }

    @Override
    public List<Career> getCareers(String uuid) {
        Scholar scholar = scholarRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scholar not found")
        );
        return scholar.getCareers();
    }

    @Override
    public List<ScholarResponse> getAllScholarsByClassRoomName(String classRoomName) {
        Class _class = classRepository.findByRoom(classRoomName).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found")
        );
        List<ScholarClass> scholarClasses = scholarClassRepository.findAllBy_class(_class);
        List<Scholar> scholars = scholarClasses.stream()
                .filter(scholarClass -> !scholarClass.getIsDeleted())
                .map(ScholarClass::getScholar)
                .toList();
        return scholars.stream()
                .map(scholarMapper::fromScholar)
                .toList();
    }

    @Override
    public List<ScholarResponse> getAllScholarsByProgramUuid(String programUuid) {
        Program program = programRepository.findByUuid(programUuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Program not found")
        );
        List<OpeningProgram> openingPrograms = openingProgramRepository.findAllByProgram(program)
                .stream()
                .filter(openingProgram -> !openingProgram.getIsDeleted())
                .toList();
        List<Class> classes = openingPrograms.stream()
                .filter(openingProgram -> !openingProgram.getIsDeleted())
                .flatMap(openingProgram -> openingProgram.getClasses().stream())
                .toList();
        List<ScholarClass> scholarClasses = classes.stream()
                .filter(aClass -> !aClass.getIsDeleted())
                .flatMap(aClass -> scholarClassRepository.findAllBy_class(aClass).stream())
                .filter(scholarClass -> !scholarClass.getIsDeleted())
                .toList();
        List<Scholar> scholars = scholarClasses.stream()
                .map(ScholarClass::getScholar)
                .toList();

        return scholars.stream()
                .map(scholarMapper::fromScholar)
                .toList();
    }

    @Override
    public ScholarResponse markCompletedCourse(String uuid, String openingProgramUuid) {
        Scholar scholar = scholarRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scholar not found")
        );
        OpeningProgram openingProgram = openingProgramRepository.findByUuid(openingProgramUuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program not found")
        );
        scholar.getCompletedCourses().add(openingProgram.getUuid());
        scholar = scholarRepository.save(scholar);
        return scholarMapper.fromScholar(scholar);
    }

    @Override
    public ScholarResponse unmarkCompletedCourse(String uuid, String openingProgramUuid) {
        Scholar scholar = scholarRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scholar not found")
        );
        OpeningProgram openingProgram = openingProgramRepository.findByUuid(openingProgramUuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program not found")
        );
        scholar.getCompletedCourses().remove(openingProgram.getUuid());
        scholar = scholarRepository.save(scholar);
        return scholarMapper.fromScholar(scholar);
    }

    @Override
    public List<String> getAllCompletedCoursesByScholarUuid(String uuid) {
        Scholar scholar = scholarRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scholar not found")
        );
        return scholar.getCompletedCourses();
    }

    @Override
    public List<ScholarResponse> getAllScholarsByStatus(ScholarStatus scholarStatus) {
        return scholarRepository.findAllByStatusAndIsDeletedFalse(scholarStatus).stream().map(scholarMapper::fromScholar).toList() ;
    }

    @Override
    public List<ScholarResponse> getAllAbroadScholars() {
        return scholarRepository.findAllByIsAbroadTrueAndIsDeletedFalse().stream().map(scholarMapper::fromScholar).toList() ;
    }
}
