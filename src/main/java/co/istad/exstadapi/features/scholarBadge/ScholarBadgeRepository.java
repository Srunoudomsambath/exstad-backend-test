package co.istad.exstadapi.features.scholarBadge;

import co.istad.exstadapi.domain.ScholarBadge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScholarBadgeRepository extends JpaRepository<ScholarBadge, Integer> {


    Optional<ScholarBadge> findByUuid(String uuid);
}
