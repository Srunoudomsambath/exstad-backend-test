package co.istad.exstadapi.features.openingProgram.requirement;

import co.istad.exstadapi.domain.vo.Requirement;
import co.istad.exstadapi.features.openingProgram.OpeningProgramService;
import co.istad.exstadapi.features.openingProgram.requirement.dto.OPRequirementSetUp;
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
public class OPRequirementController {
    private final OpeningProgramService openingProgramService;

    @PutMapping("/{uuid}/requirements")
    public ResponseEntity<?> setUpRequirements(@PathVariable String uuid, @RequestBody @Valid List<@Valid OPRequirementSetUp> opRequirementSetUps) {
        return new ResponseEntity<>(
                openingProgramService.setUpRequirements(uuid, opRequirementSetUps), HttpStatus.OK);
    }

    @GetMapping("/{uuid}/requirements")
    public ResponseEntity<?> getRequirements(@PathVariable String uuid) {
        return new ResponseEntity<>(
                openingProgramService.getRequirements(uuid), HttpStatus.OK);
    }
}
