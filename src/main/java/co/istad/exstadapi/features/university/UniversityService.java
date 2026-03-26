package co.istad.exstadapi.features.university;

import co.istad.exstadapi.base.BasedMessage;
import co.istad.exstadapi.features.university.dto.UniversityRequest;
import co.istad.exstadapi.features.university.dto.UniversityResponse;
import co.istad.exstadapi.features.university.dto.UniversityUpdate;

import java.util.List;

public interface UniversityService {
    List<UniversityResponse> getAllUniversities();
    UniversityResponse getUniversityByUuid(String uuid);
    UniversityResponse createUniversity(UniversityRequest universityRequest);
    BasedMessage deleteUniversityByUuid(String uuid);
    UniversityResponse updateUniversityByUuid(String uuid, UniversityUpdate universityUpdate);
}
