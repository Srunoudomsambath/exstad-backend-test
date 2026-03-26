package co.istad.exstadapi.features.scholarAchievement;

import co.istad.exstadapi.domain.Achievement;
import co.istad.exstadapi.domain.Scholar;
import co.istad.exstadapi.domain.ScholarAchievement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScholarAchievementRepository extends JpaRepository<ScholarAchievement, Integer> {
    List<ScholarAchievement> findAllByScholar(Scholar scholar);

    Optional<ScholarAchievement> findByScholarAndAchievement(Scholar scholar, Achievement achievement);

    List<ScholarAchievement> findAllByScholarAndIsDeletedFalse(Scholar scholar);
    List<ScholarAchievement> findAllByAchievementAndIsDeletedFalse(Achievement achievement);
}
