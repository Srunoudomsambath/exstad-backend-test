package co.istad.exstadapi.mapper;

import co.istad.exstadapi.audit.AuditableMapper;
import co.istad.exstadapi.domain.Badge;
import co.istad.exstadapi.features.badge.dto.BadgeRequest;
import co.istad.exstadapi.features.badge.dto.BadgeResponse;
import co.istad.exstadapi.features.badge.dto.BadgeUpdate;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {AuditableMapper.class})
public interface BadgeMapper {

    @Mapping(target = "audit", source = "badge")
    BadgeResponse toBadgeResponse(Badge badge);

    Badge fromBadgeRequest(BadgeRequest badgeRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    void updateBadgeFromRequest(BadgeUpdate badgeUpdate, @MappingTarget Badge badge);

}
