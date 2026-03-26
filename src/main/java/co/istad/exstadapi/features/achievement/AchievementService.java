package co.istad.exstadapi.features.achievement;

import co.istad.exstadapi.base.BasedMessage;
import co.istad.exstadapi.features.achievement.dto.AchievementRequest;
import co.istad.exstadapi.features.achievement.dto.AchievementRequestUpdate;
import co.istad.exstadapi.features.achievement.dto.AchievementResponse;

import java.util.List;

public interface AchievementService {

    AchievementResponse createAchievement(AchievementRequest achievementRequest);

    AchievementResponse getAchievementByTitle(String title);
    AchievementResponse getAchievementByUuid(String uuid);
    List<AchievementResponse> getAllAchievements();
    List<AchievementResponse> getAchievementByOpeningProgramUuid(String openingProgramUuid);

    AchievementResponse updateAchievementByUuid(String uuid, AchievementRequestUpdate achievementRequestUpdate);
    BasedMessage deleteAchievementByUuid(String uuid);



}
