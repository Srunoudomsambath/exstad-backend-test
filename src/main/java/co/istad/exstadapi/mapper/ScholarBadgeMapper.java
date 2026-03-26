package co.istad.exstadapi.mapper;

import co.istad.exstadapi.domain.ScholarBadge;
import co.istad.exstadapi.features.scholarBadge.dto.ScholarBadgeRequest;
import co.istad.exstadapi.features.scholarBadge.dto.ScholarBadgeRequestUpdate;
import co.istad.exstadapi.features.scholarBadge.dto.ScholarBadgeResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MapperHelper.class, BadgeMapper.class, ScholarMapper.class})
public interface ScholarBadgeMapper {

    @Mapping(source = "badgeUuid", target = "badge", qualifiedByName = "toBadge")
    @Mapping(source = "scholarUuid", target = "scholar", qualifiedByName = "toScholar")
    ScholarBadge toScholarBadge(ScholarBadgeRequest scholarBadgeRequest);

    @Mapping(source = "badge", target = "badge")
    @Mapping(source = "scholar", target = "scholar")
    ScholarBadgeResponse fromScholarBadge(ScholarBadge scholarBadge);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toScholarBadgePartially(ScholarBadgeRequestUpdate scholarBadgeRequestUpdate, @MappingTarget ScholarBadge scholarBadge);

    List<ScholarBadgeResponse> toScholarBadgeResponses(List<ScholarBadge> scholarBadges);
}
