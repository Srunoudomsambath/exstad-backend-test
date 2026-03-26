package co.istad.exstadapi.features.auth;

import co.istad.exstadapi.enums.Role;
import co.istad.exstadapi.features.auth.dto.KeycloakUserResponse;
import co.istad.exstadapi.features.auth.dto.LoginRequest;
import co.istad.exstadapi.features.auth.dto.RegisterRequest;
import co.istad.exstadapi.features.auth.dto.TokenResponse;
import co.istad.exstadapi.mapper.AuthMapper;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final Keycloak keycloak;
    private final AuthMapper authMapper;
    private final WebClient webClient;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.server-url}")
    private String authServerUrl;

    @Value("${keycloak.frontend-client-id}")
    private String clientId;

    @Value("${keycloak.frontend-secret-id}")
    private String clientSecret;

    @Override
    public KeycloakUserResponse register(RegisterRequest registerRequest) {
        log.info("Register request: {}", registerRequest);
        if (!registerRequest.password().equals(registerRequest.cfPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }

        UserRepresentation user = getUserRepresentation(registerRequest);

        try(Response response = keycloak.realm(realm)
                .users()
                .create(user)){

            if(response.getStatus() == HttpStatus.CREATED.value()){
                UserRepresentation createdUser = keycloak.realm(realm)
                        .users()
                        .search(user.getUsername())
                        .stream()
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found after creation"));

                if(!assignRole(createdUser.getId(), registerRequest.role() )){
                    log.error("Cannot assign role {}", createdUser.getUsername());
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot assign role " + createdUser.getUsername());
                }
                return authMapper.toKeycloakUserResponse(createdUser);
            }
            throw new ResponseStatusException(HttpStatus.valueOf(response.getStatus()), "Failed to create user");
        }
    }

    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        try {
            return getTokenFromKeycloak(loginRequest);

        } catch (WebClientResponseException e) {
            log.error("Login failed: {}", e.getResponseBodyAsString());
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Login failed");
        }
    }

    private TokenResponse getTokenFromKeycloak(LoginRequest loginRequest) {
        String tokenUrl = String.format("%s/realms/%s/protocol/openid-connect/token",
                authServerUrl, realm);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("username", loginRequest.username());
        formData.add("password", loginRequest.password());
        formData.add("scope", "openid profile email");

        try {
            return webClient.post()
                    .uri(tokenUrl)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(TokenResponse.class)
                    .block();
        } catch (Exception e) {
            log.error("Exception during Keycloak call: ", e);
            throw e;
        }
    }

    @Override
    public TokenResponse refreshToken(String refreshToken) {
        try {
            String tokenUrl = String.format("%s/realms/%s/protocol/openid-connect/token",
                    authServerUrl, realm);

            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("grant_type", "refresh_token");
            formData.add("client_id", clientId);
            formData.add("client_secret", clientSecret);
            formData.add("refresh_token", refreshToken);

            TokenResponse tokenResponse = webClient.post()
                    .uri(tokenUrl)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(TokenResponse.class)
                    .block();

            return new TokenResponse(
                    tokenResponse.accessToken(),
                    tokenResponse.refreshToken(),
                    tokenResponse.tokenType(),
                    tokenResponse.expiresIn(),
                    tokenResponse.scope()
            );

        } catch (WebClientResponseException e) {
            log.error("Token refresh failed: {}", e.getResponseBodyAsString());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
        }
    }

    @Override
    public void logout(String refreshToken) {
        try {
            String logoutUrl = String.format("%s/realms/%s/protocol/openid_connect/logout",
                    authServerUrl, realm);

            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("client_id", clientId);
            formData.add("client_secret", clientSecret);
            formData.add("refresh_token", refreshToken);

            webClient.post()
                    .uri(logoutUrl)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .toBodilessEntity()
                    .block();

        } catch (WebClientResponseException e) {
            log.warn("Logout failed: {}", e.getResponseBodyAsString());
        }
    }

    @Override
    public boolean delete(String uuid) {
        try(Response response = keycloak.realm(realm).users().delete(uuid)){
            if(response.getStatus() == HttpStatus.NO_CONTENT.value()){
                return true;
            }
            throw new ResponseStatusException(HttpStatus.valueOf(response.getStatus()), response.getEntity().toString());
        }
    }

    public boolean assignRole(String uuid, Role role){
        UserResource user = keycloak.realm(realm).users().get(uuid);
        if(user == null){
            log.error("User not found: {}", uuid);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        RoleRepresentation roleRepresentation = keycloak.realm(realm).roles().get(role.name()).toRepresentation();
        if(roleRepresentation == null){
            log.error("Cannot find role: {}", role.name());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found");
        }
        user.roles().realmLevel().add(List.of(roleRepresentation));
        return true;
    }

    @Override
    public Optional<KeycloakUserResponse> findByUuid(String uuid) {
        UserRepresentation user = keycloak.realm(realm).users().get(uuid).toRepresentation();
        return Optional.of(authMapper.toKeycloakUserResponse(user));
    }

    @Override
    public Optional<KeycloakUserResponse> findByEmail(String email) {
        UserRepresentation user =  keycloak.realm(realm).users()
                .searchByEmail(email, true).stream().findFirst().orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found in keycloak")
                );
        return Optional.of(authMapper.toKeycloakUserResponse(user));
    }

    @Override
    public Optional<KeycloakUserResponse> findByUsername(String username) {
        UserRepresentation user =  keycloak.realm(realm).users()
                .search(username, true).stream().findFirst().orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found in keycloak")
                );
        return Optional.of(authMapper.toKeycloakUserResponse(user));
    }

    private static UserRepresentation getUserRepresentation(RegisterRequest registerRequest) {
        UserRepresentation user = new UserRepresentation();

        user.setUsername(registerRequest.username());
        user.setEmail(registerRequest.email());
        user.setFirstName(registerRequest.englishName());
        user.setLastName(registerRequest.khmerName());

        CredentialRepresentation cred = new CredentialRepresentation();
        cred.setType(CredentialRepresentation.PASSWORD);
        cred.setValue(registerRequest.password());

        user.setCredentials(List.of(cred));
        user.setEnabled(true);
        user.setEmailVerified(false);
        return user;
    }
}
