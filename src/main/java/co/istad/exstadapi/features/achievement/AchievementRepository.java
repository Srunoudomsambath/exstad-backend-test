package co.istad.exstadapi.features.achievement;

import co.istad.exstadapi.domain.Achievement;
import co.istad.exstadapi.domain.OpeningProgram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AchievementRepository extends JpaRepository<Achievement, Integer> {

    Optional<Achievement> findByTitle(String title);
    Optional<Achievement> findByUuid(String uuid);

    List<Achievement> findAllByOpeningProgram(OpeningProgram openingProgram);
    List<Achievement> findAllByIsDeletedFalse();
}
