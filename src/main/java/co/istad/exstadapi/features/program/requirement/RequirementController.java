package co.istad.exstadapi.features.program.requirement;

import co.istad.exstadapi.domain.vo.Requirement;
import co.istad.exstadapi.features.program.ProgramService;
import co.istad.exstadapi.features.program.dto.ProgramResponse;
import co.istad.exstadapi.features.program.requirement.dto.RequirementSetUp;
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
public class RequirementController {
    private final ProgramService programService;

    @PutMapping("/{uuid}/requirements")
    public ResponseEntity<ProgramResponse> updateRequirements(
            @PathVariable String uuid,
            @RequestBody @Valid List<@Valid RequirementSetUp> requirementSetUps
    ) {
        return ResponseEntity.ok(programService.setUpRequirements(uuid, requirementSetUps));
    }

    @GetMapping("/{uuid}/requirements")
    public ResponseEntity<List<Requirement>> getRequirements(@PathVariable String uuid) {
        return ResponseEntity.ok(programService.getRequirements(uuid));
    }
}
