package co.istad.exstadapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
//
//@Configuration
//public class KeycloakSecurity {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        // ✅ CORS must be configured FIRST before any auth rules
//        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
//
//        // ✅ Disable CSRF (stateless JWT - not needed)
//        http.csrf(AbstractHttpConfigurer::disable);
//
//        // ✅ Disable form login
//        http.formLogin(AbstractHttpConfigurer::disable);
//
//        // ✅ Stateless session
//        http.sessionManagement(session ->
//                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        );
//
//        // ✅ Authorization rules
//        http.authorizeHttpRequests(endpoint -> endpoint
////
////                // ✅ CRITICAL: Allow ALL OPTIONS preflight requests first
////                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
////
////                // Swagger / OpenAPI
////                .requestMatchers("/swagger-ui.html").permitAll()
////                .requestMatchers("/swagger-ui/**").permitAll()
////                .requestMatchers("/v3/api-docs/**").permitAll()
////
////                // Documents / file uploads
////                .requestMatchers("/api/v1/documents/**").permitAll()
////                .requestMatchers("/documents/**").permitAll()
////
////                // Bakong
////                .requestMatchers("/api/bakong/**").permitAll()
////                .requestMatchers("/api/v1/bakong/**").permitAll()
////
////                // Provinces - public read
////                .requestMatchers(HttpMethod.GET, "/api/v1/provinces/**").permitAll()
////
////                // Universities
////                .requestMatchers(HttpMethod.GET, "/api/v1/universities/**").permitAll()
////                .requestMatchers(HttpMethod.POST, "/api/v1/universities/**").hasAnyRole("ADMIN", "INSTRUCTOR1", "INSTRUCTOR2")
////                .requestMatchers(HttpMethod.PATCH, "/api/v1/universities/**").hasAnyRole("ADMIN", "INSTRUCTOR1")
////                .requestMatchers(HttpMethod.DELETE, "/api/v1/universities/**").hasRole("ADMIN")
////
////                // Programs
////                .requestMatchers(HttpMethod.GET, "/api/v1/programs/**").permitAll()
////                .requestMatchers(HttpMethod.POST, "/api/v1/programs/**").hasAnyRole("ADMIN", "INSTRUCTOR1", "INSTRUCTOR2")
////                .requestMatchers(HttpMethod.PUT, "/api/v1/programs/**").hasAnyRole("ADMIN", "INSTRUCTOR1")
////                .requestMatchers(HttpMethod.DELETE, "/api/v1/programs/**").hasRole("ADMIN")
////
////                // Badges
////                .requestMatchers(HttpMethod.GET, "/api/v1/badges/**").permitAll()
////                .requestMatchers(HttpMethod.POST, "/api/v1/badges/**").hasAnyRole("ADMIN", "INSTRUCTOR1", "INSTRUCTOR2")
////                .requestMatchers(HttpMethod.PUT, "/api/v1/badges/**").hasAnyRole("ADMIN", "INSTRUCTOR1")
////                .requestMatchers(HttpMethod.DELETE, "/api/v1/badges/**").hasRole("ADMIN")
////
////                // Current Addresses
////                .requestMatchers(HttpMethod.GET, "/api/v1/current-addresses/**").permitAll()
////                .requestMatchers(HttpMethod.POST, "/api/v1/current-addresses/**").hasAnyRole("ADMIN", "INSTRUCTOR1", "INSTRUCTOR2")
////                .requestMatchers(HttpMethod.PUT, "/api/v1/current-addresses/**").hasAnyRole("ADMIN", "INSTRUCTOR1")
////                .requestMatchers(HttpMethod.DELETE, "/api/v1/current-addresses/**").hasRole("ADMIN")
////
////                // Scholars
////                .requestMatchers(HttpMethod.GET, "/api/v1/scholars/**").permitAll()
////                .requestMatchers(HttpMethod.POST, "/api/v1/scholars/**").hasAnyRole("ADMIN", "INSTRUCTOR1", "INSTRUCTOR2")
////                .requestMatchers(HttpMethod.PUT, "/api/v1/scholars/**").hasAnyRole("ADMIN", "INSTRUCTOR1")
////                .requestMatchers(HttpMethod.PATCH, "/api/v1/scholars/**").hasAnyRole("ADMIN", "INSTRUCTOR1")
////                .requestMatchers(HttpMethod.DELETE, "/api/v1/scholars/**").hasRole("ADMIN")
////
////                // Opening Programs
////                .requestMatchers(HttpMethod.GET, "/api/v1/opening-programs/**").permitAll()
////                .requestMatchers(HttpMethod.POST, "/api/v1/opening-programs/**").hasAnyRole("ADMIN", "INSTRUCTOR1", "INSTRUCTOR2")
////                .requestMatchers(HttpMethod.PUT, "/api/v1/opening-programs/**").hasAnyRole("ADMIN", "INSTRUCTOR1")
////                .requestMatchers(HttpMethod.DELETE, "/api/v1/opening-programs/**").hasRole("ADMIN")
////
////                // Classes
////                .requestMatchers(HttpMethod.GET, "/api/v1/classes/**").permitAll()
////                .requestMatchers(HttpMethod.POST, "/api/v1/classes/**").hasAnyRole("ADMIN", "INSTRUCTOR1", "INSTRUCTOR2")
////                .requestMatchers(HttpMethod.PUT, "/api/v1/classes/**").hasAnyRole("ADMIN", "INSTRUCTOR1")
////                .requestMatchers(HttpMethod.DELETE, "/api/v1/classes/**").hasRole("ADMIN")
////
////                // Enrollments - fully public for now
////                .requestMatchers("/api/v1/enrollments/**").permitAll()
////
////                // Achievements
////                .requestMatchers(HttpMethod.GET, "/api/v1/achievements/**").permitAll()
////                .requestMatchers(HttpMethod.POST, "/api/v1/achievements/**").hasAnyRole("ADMIN", "INSTRUCTOR1", "INSTRUCTOR2")
////                .requestMatchers(HttpMethod.PATCH, "/api/v1/achievements/**").hasAnyRole("ADMIN", "INSTRUCTOR1")
////                .requestMatchers(HttpMethod.DELETE, "/api/v1/achievements/**").hasRole("ADMIN")
////
////                // Scholar Achievements
////                .requestMatchers(HttpMethod.GET, "/api/v1/scholars-achievements/**").permitAll()
////                .requestMatchers(HttpMethod.POST, "/api/v1/scholars-achievements/**").hasAnyRole("ADMIN", "INSTRUCTOR1", "INSTRUCTOR2")
////                .requestMatchers(HttpMethod.DELETE, "/api/v1/scholars-achievements/**").hasRole("ADMIN")
////
////                // Scholar Classes
////                .requestMatchers(HttpMethod.GET, "/api/v1/scholars-classes/**").permitAll()
////                .requestMatchers(HttpMethod.POST, "/api/v1/scholars-classes/**").hasAnyRole("ADMIN", "INSTRUCTOR1", "INSTRUCTOR2")
////                .requestMatchers(HttpMethod.PUT, "/api/v1/scholars-classes/**").hasAnyRole("ADMIN", "INSTRUCTOR1")
////                .requestMatchers(HttpMethod.DELETE, "/api/v1/scholars-classes/**").hasRole("ADMIN")
////
////                // Certificates
////                .requestMatchers(HttpMethod.GET, "/api/v1/certificates/**").permitAll()
////                .requestMatchers(HttpMethod.POST, "/api/v1/certificates/**").hasAnyRole("ADMIN", "INSTRUCTOR1", "INSTRUCTOR2")
////                .requestMatchers(HttpMethod.PUT, "/api/v1/certificates/**").hasAnyRole("ADMIN", "INSTRUCTOR1")
////
////                // Instructor Classes
////                .requestMatchers(HttpMethod.GET, "/api/v1/instructors-classes/**").permitAll()
////                .requestMatchers(HttpMethod.POST, "/api/v1/instructors-classes/**").hasAnyRole("ADMIN", "INSTRUCTOR1", "INSTRUCTOR2")
////                .requestMatchers(HttpMethod.PUT, "/api/v1/instructors-classes/**").hasAnyRole("ADMIN", "INSTRUCTOR1")
////                .requestMatchers(HttpMethod.DELETE, "/api/v1/instructors-classes/**").hasRole("ADMIN")
////
////                // Users
////                .requestMatchers("/api/v1/users/login").permitAll()
////                .requestMatchers(HttpMethod.POST, "/api/v1/users/**").hasAnyRole("ADMIN", "INSTRUCTOR1", "INSTRUCTOR2")
////                .requestMatchers(HttpMethod.GET, "/api/v1/users/**").hasAnyRole("ADMIN", "INSTRUCTOR1", "INSTRUCTOR2")
//
//                // Everything else requires authentication
//                .anyRequest().permitAll()
//        );
//
//        // ✅ JWT resource server
//        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
//
//        return http.build();
//    }
//
//    @Bean
//    public JwtAuthenticationConverter jwtAuthenticationConverter() {
//        Converter<Jwt, Collection<GrantedAuthority>> jwtCollectionConverter = jwt -> {
//            Map<String, Collection<String>> realmAccess = jwt.getClaim("realm_access");
//            if (realmAccess == null || !realmAccess.containsKey("roles")) {
//                return List.of(); // ✅ safe null check
//            }
//            Collection<String> roles = realmAccess.get("roles");
//            return roles.stream()
//                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
//                    .collect(Collectors.toList());
//        };
//
//        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
//        converter.setJwtGrantedAuthoritiesConverter(jwtCollectionConverter);
//        return converter;
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//
//        // ✅ Allowed origins
//        configuration.setAllowedOrigins(List.of(
//                "http://localhost:3000",
//                "http://localhost:3001",
//                "http://localhost:5173",
//                "https://admin.exstad.tech",
//                "https://www.exstad.tech",
//                "https://exstad-admin-front-end.vercel.app"
//
//        ));
//
//        // ✅ Allowed methods including OPTIONS
//        configuration.setAllowedMethods(List.of(
//                "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
//        ));
//
//        // ✅ Explicit headers — wildcard "*" breaks when allowCredentials=true
//        configuration.setAllowedHeaders(List.of(
//                "Authorization",
//                "Content-Type",
//                "X-Requested-With",
//                "Accept",
//                "Origin",
//                "Access-Control-Request-Method",
//                "Access-Control-Request-Headers"
//        ));
//
//        // ✅ Expose these headers to the frontend
//        configuration.setExposedHeaders(List.of(
//                "Authorization",
//                "Content-Type"
//        ));
//
//        // ✅ Allow credentials (cookies, auth headers)
//        configuration.setAllowCredentials(true);
//
//        // ✅ Cache preflight for 1 hour — reduces OPTIONS spam
//        configuration.setMaxAge(3600L);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//}

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // allow all requests, no auth needed
                );
        return http.build();
    }
}