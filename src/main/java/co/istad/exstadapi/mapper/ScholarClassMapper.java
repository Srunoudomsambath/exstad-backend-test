package co.istad.exstadapi.mapper;

import co.istad.exstadapi.audit.AuditableMapper;
import co.istad.exstadapi.domain.ScholarClass;
import co.istad.exstadapi.features.scholarClass.dto.ScholarClassRequest;
import co.istad.exstadapi.features.scholarClass.dto.ScholarClassResponse;
import co.istad.exstadapi.features.scholarClass.dto.ScholarClassUpdate;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {
        AuditableMapper.class, MapperHelper.class,ScholarMapper.class, ClassMapper.class
})
public interface ScholarClassMapper {

    @Mapping(target = "audit", source = "scholarClass")
    @Mapping(target = "classUuid", source = "_class.uuid")
    @Mapping(target = "room", source = "_class.room")
    ScholarClassResponse toScholarClassResponse(ScholarClass scholarClass);

    @Mapping(target = "_class", source = "classUuid", qualifiedByName = "toClassByUuid")
    @Mapping(target = "scholar", source = "scholarUuid", qualifiedByName = "toScholar")
    ScholarClass toScholarClassRequest(ScholarClassRequest scholarClassRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    void updateScholarClassFromRequest(ScholarClassUpdate scholarClassUpdate, @MappingTarget ScholarClass scholarClass);
}
