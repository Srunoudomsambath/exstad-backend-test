package co.istad.exstadapi.features.program.programOverview;

import co.istad.exstadapi.domain.vo.ProgramOverview;
import co.istad.exstadapi.features.program.ProgramService;
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
public class ProgramOverviewController {
    private final ProgramService programService;

    @PutMapping("/{uuid}/program-overviews")
    public ResponseEntity<ProgramResponse> updateProgramOverviews(
            @PathVariable String uuid,
            @RequestBody @Valid List<@Valid ProgramOverviewSetUp> programOverviewSetUps
    ) {
        return ResponseEntity.ok(programService.setUpProgramOverviews(uuid, programOverviewSetUps));
    }

    @GetMapping("/{uuid}/program-overviews")
    public ResponseEntity<List<ProgramOverview>> getProgramOverviews(@PathVariable String uuid) {
        return ResponseEntity.ok(programService.getProgramOverviews(uuid));
    }

}
