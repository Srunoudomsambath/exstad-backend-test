package co.istad.exstadapi.features.program.curriculum;

import co.istad.exstadapi.domain.vo.Curriculum;
import co.istad.exstadapi.features.program.ProgramService;
import co.istad.exstadapi.features.program.curriculum.dto.CurriculumSetUp;
import co.istad.exstadapi.features.program.dto.ProgramResponse;
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
public class CurriculumController {
    private final ProgramService programService;

    @PutMapping("/{uuid}/curriculums")
    public ResponseEntity<ProgramResponse> updateCurricula(
            @PathVariable String uuid,
            @RequestBody @Valid List<@Valid CurriculumSetUp> curriculumSetUps
    ) {
        return ResponseEntity.ok(programService.setUpCurricula(uuid, curriculumSetUps));
    }

    @GetMapping("/{uuid}/curriculums")
    public ResponseEntity<List<Curriculum>> getCurricula(@PathVariable String uuid) {
        return ResponseEntity.ok(programService.getCurricula(uuid));
    }
}
