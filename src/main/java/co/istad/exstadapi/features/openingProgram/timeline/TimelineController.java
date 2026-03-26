package co.istad.exstadapi.features.openingProgram.timeline;

import co.istad.exstadapi.domain.vo.Timeline;
import co.istad.exstadapi.features.openingProgram.OpeningProgramService;
import co.istad.exstadapi.features.openingProgram.timeline.dto.TimelineSetUp;
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
public class TimelineController {
    private final OpeningProgramService openingProgramService;

    @PutMapping("/{uuid}/timelines")
    public ResponseEntity<?> setUpTimeline(@PathVariable String uuid, @RequestBody @Valid List<@Valid TimelineSetUp> timelineSetUps) {
        return new ResponseEntity<>(openingProgramService.setUpTimelines(uuid, timelineSetUps), HttpStatus.OK);
    }

    @GetMapping("/{uuid}/timelines")
    public ResponseEntity<?> getTimelines(@PathVariable String uuid) {
        return new ResponseEntity<>(openingProgramService.getTimelines(uuid), HttpStatus.OK);
    }
}
