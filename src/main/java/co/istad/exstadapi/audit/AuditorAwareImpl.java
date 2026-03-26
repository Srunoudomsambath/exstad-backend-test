package co.istad.exstadapi.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorProvider")
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth instanceof AnonymousAuthenticationToken) {
            return Optional.of("SYSTEM");
        }

        if (auth instanceof JwtAuthenticationToken jwtToken) {
            String keycloakUuid = jwtToken.getToken().getClaimAsString("sub");
            return Optional.ofNullable(keycloakUuid != null ? keycloakUuid : auth.getName());
        }

        return Optional.of(auth.getName());
    }
}
