package co.istad.exstadapi.mapper;

import co.istad.exstadapi.audit.AuditableMapper;
import co.istad.exstadapi.domain.Program;
import co.istad.exstadapi.features.program.dto.ProgramRequest;
import co.istad.exstadapi.features.program.dto.ProgramResponse;
import co.istad.exstadapi.features.program.dto.ProgramUpdate;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {AuditableMapper.class})
public interface ProgramMapper {

    @Mapping(target = "audit", source = "program")
    ProgramResponse toProgramResponse(Program program);

    Program fromProgramRequest(ProgramRequest programRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    void toProgramUpdate(ProgramUpdate programUpdate, @MappingTarget Program program);
}
