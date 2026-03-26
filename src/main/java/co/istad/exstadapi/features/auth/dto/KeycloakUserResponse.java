package co.istad.exstadapi.features.auth.dto;

public record KeycloakUserResponse(
        String uuid,
        String username,
        String email,
        String englishName,
        String khmerName

) {
}
