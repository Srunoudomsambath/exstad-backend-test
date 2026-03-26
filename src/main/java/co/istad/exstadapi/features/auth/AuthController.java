package co.istad.exstadapi.features.auth;

import co.istad.exstadapi.features.auth.dto.LoginRequest;
import co.istad.exstadapi.features.auth.dto.RefreshTokenRequest;
import co.istad.exstadapi.features.auth.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("/register")
//    public KeycloakUserResponse register(@RequestBody RegisterRequest registerRequest) {
//        return authService.register(registerRequest);
//    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/refresh-token")
    public TokenResponse refreshToken(@RequestBody RefreshTokenRequest refreshToken) {
        return authService.refreshToken(refreshToken.refreshToken());
    }
}
