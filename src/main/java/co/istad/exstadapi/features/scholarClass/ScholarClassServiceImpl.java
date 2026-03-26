package co.istad.exstadapi.features.scholarClass;

import co.istad.exstadapi.base.BasedMessage;
import co.istad.exstadapi.domain.Class;
import co.istad.exstadapi.domain.Scholar;
import co.istad.exstadapi.domain.ScholarClass;
import co.istad.exstadapi.features.classes.ClassRepository;
import co.istad.exstadapi.features.classes.dto.ClassResponse;
import co.istad.exstadapi.features.scholar.ScholarRepository;
import co.istad.exstadapi.features.scholar.dto.ScholarResponse;
import co.istad.exstadapi.features.scholarClass.dto.ScholarClassRequest;
import co.istad.exstadapi.features.scholarClass.dto.ScholarClassResponse;
import co.istad.exstadapi.features.scholarClass.dto.ScholarClassUpdate;
import co.istad.exstadapi.features.user.UserRepository;
import co.istad.exstadapi.features.user.dto.UserResponse;
import co.istad.exstadapi.mapper.ClassMapper;
import co.istad.exstadapi.mapper.ScholarClassMapper;
import co.istad.exstadapi.mapper.ScholarMapper;
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
public class ScholarClassServiceImpl implements ScholarClassService{
    private final ScholarClassRepository scholarClassRepository;
    private final ScholarRepository scholarRepository;
    private final ClassRepository classRepository;
    private final ScholarClassMapper scholarClassMapper;
    private final ScholarMapper scholarMapper;
    private final ClassMapper classMapper;

    @Override
    public ScholarClassResponse createScholarIntoClass(ScholarClassRequest scholarClassRequest) {
        if (!scholarRepository.existsByUuid(scholarClassRequest.scholarUuid())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Scholar with UUID "+ scholarClassRequest.scholarUuid() +" not found");
        }
        if (!classRepository.existsByUuid(scholarClassRequest.classUuid())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Class with UUID "+ scholarClassRequest.classUuid() +" not found");
        }
        ScholarClass scholarClass = scholarClassMapper.toScholarClassRequest(scholarClassRequest);
        scholarClass.setUuid(UUID.randomUUID().toString());
        scholarClass.setIsDeleted(false);
            scholarClass = scholarClassRepository.save(scholarClass);
            return scholarClassMapper.toScholarClassResponse(scholarClass);
    }

    @Override
    public List<ScholarClassResponse> getAllScholarClasses() {
        List<ScholarClass> scholarClasses = scholarClassRepository.findAllByIsDeletedFalse();
        return scholarClasses
                .stream()
                .map(scholarClassMapper::toScholarClassResponse)
                .toList();
    }

