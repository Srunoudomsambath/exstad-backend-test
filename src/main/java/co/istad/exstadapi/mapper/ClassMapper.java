package co.istad.exstadapi.mapper;

import co.istad.exstadapi.audit.AuditableMapper;
import co.istad.exstadapi.domain.Class;
import co.istad.exstadapi.features.classes.dto.ClassRequest;
import co.istad.exstadapi.features.classes.dto.ClassResponse;
import co.istad.exstadapi.features.classes.dto.ClassUpdate;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {
        AuditableMapper.class, MapperHelper.class
})
public interface ClassMapper {

    @Mapping(target = "openingProgramName", source = "openingProgram.title")
    @Mapping(target = "audit", source = "aClass")
    ClassResponse toClassResponse(Class aClass);

    @Mapping(target = "openingProgram", source = "openingProgramUuid", qualifiedByName = "toOpeningProgramTitle")
    Class fromClassRequest(ClassRequest classRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    void updateClassFromRequest(ClassUpdate classUpdate,@MappingTarget Class aClass);
}
