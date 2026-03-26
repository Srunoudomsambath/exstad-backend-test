package co.istad.exstadapi.features.classes;

import co.istad.exstadapi.base.BasedMessage;
import co.istad.exstadapi.features.classes.dto.ClassRequest;
import co.istad.exstadapi.features.classes.dto.ClassResponse;
import co.istad.exstadapi.features.classes.dto.ClassUpdate;

import java.util.List;

public interface ClassService {
    List<ClassResponse> getAllClasses();
    ClassResponse getClassByUuid(String uuid);
    ClassResponse getClassByName(String name);
    List<ClassResponse> getClassByOpeningProgramTitle(String openingProgramTitle);
    List<ClassResponse> getAllClassesByOpeningProgramUuid(String openingProgramUuid);
    ClassResponse getClassByClassCode(String classCode);
    ClassResponse createClass(ClassRequest classRequest);
    ClassResponse updateClass(String uuid, ClassUpdate classUpdate);
    BasedMessage softDeleteClass(String uuid);
    BasedMessage restoreClass(String uuid);
    BasedMessage hardDeleteClass(String uuid);
    BasedMessage disableClass(String uuid);
    BasedMessage enableClass(String uuid);
    BasedMessage setToWeekend(String uuid);
    BasedMessage setToWeekday(String uuid);
}
