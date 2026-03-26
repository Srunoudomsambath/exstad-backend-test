package co.istad.exstadapi.features.scholar;

import co.istad.exstadapi.base.BasedMessage;
import co.istad.exstadapi.domain.vo.Career;
import co.istad.exstadapi.domain.vo.Specialist;
import co.istad.exstadapi.enums.ScholarStatus;
import co.istad.exstadapi.features.scholar.career.dto.CareerSetup;
import co.istad.exstadapi.features.scholar.dto.*;
import co.istad.exstadapi.features.scholar.specialist.dto.SpecialistSetup;

import java.util.List;

public interface ScholarService {

    ScholarResponse createScholar(ScholarRequest scholarRequest);
    ScholarResponse updateScholar(String uuid, ScholarRequestUpdate scholarRequestUpdate);
    List<ScholarResponse> createMultipleScholars(List<ScholarRequest> scholarRequests);

    List<ScholarResponse> findAllScholars();
    ScholarResponse findByUuid(String uuid);
    ScholarResponse findByUsername(String username);
    List<ScholarResponse> searchByEnglishName(String englishName);
    List<ScholarResponse> searchByUsername(String username);

    Long countScholars();

    BasedMessage softDeleteScholarByUuid(String uuid);
    BasedMessage restoreScholarByUuid(String uuid);
    BasedMessage hardDeleteScholarByUuid(String uuid);
    BasedMessage markIsEmployed(String uuid);
    BasedMessage unmarkIsEmployed(String uuid);
    BasedMessage markIsAbroad(String uuid);
    BasedMessage unmarkIsAbroad(String uuid);

    List<ScholarResponse> getAllScholarsByOpeningProgramUuid(String openingProgramUuid);
    List<ScholarResponse> getAllScholarsByStatus(ScholarStatus scholarStatus);
    List<ScholarResponse> getAllAbroadScholars();
    ScholarResponse getCurrentScholar();
    ScholarResponse updateCurrentScholar(ScholarRequestUpdate scholarRequestUpdate);

    SocialLinkResponse setUpScholarSocialLink(String uuid, SocialLinkRequest socialLinkRequest);
    List<SocialLinkResponse> getScholarSocialLink(String uuid);
    SocialLinkResponse updateSocialLinkStatus(String scholarUuid, String socialLinkUuid, boolean status);
    void deleteSocialLink(String scholarUuid, String socialLinkUuid);

    ScholarResponse setUpSpecialist(String uuid, List<SpecialistSetup> specialistSetups);
    List<Specialist> getSpecialistSetups(String uuid);

    ScholarResponse setUpCareer(String uuid, List<CareerSetup> careerSetups);
    List<Career> getCareers(String uuid);

    List<ScholarResponse> getAllScholarsByClassRoomName(String classRoomName);
    List<ScholarResponse> getAllScholarsByProgramUuid(String programUuid);

    ScholarResponse markCompletedCourse(String uuid, String openingProgramUuid);
    ScholarResponse unmarkCompletedCourse(String uuid, String openingProgramUuid);
    List<String> getAllCompletedCoursesByScholarUuid(String uuid);
}
