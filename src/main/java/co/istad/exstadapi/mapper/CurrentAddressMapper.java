package co.istad.exstadapi.mapper;

import co.istad.exstadapi.audit.AuditableMapper;
import co.istad.exstadapi.domain.CurrentAddress;
import co.istad.exstadapi.features.currentAddress.dto.CurrentAddressRequest;
import co.istad.exstadapi.features.currentAddress.dto.CurrentAddressResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MapperHelper.class, AuditableMapper.class})
public interface CurrentAddressMapper
{

    @Mapping(source = "province", target = "province", qualifiedByName = "toProvince")
    CurrentAddress toCurrentAddress(CurrentAddressRequest currentAddressRequest);

    @Mapping(source = "province.englishName", target = "province")
    @Mapping(target = "scholars", expression = "java(currentAddress.getScholars() != null ? (long) currentAddress.getScholars().size() : 0L)")
    @Mapping(source = "currentAddress", target = "audit")
    CurrentAddressResponse fromCurrentAddress(CurrentAddress currentAddress);
}
