package co.istad.exstadapi.features.achievement;

import co.istad.exstadapi.base.BasedMessage;
import co.istad.exstadapi.domain.Achievement;
import co.istad.exstadapi.domain.OpeningProgram;
import co.istad.exstadapi.features.achievement.dto.AchievementRequest;
import co.istad.exstadapi.features.achievement.dto.AchievementRequestUpdate;
import co.istad.exstadapi.features.achievement.dto.AchievementResponse;
import co.istad.exstadapi.features.openingProgram.OpeningProgramRepository;
import co.istad.exstadapi.mapper.AchievementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository achievementRepository;
    private final AchievementMapper achievementMapper;
    private final OpeningProgramRepository openingProgramRepository;

    @Override
    public AchievementResponse createAchievement(AchievementRequest achievementRequest) {

        Achievement achievement = achievementMapper.toAchievement(achievementRequest);
        achievement.setUuid(UUID.randomUUID().toString());
        achievement.setIsDeleted(false);
        achievement = achievementRepository.save(achievement);

        return achievementMapper.fromAchievement(achievement);
    }

    @Override
    public AchievementResponse getAchievementByTitle(String title) {
        return achievementMapper.fromAchievement(
                achievementRepository.findByTitle(title).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No achievement found with title " + title)
                )
        );
    }

    @Override
    public AchievementResponse getAchievementByUuid(String uuid) {
        return achievementMapper.fromAchievement(
                achievementRepository.findByUuid(uuid).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No achievement found with uuid " + uuid)
                )
        );
    }

    @Override
    public List<AchievementResponse> getAllAchievements() {

        return achievementRepository.findAllByIsDeletedFalse().stream().map(
                achievementMapper::fromAchievement
        ).toList();
    }

    @Override
    public List<AchievementResponse> getAchievementByOpeningProgramUuid(String openingProgramUuid) {
        OpeningProgram openingProgram = openingProgramRepository.findByUuid(openingProgramUuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No opening program found with uuid " + openingProgramUuid)
        );

        return achievementRepository.findAllByOpeningProgram(openingProgram).stream().map(
                achievementMapper::fromAchievement
        ).toList();
    }

    @Override
    public AchievementResponse updateAchievementByUuid(String uuid, AchievementRequestUpdate achievementRequestUpdate) {
        Achievement achievement = achievementRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No achievement found with uuid " + uuid)
        );
        achievementMapper.toAchievementPartially(achievementRequestUpdate, achievement);
        achievement = achievementRepository.save(achievement);
        return achievementMapper.fromAchievement(achievement);
    }

    @Override
    public BasedMessage deleteAchievementByUuid(String uuid) {
        Achievement achievement = achievementRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No achievement found with uuid " + uuid)
        );
        achievement.setIsDeleted(true);
        achievementRepository.save(achievement);
        return new BasedMessage("Achievement with uuid " + uuid + " has been deleted");
    }
}
