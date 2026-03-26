package co.istad.exstadapi.features.classes;

import co.istad.exstadapi.base.BasedMessage;
import co.istad.exstadapi.domain.Class;
import co.istad.exstadapi.domain.OpeningProgram;
import co.istad.exstadapi.features.classes.dto.ClassRequest;
import co.istad.exstadapi.features.classes.dto.ClassResponse;
import co.istad.exstadapi.features.classes.dto.ClassUpdate;
import co.istad.exstadapi.features.openingProgram.OpeningProgramRepository;
import co.istad.exstadapi.mapper.ClassMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {
    private final ClassRepository classRepository;
    private final OpeningProgramRepository openingProgramRepository;
    private final ClassMapper classMapper;

    @Override
    public List<ClassResponse> getAllClasses() {
        List<Class> classes = classRepository.findAllByIsDeletedFalse();
        return classes
                .stream()
                .map(classMapper::toClassResponse)
                .toList();
    }

    @Override
    public ClassResponse getClassByUuid(String uuid) {
        Class aClass = classRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Class with UUID " + uuid + " not found"));
        return classMapper.toClassResponse(aClass);
    }

    @Override
    public ClassResponse getClassByName(String name) {
        Class aClass = classRepository.findByRoomIgnoreCase(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Class with name " + name + " not found"));
        return classMapper.toClassResponse(aClass);
    }

    @Override
    public List<ClassResponse> getClassByOpeningProgramTitle(String openingProgramTitle) {
        OpeningProgram openingProgram = openingProgramRepository.findByTitleIgnoreCase(openingProgramTitle)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program with title " + openingProgramTitle + " not found"));
        return classRepository.findAllByOpeningProgramAndIsDeletedFalse(openingProgram).stream().map(classMapper::toClassResponse).toList();
    }

    @Override
    public List<ClassResponse> getAllClassesByOpeningProgramUuid(String openingProgramUuid) {
        OpeningProgram openingProgram = openingProgramRepository.findByUuid(openingProgramUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program with UUID " + openingProgramUuid + " not found"));
        List<Class> classes = classRepository.findAllByOpeningProgramAndIsDeletedFalse(openingProgram);
        return classes.stream().map(classMapper::toClassResponse).toList();
    }

    @Override
    public ClassResponse getClassByClassCode(String classCode) {
        Class _class = classRepository.findByClassCode(classCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Class with class code " + classCode + " not found"));
        return classMapper.toClassResponse(_class);
    }


    @Override
    public ClassResponse createClass(ClassRequest classRequest) {
        if (classRepository.existsByClassCode(classRequest.classCode())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Class code already exists");
        }
        Class aClass = classMapper.fromClassRequest(classRequest);
        aClass.setUuid(UUID.randomUUID().toString());
        aClass.setIsDeleted(false);
        aClass.setIsEnabled(true);
        aClass = classRepository.save(aClass);
        return classMapper.toClassResponse(aClass);
    }

    @Override
    public ClassResponse updateClass(String uuid ,ClassUpdate classUpdate) {
        Class aClass = classRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Class with UUID " + uuid + " not found"));
        classMapper.updateClassFromRequest(classUpdate, aClass);
        aClass = classRepository.save(aClass);
        return classMapper.toClassResponse(aClass);
    }

    @Transactional
    @Override
    public BasedMessage softDeleteClass(String uuid) {
        if (!classRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class with UUID " + uuid + " not found");
        }
        classRepository.softDeleteByUuid(uuid);
        return new BasedMessage("Class " + uuid + " has been deleted!");
    }

    @Transactional
    @Override
    public BasedMessage restoreClass(String uuid) {
        if (!classRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class with UUID " + uuid + " not found");
        }
        classRepository.restoreByUuid(uuid);
        return new BasedMessage("Class " + uuid + " has been restored!");
    }

    @Transactional
    @Override
    public BasedMessage hardDeleteClass(String uuid) {
        if (!classRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class with UUID " + uuid + " not found");
        }
        classRepository.deleteByUuid(uuid);
        return new BasedMessage("Class " + uuid + " has been permanently deleted!");
    }

    @Transactional
    @Override
    public BasedMessage disableClass(String uuid) {
        if (!classRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class with UUID " + uuid + " not found");
        }
        classRepository.disableByUuid(uuid);
        return new BasedMessage("Class " + uuid + " has been disabled!");
    }

    @Transactional
    @Override
    public BasedMessage enableClass(String uuid) {
        if (!classRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class with UUID " + uuid + " not found");
        }
        classRepository.enableByUuid(uuid);
        return new BasedMessage("Class " + uuid + " has been enabled!");
    }

    @Transactional
    @Override
    public BasedMessage setToWeekend(String uuid) {
        if (!classRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class with UUID " + uuid + " not found");
        }
        classRepository.setToWeekendByUuid(uuid);
        return new BasedMessage("Class " + uuid + " has been set to weekend!");
    }

    @Transactional
    @Override
    public BasedMessage setToWeekday(String uuid) {
        if (!classRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class with UUID " + uuid + " not found");
        }
        classRepository.setToWeekdayByUuid(uuid);
        return new BasedMessage("Class " + uuid + " has been set to weekday!");
    }
}
