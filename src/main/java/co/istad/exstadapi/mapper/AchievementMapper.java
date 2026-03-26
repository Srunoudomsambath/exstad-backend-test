package co.istad.exstadapi.mapper;

import co.istad.exstadapi.audit.AuditableMapper;
import co.istad.exstadapi.domain.Achievement;
import co.istad.exstadapi.features.achievement.dto.AchievementRequest;
import co.istad.exstadapi.features.achievement.dto.AchievementRequestUpdate;
import co.istad.exstadapi.features.achievement.dto.AchievementResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring",uses = {AuditableMapper.class, MapperHelper.class})
public interface AchievementMapper
{
    @Mapping(source = "openingProgramUuid", target = "openingProgram", qualifiedByName = "toOpeningProgramByUuid")
    Achievement toAchievement(AchievementRequest achievementRequest);

    @Mapping(source = ".", target = "audit")
    @Mapping(source = "openingProgram.program.title", target = "program")
    AchievementResponse fromAchievement(Achievement achievement);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toAchievementPartially(AchievementRequestUpdate achievementRequestUpdate, @MappingTarget Achievement achievement);

}
