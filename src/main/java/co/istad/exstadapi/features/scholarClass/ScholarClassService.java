package co.istad.exstadapi.features.scholarClass;

import co.istad.exstadapi.base.BasedMessage;
import co.istad.exstadapi.features.classes.dto.ClassResponse;
import co.istad.exstadapi.features.scholar.dto.ScholarResponse;
import co.istad.exstadapi.features.scholarClass.dto.ScholarClassRequest;
import co.istad.exstadapi.features.scholarClass.dto.ScholarClassResponse;
import co.istad.exstadapi.features.scholarClass.dto.ScholarClassUpdate;
import co.istad.exstadapi.features.user.dto.UserResponse;

import java.util.List;

public interface ScholarClassService {

    ScholarClassResponse createScholarIntoClass(ScholarClassRequest scholarClassRequest);
    List<ScholarClassResponse> getAllScholarClasses();
    List<ScholarClassResponse> getAllScholarClassesByClassCode(String classCode);
    ScholarClassResponse getScholarClassByUuid(String uuid);
    ScholarClassResponse updateScholarClassByUuid(String uuid, ScholarClassUpdate scholarClassUpdate);
    BasedMessage softDeleteScholarClassByUuid(String uuid);
    BasedMessage restoreScholarClassByUuid(String uuid);
    BasedMessage hardDeleteScholarClassByUuid(String uuid);

    List<ClassResponse> getAllClassesByOneScholarUuid(String scholarUuid);
    List<ScholarResponse> getAllScholarsByOneClassUuid(String classUuid);
    List<ScholarClassResponse> getAllScholarsClassesByOneClassUuid(String classUuid);

    BasedMessage markAsPaid(String uuid);
    BasedMessage markAsUnpaid(String uuid);
    BasedMessage markAsReminded(String uuid);
    BasedMessage markAsUnreminded(String uuid);
}
