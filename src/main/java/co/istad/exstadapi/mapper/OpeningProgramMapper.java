package co.istad.exstadapi.mapper;

import co.istad.exstadapi.audit.AuditableMapper;
import co.istad.exstadapi.domain.OpeningProgram;
import co.istad.exstadapi.features.openingProgram.dto.OpeningProgramRequest;
import co.istad.exstadapi.features.openingProgram.dto.OpeningProgramResponse;
import co.istad.exstadapi.features.openingProgram.dto.OpeningProgramUpdate;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = {AuditableMapper.class, MapperHelper.class})
public interface OpeningProgramMapper {

    @Mapping(target = "audit", source = "openingProgram")
    @Mapping(target = "programName", source = "program.title")
    OpeningProgramResponse toOpeningProgramResponse(OpeningProgram openingProgram);

    @Mapping(target = "program", source = "programUuid",
            qualifiedByName = "toProgramTitle")
    OpeningProgram fromOpeningProgramRequest(OpeningProgramRequest openingProgramRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOpeningProgramFromRequest(OpeningProgramUpdate openingProgramUpdate,
                                         @MappingTarget OpeningProgram openingProgram);
}

