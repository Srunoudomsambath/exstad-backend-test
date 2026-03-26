package co.istad.exstadapi.features.scholarAchievement;

import co.istad.exstadapi.features.scholarAchievement.dto.ScholarAchievementRequest;
import co.istad.exstadapi.features.scholarAchievement.dto.ScholarAchievementResponseForAchievement;
import co.istad.exstadapi.features.scholarAchievement.dto.ScholarAchievementResponseForScholar;

import java.util.List;

public interface ScholarAchievementService {

    ScholarAchievementResponseForScholar createScholarAchievement(String scholarUuid, ScholarAchievementRequest achievementRequest);

    List<ScholarAchievementResponseForScholar> getAllScholarAchievementsByUuid(String scholarUuid);

    void removeScholarAchievement(String scholarUuid, String achievementUuid);

    List<ScholarAchievementResponseForAchievement> getAllScholarAchievementsByAchievementUuid(String achievementUuid);
}
