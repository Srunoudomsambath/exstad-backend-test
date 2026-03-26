package co.istad.exstadapi.features.achievement;

import co.istad.exstadapi.features.achievement.dto.AchievementRequest;
import co.istad.exstadapi.features.achievement.dto.AchievementRequestUpdate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/achievements")
public class AchievementController {

    private final AchievementService achievementService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAchievements() {
        return new ResponseEntity<>(
                Map.of("achievements",achievementService.getAllAchievements()), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAchievement(@PathVariable String uuid) {
        return ResponseEntity.ok(
                achievementService.getAchievementByUuid(uuid)
        );
    }

    @PostMapping
    public ResponseEntity<?> createAchievement(@RequestBody @Valid AchievementRequest achievementRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                achievementService.createAchievement(achievementRequest)
        );
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<?> updateAchievement(@RequestBody AchievementRequestUpdate achievementRequestUpdate, @PathVariable String uuid) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                achievementService.updateAchievementByUuid(uuid, achievementRequestUpdate)
        );
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteAchievement(@PathVariable String uuid) {
        achievementService.deleteAchievementByUuid(uuid);
        return ResponseEntity.noContent().build();
    }

}
