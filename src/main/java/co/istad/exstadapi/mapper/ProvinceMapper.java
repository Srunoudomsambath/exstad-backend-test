package co.istad.exstadapi.mapper;

import co.istad.exstadapi.audit.AuditableMapper;
import co.istad.exstadapi.domain.Province;
import co.istad.exstadapi.features.province.dto.ProvinceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AuditableMapper.class})
public interface ProvinceMapper {

    @Mapping(target = "scholars", expression = "java(province.getScholars() != null ? (long) province.getScholars().size() : 0L)" )
    @Mapping(target = "audit", source = "province")
    ProvinceResponse toProvinceResponse(Province province);
}
