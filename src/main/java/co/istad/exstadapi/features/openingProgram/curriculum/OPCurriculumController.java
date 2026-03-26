package co.istad.exstadapi.features.openingProgram.curriculum;

import co.istad.exstadapi.domain.vo.Curriculum;
import co.istad.exstadapi.features.openingProgram.OpeningProgramService;
import co.istad.exstadapi.features.openingProgram.curriculum.dto.OPCurriculumSetUp;
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
public class OPCurriculumController {
    private final OpeningProgramService openingProgramService;

    @PutMapping("/{uuid}/curriculums")
    public ResponseEntity<?> setUpCurriculums(@PathVariable String uuid, @RequestBody @Valid List<@Valid OPCurriculumSetUp> opCurriculumSetUps) {
        return new ResponseEntity<>(openingProgramService.setUpCurricula(uuid, opCurriculumSetUps), HttpStatus.OK);
    }

    @GetMapping("/{uuid}/curriculums")
    public ResponseEntity<?> getCurriculums(@PathVariable String uuid) {
        return new ResponseEntity<>(openingProgramService.getCurricula(uuid), HttpStatus.OK);
    }
}
