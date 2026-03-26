package co.istad.exstadapi.mapper;

import co.istad.exstadapi.audit.AuditableMapper;
import co.istad.exstadapi.domain.User;
import co.istad.exstadapi.features.user.dto.UserRequest;
import co.istad.exstadapi.features.user.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AuditableMapper.class})
public interface UserMapper {
    @Mapping(target = "audit", source = "user")
    UserResponse fromUser(User user);

    User toUser(UserRequest userRequest);
}
