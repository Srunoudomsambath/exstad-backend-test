package co.istad.exstadapi.features.program.highlight;

import co.istad.exstadapi.domain.vo.Highlight;
import co.istad.exstadapi.features.program.ProgramService;
import co.istad.exstadapi.features.program.dto.ProgramResponse;
import co.istad.exstadapi.features.program.highlight.dto.HighlightSetUp;
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
public class HighlightController {
    private final ProgramService programService;

    @PutMapping("/{uuid}/highlights")
    public ResponseEntity<ProgramResponse> updateHighlights(
            @PathVariable String uuid,
            @RequestBody @Valid List<@Valid HighlightSetUp> highlightSetUps
    ) {
        return ResponseEntity.ok(programService.setUpHighlights(uuid, highlightSetUps));
    }

    @GetMapping("/{uuid}/highlights")
    public ResponseEntity<List<Highlight>> getHighlights(@PathVariable String uuid) {
        return ResponseEntity.ok(programService.getHighlights(uuid));
    }
}
