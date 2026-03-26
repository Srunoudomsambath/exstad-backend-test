package co.istad.exstadapi.features.user;

import co.istad.exstadapi.domain.User;
import co.istad.exstadapi.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUuid(String uuid);
    Optional<User> findByEmail(String email);
    List<User> findAllByIsDeletedFalse();

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUuid(String uuid);

    List<User> findAllByEnglishNameAndRole(String englishName, Role role);

    List<User> findAllByUsernameAndRole(String username, Role role);

    List<User> findAllByRoleNotIn(Set<Role> roles);

    Optional<User> findByUsernameAndRole(String username, Role role);
}
