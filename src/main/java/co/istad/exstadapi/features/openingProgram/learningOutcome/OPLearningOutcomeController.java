package co.istad.exstadapi.features.openingProgram.learningOutcome;

import co.istad.exstadapi.domain.vo.LearningOutcome;
import co.istad.exstadapi.features.openingProgram.OpeningProgramService;
import co.istad.exstadapi.features.openingProgram.learningOutcome.dto.OPLearningOutcomeSetUp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/opening-programs")
public class OPLearningOutcomeController {
    private final OpeningProgramService openingProgramService;

    @PutMapping("/{uuid}/learning-outcomes")
    public ResponseEntity<?> setUpLearningOutcomes(@PathVariable String uuid, @RequestBody @Valid List<@Valid OPLearningOutcomeSetUp> opLearningOutcomeSetUps) {
        return new ResponseEntity<>(
                openingProgramService.setUpLearningOutcomes(uuid, opLearningOutcomeSetUps), HttpStatus.OK);
    }

    @GetMapping("/{uuid}/learning-outcomes")
    public ResponseEntity<?> getLearningOutcomes(@PathVariable String uuid) {
        return new ResponseEntity<>(
                openingProgramService.getLearningOutcomes(uuid), HttpStatus.OK);
    }
}
