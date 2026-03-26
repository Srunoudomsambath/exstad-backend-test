package co.istad.exstadapi.features.instructorClass;

import co.istad.exstadapi.base.BasedMessage;
import co.istad.exstadapi.domain.Class;
import co.istad.exstadapi.domain.InstructorClass;
import co.istad.exstadapi.domain.User;
import co.istad.exstadapi.enums.Role;
import co.istad.exstadapi.features.classes.ClassRepository;
import co.istad.exstadapi.features.classes.dto.ClassResponse;
import co.istad.exstadapi.features.instructorClass.dto.InstructorClassRequest;
import co.istad.exstadapi.features.instructorClass.dto.InstructorClassResponse;
import co.istad.exstadapi.features.instructorClass.dto.InstructorClassUpdate;
import co.istad.exstadapi.features.user.UserRepository;
import co.istad.exstadapi.features.user.dto.UserResponse;
import co.istad.exstadapi.mapper.ClassMapper;
import co.istad.exstadapi.mapper.InstructorClassMapper;
import co.istad.exstadapi.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InstructorClassServiceImpl implements InstructorClassService{
    private final UserRepository userRepository;
    private final ClassRepository classRepository;
    private final InstructorClassMapper instructorClassMapper;
    private final InstructorClassRepository instructorClassRepository;
    private final ClassMapper classMapper;
    private final UserMapper userMapper;


    @Override
    public InstructorClassResponse addInstructorIntoClass(InstructorClassRequest instructorClassRequest) {
        if (!userRepository.existsByUuid(instructorClassRequest.instructorUuid())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Instructor not found");
        }
        if (!classRepository.existsByUuid(instructorClassRequest.classUuid())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found");
        }
        InstructorClass instructorClass = instructorClassMapper.fromInstructorClassRequest(instructorClassRequest);
        instructorClass.setIsDeleted(false);
        instructorClass.setUuid(UUID.randomUUID().toString());
        instructorClass = instructorClassRepository.save(instructorClass);
        return instructorClassMapper.toInstructorClassResponse(instructorClass);
    }

    @Override
    public List<InstructorClassResponse> getAllInstructorsClasses() {
        List<InstructorClass> instructorClasses = instructorClassRepository.findAll();
        return instructorClasses
                .stream()
                .filter(instructorClass -> !instructorClass.getIsDeleted())
                .map(instructorClassMapper::toInstructorClassResponse)
                .toList();
    }

    @Override
    public List<InstructorClassResponse> getAllInstructorsClassesByClassUuid(String classUuid) {
        Class _class = classRepository.findByUuid(classUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found"));
        List<InstructorClass> instructorClasses = instructorClassRepository.findAllBy_class(_class);
        return instructorClasses
                .stream()
                .filter(instructorClass -> !instructorClass.getIsDeleted())
                .map(instructorClassMapper::toInstructorClassResponse)
                .toList();
    }

    @Override
    public InstructorClassResponse getInstructorClassByUuid(String uuid) {
        InstructorClass instructorClass = instructorClassRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instructor-Class not found"));
        return instructorClassMapper.toInstructorClassResponse(instructorClass);
    }

    @Override
    public InstructorClassResponse updateInstructorClassByUuid(String uuid, InstructorClassUpdate instructorClassUpdate) {
        InstructorClass instructorClass = instructorClassRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instructor-Class not found"));
        instructorClassMapper.instructorClassUpdate(instructorClassUpdate, instructorClass);
        instructorClass = instructorClassRepository.save(instructorClass);
        return instructorClassMapper.toInstructorClassResponse(instructorClass);
    }

    @Override
    public BasedMessage softDeleteInstructorClassByUuid(String uuid) {
        InstructorClass instructorClass = instructorClassRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instructor-Class not found"));
        instructorClass.setIsDeleted(true);
        instructorClassRepository.save(instructorClass);
        return new BasedMessage("Instructor-Class deleted successfully");
    }

    @Override
    public BasedMessage restoreInstructorClassByUuid(String uuid) {
        InstructorClass instructorClass = instructorClassRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instructor-Class not found"));
        instructorClass.setIsDeleted(false);
        instructorClassRepository.save(instructorClass);
        return new BasedMessage("Instructor-Class restored successfully");
    }

    @Transactional
    @Override
    public BasedMessage hardDeleteInstructorClassByUuid(String uuid) {
        if (!instructorClassRepository.existsByUuid(uuid)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Instructor-Class not found");
        }
        instructorClassRepository.deleteByUuid(uuid);
        return new BasedMessage("Instructor-Class deleted permanently");
    }

    @Override
    public List<ClassResponse> getAllClassesByOneInstructorUuid(String instructorUuid) {
        User instructor = userRepository.findByUuid(instructorUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Instructor not found"));
        List<InstructorClass> instructorClasses = instructorClassRepository.findAllByInstructor(instructor);
        List<Class> classes = instructorClasses
                .stream()
                .filter(instructorClass -> !instructorClass.getIsDeleted())
                .map(InstructorClass::get_class)
                .toList();
        return classes
                .stream()
                .filter(_class -> !_class.getIsDeleted())
                .map(classMapper::toClassResponse)
                .toList();
    }

    @Override
    public List<UserResponse> getAllInstructorsByOneClassUuid(String classUuid) {
        Class _class = classRepository.findByUuid(classUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Class not found"));
        List<InstructorClass> instructorClasses = instructorClassRepository
                .findAllBy_class(_class);
        List<User> instructors = instructorClasses
                .stream()
                .filter(instructorClass -> !instructorClass.getIsDeleted())
                .map(InstructorClass::getInstructor)
                .toList();
        return instructors
                .stream()
                .filter(instructor -> !instructor.getIsDeleted())
                .map(userMapper::fromUser)
                .toList();
    }

    @Override
    public List<UserResponse> getAllInstructors() {
        return userRepository
                .findAll()
                .stream()
                .filter(instructor -> instructor.getRole().equals(Role.INSTRUCTOR1) || instructor.getRole().equals(Role.INSTRUCTOR2))
                .map(userMapper::fromUser)
                .toList();
    }
}
