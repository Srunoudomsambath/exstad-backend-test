package co.istad.exstadapi.features.instructorClass;

import co.istad.exstadapi.base.BasedMessage;
import co.istad.exstadapi.features.classes.dto.ClassResponse;
import co.istad.exstadapi.features.instructorClass.dto.InstructorClassRequest;
import co.istad.exstadapi.features.instructorClass.dto.InstructorClassResponse;
import co.istad.exstadapi.features.instructorClass.dto.InstructorClassUpdate;
import co.istad.exstadapi.features.university.UniversityService;
import co.istad.exstadapi.features.user.dto.UserResponse;

import java.util.List;

public interface InstructorClassService {

    InstructorClassResponse addInstructorIntoClass(InstructorClassRequest instructorClassRequest);
    List<InstructorClassResponse> getAllInstructorsClasses();
    List<InstructorClassResponse> getAllInstructorsClassesByClassUuid(String classUuid);
    InstructorClassResponse getInstructorClassByUuid(String uuid);
    InstructorClassResponse updateInstructorClassByUuid(String uuid, InstructorClassUpdate instructorClassUpdate);
    BasedMessage softDeleteInstructorClassByUuid(String uuid);
    BasedMessage restoreInstructorClassByUuid(String uuid);
    BasedMessage hardDeleteInstructorClassByUuid(String uuid);
    List<ClassResponse> getAllClassesByOneInstructorUuid(String instructorUuid);
    List<UserResponse> getAllInstructorsByOneClassUuid(String classUuid);
    List<UserResponse> getAllInstructors();
}
