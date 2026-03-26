package co.istad.exstadapi.mapper;

import co.istad.exstadapi.audit.AuditableMapper;
import co.istad.exstadapi.domain.InstructorClass;
import co.istad.exstadapi.features.instructorClass.dto.InstructorClassRequest;
import co.istad.exstadapi.features.instructorClass.dto.InstructorClassResponse;
import co.istad.exstadapi.features.instructorClass.dto.InstructorClassUpdate;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {
        AuditableMapper.class, ClassMapper.class, UserMapper.class, MapperHelper.class
})
public interface InstructorClassMapper {

    @Mapping(source = "instructorClass", target = "audit")
    @Mapping(source = "instructor.uuid", target = "instructorUuid")
    @Mapping(source = "instructor.username", target = "instructorUsername")
    @Mapping(source = "_class.uuid", target = "classUuid")
    InstructorClassResponse toInstructorClassResponse(InstructorClass instructorClass);

    @Mapping(source = "instructorUuid", target = "instructor", qualifiedByName = "toInstructorByUuid")
    @Mapping(source = "classUuid", target = "_class", qualifiedByName = "toClassByUuid")
    InstructorClass fromInstructorClassRequest(InstructorClassRequest instructorClassRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "instructorUuid", target = "instructor", qualifiedByName = "toInstructorByUuid")
    @Mapping(source = "classUuid", target = "_class", qualifiedByName = "toClassByUuid")
    void instructorClassUpdate(InstructorClassUpdate instructorClassUpdate, @MappingTarget InstructorClass instructorClass);
}
