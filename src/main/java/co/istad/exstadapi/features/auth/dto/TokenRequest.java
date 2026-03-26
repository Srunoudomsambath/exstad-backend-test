package co.istad.exstadapi.features.auth.dto;

public record TokenRequest(
        String grantType,
        String clientId,
        String clientSecret,
        String username,
        String password,
        String scope
) {
    public TokenRequest(String clientId, String clientSecret, String username, String password) {
        this("password", clientId, clientSecret, username, password, "openid profile email");
    }
}