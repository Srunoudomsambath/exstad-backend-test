package co.istad.exstadapi.features.program.learningOutcome;

import co.istad.exstadapi.domain.vo.LearningOutcome;
import co.istad.exstadapi.features.program.ProgramService;
import co.istad.exstadapi.features.program.dto.ProgramResponse;
import co.istad.exstadapi.features.program.learningOutcome.dto.LearningOutcomeSetUp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/programs")
public class LearningOutcomeController {
    private final ProgramService programService;

    @PutMapping("/{uuid}/learning-outcomes")
    public ResponseEntity<ProgramResponse> updateLearningOutcomes(
            @PathVariable String uuid,
            @RequestBody @Valid List<@Valid LearningOutcomeSetUp> learningOutcomeSetUps
    ) {
        return ResponseEntity.ok(programService.setUpLearningOutcomes(uuid, learningOutcomeSetUps));
    }

    @GetMapping("/{uuid}/learning-outcomes")
    public ResponseEntity<List<LearningOutcome>> getLearningOutcomes(@PathVariable String uuid) {
        return ResponseEntity.ok(programService.getLearningOutcomes(uuid));
    }
}
