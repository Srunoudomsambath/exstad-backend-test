package co.istad.exstadapi.mapper;

import co.istad.exstadapi.audit.AuditableMapper;
import co.istad.exstadapi.domain.ScholarAchievement;
import co.istad.exstadapi.features.scholarAchievement.dto.ScholarAchievementRequest;
import co.istad.exstadapi.features.scholarAchievement.dto.ScholarAchievementResponseForAchievement;
import co.istad.exstadapi.features.scholarAchievement.dto.ScholarAchievementResponseForScholar;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AuditableMapper.class, MapperHelper.class, ScholarMapper.class, AchievementMapper.class})
public interface ScholarAchievementMapper {

    @Mapping(source = "achievementUuid", target = "achievement", qualifiedByName = "toAchievementByUuid")
    ScholarAchievement toScholarAchievement(ScholarAchievementRequest scholarAchievementRequest);

    @Mapping(source = ".", target = "audit")
    @Mapping(source = "achievement", target = "achievement")
    ScholarAchievementResponseForScholar fromScholarAchievement(ScholarAchievement scholarAchievement);

    @Mapping(source = ".", target = "audit")
    @Mapping(source = "scholar", target = "scholar")
    ScholarAchievementResponseForAchievement fromScholarAchievementToAchievement(ScholarAchievement scholarAchievement);
}
