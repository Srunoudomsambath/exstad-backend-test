package co.istad.exstadapi.features.auth.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponse(
        @JsonProperty("accessToken")
        @JsonAlias("access_token")
        String accessToken,

        @JsonProperty("refreshToken")
        @JsonAlias("refresh_token")
        String refreshToken,

        @JsonProperty("tokenType")
        @JsonAlias("token_type")
        String tokenType,

        @JsonProperty("expiresIn")
        @JsonAlias("expires_in")
        long expiresIn,
        String scope
) {
}