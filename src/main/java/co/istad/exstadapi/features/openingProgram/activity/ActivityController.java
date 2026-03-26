package co.istad.exstadapi.features.openingProgram.activity;

import co.istad.exstadapi.domain.vo.Activity;
import co.istad.exstadapi.features.openingProgram.OpeningProgramService;
import co.istad.exstadapi.features.openingProgram.activity.dto.ActivitySetUp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/opening-programs")
public class ActivityController {
    private final OpeningProgramService openingProgramService;

    @PutMapping("/{uuid}/activities")
    public ResponseEntity<?> setUpActivity(@PathVariable String uuid, @RequestBody @Valid List<@Valid ActivitySetUp> activitySetUps) {
        return ResponseEntity.ok(openingProgramService.setUpActivities(uuid, activitySetUps));
    }

    @GetMapping("/{uuid}/activities")
    public ResponseEntity<?> getActivities(@PathVariable String uuid) {
        return ResponseEntity.ok(openingProgramService.getActivities(uuid));
    }
}
