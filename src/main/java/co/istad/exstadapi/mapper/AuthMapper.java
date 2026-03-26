package co.istad.exstadapi.mapper;

import co.istad.exstadapi.features.auth.dto.KeycloakUserResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    @Mapping(source = "firstName", target = "englishName")
    @Mapping(source = "lastName", target = "khmerName")
    @Mapping(source = "id", target = "uuid")
    KeycloakUserResponse toKeycloakUserResponse(UserRepresentation userRepresentation);
}
