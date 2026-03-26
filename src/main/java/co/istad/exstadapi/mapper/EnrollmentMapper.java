package co.istad.exstadapi.mapper;

import co.istad.exstadapi.audit.AuditableMapper;
import co.istad.exstadapi.domain.Enrollment;
import co.istad.exstadapi.features.enrollment.dto.EnrollmentRequest;
import co.istad.exstadapi.features.enrollment.dto.EnrollmentRequestUpdate;
import co.istad.exstadapi.features.enrollment.dto.EnrollmentResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {MapperHelper.class, ClassMapper.class, AuditableMapper.class})
public interface EnrollmentMapper {

    @Mapping(source = "university", target = "university", qualifiedByName = "toUniversity")
    @Mapping(source = "currentAddress", target = "currentAddress", qualifiedByName = "toCurrentAddress")
    @Mapping(source = "province", target = "province", qualifiedByName = "toProvince")
    @Mapping(source = "openingProgramUuid", target = "openingProgram", qualifiedByName = "toOpeningProgramByUuid")
    @Mapping(source = "classUuid", target = "_class", qualifiedByName = "toClassByUuid", conditionExpression = "java(enrollmentRequest.classUuid() != null)")
    Enrollment toEnrollment(EnrollmentRequest enrollmentRequest);

    @Mapping(source = "university.englishName", target = "university")
    @Mapping(source = "province.englishName", target = "province")
    @Mapping(source = "currentAddress.englishName", target = "currentAddress")
    @Mapping(source = "openingProgram.program.title", target = "program")
    @Mapping(source = ".", target = "audit")
    EnrollmentResponse fromEnrollment(Enrollment enrollment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "university", target = "university", qualifiedByName = "toUniversity")
    @Mapping(source = "currentAddress", target = "currentAddress", qualifiedByName = "toCurrentAddress")
    @Mapping(source = "province", target = "province", qualifiedByName = "toProvince")
    void toEnrollmentPartially(EnrollmentRequestUpdate enrollmentRequestUpdate, @MappingTarget Enrollment enrollment);

//    default String getFirstClassRoom(Enrollment enrollment) {
//        if (enrollment.getOpeningProgram() != null &&
//            enrollment.getOpeningProgram().getClasses() != null &&
//            !enrollment.getOpeningProgram().getClasses().isEmpty()) {
//            return enrollment.getOpeningProgram().getClasses().get(0).getRoom();
//        }
//        return null;
//    }
//
//    default String getFirstClassShift(Enrollment enrollment) {
//        if (enrollment.getOpeningProgram() != null &&
//            enrollment.getOpeningProgram().getClasses() != null &&
//            !enrollment.getOpeningProgram().getClasses().isEmpty()) {
//            return enrollment.getOpeningProgram().getClasses().get(0).getShift().toString();
//        }
//        return null;
//    }
}