    @Override
    public List<ScholarClassResponse> getAllScholarClassesByClassCode(String classCode) {
        Class _class = classRepository.findByClassCodeIgnoreCase(classCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Class with code "+ classCode +" not found"));
        List<ScholarClass> scholarClasses = scholarClassRepository.findAllBy_class(_class);
        return scholarClasses
                .stream()
                .filter(scholarClass -> !scholarClass.getIsDeleted())
                .map(scholarClassMapper::toScholarClassResponse)
                .toList();
    }

    @Override
    public ScholarClassResponse getScholarClassByUuid(String uuid) {
        ScholarClass scholarClass = scholarClassRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ScholarClass with UUID "+ uuid +" not found"));
        return scholarClassMapper.toScholarClassResponse(scholarClass);
    }

    @Override
    public ScholarClassResponse updateScholarClassByUuid(String uuid, ScholarClassUpdate scholarClassUpdate) {
        ScholarClass scholarClass = scholarClassRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ScholarClass with UUID "+ uuid +" not found"));
        scholarClassMapper.updateScholarClassFromRequest(scholarClassUpdate, scholarClass);
        scholarClass = scholarClassRepository.save(scholarClass);
        return scholarClassMapper.toScholarClassResponse(scholarClass);
    }

    @Transactional
    @Override
    public BasedMessage softDeleteScholarClassByUuid(String uuid) {
        if (!scholarClassRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ScholarClass with UUID " + uuid + " not found");
        }
        scholarClassRepository.softDeleteByUuid(uuid);
        return new BasedMessage("Scholar Class " + uuid + " deleted successfully");
    }

    @Transactional
    @Override
    public BasedMessage restoreScholarClassByUuid(String uuid) {
        if (!scholarClassRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ScholarClass with UUID " + uuid + " not found");
        }
        scholarClassRepository.restoreByUuid(uuid);
        return new BasedMessage("Scholar Class " + uuid + " restored successfully");
    }

    @Transactional
    @Override
    public BasedMessage hardDeleteScholarClassByUuid(String uuid) {
        if (!scholarClassRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ScholarClass with UUID " + uuid + " not found");
        }
        scholarClassRepository.deleteByUuid(uuid);
        return new BasedMessage("Scholar Class " + uuid + " deleted permanently");
    }

    @Override
    public List<ClassResponse> getAllClassesByOneScholarUuid(String scholarUuid) {
        Scholar scholar = scholarRepository.findByUuid(scholarUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scholar with UUID "+ scholarUuid +" not found"));
        List<ScholarClass> scholarClasses = scholarClassRepository.findAllByScholar(scholar);
        List<Class> classes = scholarClasses
                .stream()
                .filter(scholarClass -> !scholarClass.getIsDeleted())
                .map(ScholarClass::get_class)
                .toList();
        return classes
                .stream()
                .filter(aClass -> !aClass.getIsDeleted())
                .map(classMapper::toClassResponse)
                .toList();
    }

    @Override
    public List<ScholarResponse> getAllScholarsByOneClassUuid(String classUuid) {
        Class aClass = classRepository.findByUuid(classUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Class with UUID "+ classUuid +" not found"));
        List<ScholarClass> scholarClasses = scholarClassRepository.findAllBy_class(aClass);
        List<Scholar> scholars = scholarClasses
                .stream()
                .filter(scholarClass -> !scholarClass.getIsDeleted())
                .map(ScholarClass::getScholar)
                .toList();
        return scholars
                .stream()
                .filter(scholar -> !scholar.getIsDeleted())
                .map(scholarMapper::fromScholar)
                .toList();
    }

    @Override
    public List<ScholarClassResponse> getAllScholarsClassesByOneClassUuid(String classUuid) {
        Class _class = classRepository.findByUuid(classUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Class with UUID "+ classUuid +" not found"));
        List<ScholarClass> scholarClasses = scholarClassRepository.findAllBy_class(_class);
        return scholarClasses.stream()
                .filter(scholarClass -> !scholarClass.getIsDeleted())
                .map(scholarClassMapper::toScholarClassResponse)
                .toList();
    }

    @Transactional
    @Override
    public BasedMessage markAsPaid(String uuid) {
        if (!scholarClassRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ScholarClass with UUID " + uuid + " not found");
        }
            scholarClassRepository.markAsPaidByUuid(uuid);
            return new BasedMessage("Scholar Class " + uuid + " has been marked as paid");
    }

    @Transactional
    @Override
    public BasedMessage markAsUnpaid(String uuid) {
        if (!scholarClassRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ScholarClass with UUID " + uuid + " not found");
        }
            scholarClassRepository.markAsUnpaidByUuid(uuid);
            return new BasedMessage("Scholar Class " + uuid + " has been marked as unpaid");
    }

    @Transactional
    @Override
    public BasedMessage markAsReminded(String uuid) {
        if (!scholarClassRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ScholarClass with UUID " + uuid + " not found");
        }
            scholarClassRepository.markAsRemindedByUuid(uuid);
            return new BasedMessage("Scholar Class " + uuid + " has been marked as reminded");
    }

    @Transactional
    @Override
    public BasedMessage markAsUnreminded(String uuid) {
        if (!scholarClassRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ScholarClass with UUID " + uuid + " not found");
        }
            scholarClassRepository.markAsUnremindedByUuid(uuid);
            return new BasedMessage("Scholar Class " + uuid + " has been marked as unreminded");
    }
}
