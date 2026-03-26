package co.istad.exstadapi.features.program.roadmap;

import co.istad.exstadapi.domain.vo.Roadmap;
import co.istad.exstadapi.features.program.ProgramService;
import co.istad.exstadapi.features.program.dto.ProgramResponse;
import co.istad.exstadapi.features.program.roadmap.dto.RoadmapSetUp;
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
public class RoadmapController {
    private final ProgramService programService;

    @PutMapping("/{uuid}/roadmaps")
    public ResponseEntity<ProgramResponse> updateRoadmaps(
            @PathVariable String uuid,
            @RequestBody @Valid List<@Valid RoadmapSetUp> roadmapSetUps
    ) {
        return ResponseEntity.ok(programService.setUpRoadmaps(uuid, roadmapSetUps));
    }

    @GetMapping("/{uuid}/roadmaps")
    public ResponseEntity<List<Roadmap>> getRoadmaps(@PathVariable String uuid) {
        return ResponseEntity.ok(programService.getRoadmaps(uuid));
    }
}
