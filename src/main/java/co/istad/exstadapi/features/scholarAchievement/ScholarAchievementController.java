package co.istad.exstadapi.features.scholarAchievement;

import co.istad.exstadapi.features.scholarAchievement.dto.ScholarAchievementRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ScholarAchievementController {

    private final ScholarAchievementService scholarAchievementService;

    @GetMapping("/scholars/{uuid}/achievements")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllScholarAchievements(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok(scholarAchievementService.getAllScholarAchievementsByUuid(uuid));
    }

    @GetMapping("/achievements/{uuid}/scholars")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllScholarsByAchievement(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok(scholarAchievementService.getAllScholarAchievementsByAchievementUuid(uuid));
    }

    @PostMapping("/scholars/{uuid}/achievements")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createScholarAchievement(@PathVariable String uuid, @RequestBody @Valid ScholarAchievementRequest scholarAchievementRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                scholarAchievementService.createScholarAchievement(uuid, scholarAchievementRequest)
        );
    }

    @DeleteMapping("/scholars/{scholarUuid}/achievements/{achievementUuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteScholarAchievement(@PathVariable String scholarUuid, @PathVariable String achievementUuid) {
        scholarAchievementService.removeScholarAchievement(scholarUuid, achievementUuid);
        return ResponseEntity.noContent().build();
    }

}
