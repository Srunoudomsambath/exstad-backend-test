
package co.istad.exstadapi.features.program.Technology;

import co.istad.exstadapi.domain.vo.ProgramOverview;
import co.istad.exstadapi.domain.vo.Technology;
import co.istad.exstadapi.features.program.ProgramService;
import co.istad.exstadapi.features.program.Technology.dto.TechnologySetup;
import co.istad.exstadapi.features.program.dto.ProgramResponse;
import co.istad.exstadapi.features.program.programOverview.dto.ProgramOverviewSetUp;
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
public class TechnologyController {
    private final ProgramService programService;

    @PutMapping("/{uuid}/technologies")
    public ResponseEntity<ProgramResponse> updateTechnologies(
            @PathVariable String uuid,
            @RequestBody @Valid List<@Valid TechnologySetup> technologySetups
    ) {
        return ResponseEntity.ok(programService.setUpTechnologies(uuid, technologySetups));
    }

    @GetMapping("/{uuid}/technologies")
    public ResponseEntity<List<Technology>> getTechnologies(@PathVariable String uuid) {
        return ResponseEntity.ok(programService.getTechnologies(uuid));
    }
}
