package co.istad.exstadapi.features.auth;

import co.istad.exstadapi.features.auth.dto.LoginRequest;
import co.istad.exstadapi.features.auth.dto.RegisterRequest;
import co.istad.exstadapi.features.auth.dto.KeycloakUserResponse;
import co.istad.exstadapi.features.auth.dto.TokenResponse;

import java.util.Optional;

public interface AuthService {

    KeycloakUserResponse register(RegisterRequest registerRequest);
    boolean delete(String uuid);
    TokenResponse login(LoginRequest loginRequest);
    TokenResponse refreshToken(String refreshToken);
    void logout(String refreshToken);
    Optional<KeycloakUserResponse> findByUuid(String uuid);
    Optional<KeycloakUserResponse> findByEmail(String email);
    Optional<KeycloakUserResponse> findByUsername(String username);

}
