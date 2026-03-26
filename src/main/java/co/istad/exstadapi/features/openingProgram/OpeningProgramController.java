package co.istad.exstadapi.features.openingProgram;

import co.istad.exstadapi.features.openingProgram.dto.OpeningProgramRequest;
import co.istad.exstadapi.features.openingProgram.dto.OpeningProgramUpdate;
import co.istad.exstadapi.features.openingProgram.dto.SetUpTemplate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/opening-programs")
public class OpeningProgramController {
    private final OpeningProgramService openingProgramService;

    @GetMapping
    public ResponseEntity<?> getAllOpeningPrograms() {
        return new ResponseEntity<>(Map.of(
                "opening-programs",openingProgramService.getAllOpeningPrograms()), HttpStatus.OK);
    }

    @GetMapping("/program/{slug}")
    public ResponseEntity<?> getAllOpeningProgramsByProgramSlug(@PathVariable String slug) {
        return new ResponseEntity<>(Map.of(
                "opening-programs",openingProgramService.getAllOpeningProgramsByProgramSlug(slug)), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getOpeningProgramByUuid(@PathVariable String uuid) {
        return new ResponseEntity<>(openingProgramService.getOpeningProgramByUuid(uuid), HttpStatus.OK);
    }

    @GetMapping("/scholar/{uuid}")
    public ResponseEntity<?> getAllOpeningProgramByScholarUuid(@PathVariable String uuid) {
        return new ResponseEntity<>(openingProgramService.getAllOpeningProgramByScholarUuid(uuid), HttpStatus.OK);
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<?> getOpeningProgramBySlug(@PathVariable String slug) {
        return new ResponseEntity<>(openingProgramService.getOpeningProgramBySlug(slug), HttpStatus.OK);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<?> getOpeningProgramByTitle(@PathVariable String title) {
        return new ResponseEntity<>(openingProgramService.getOpeningProgramByTitle(title), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createOpeningProgram(@Valid @RequestBody OpeningProgramRequest openingProgramRequest){
        return new ResponseEntity<>(
                openingProgramService.createOpeningProgram(openingProgramRequest),HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> updateOpeningProgram(@PathVariable String uuid,@Valid @RequestBody OpeningProgramUpdate openingProgramUpdate){
        return new ResponseEntity<>(
                openingProgramService.updateOpeningProgram(uuid,openingProgramUpdate),HttpStatus.OK);
    }

    @PutMapping("/{uuid}/soft-delete")
    public ResponseEntity<?> softDeleteOpeningProgram(@PathVariable String uuid){
        return new ResponseEntity<>(
                openingProgramService.deleteSoftOpeningProgram(uuid),HttpStatus.OK);
    }

    @PutMapping("/{uuid}/restore")
    public ResponseEntity<?> restoreOpeningProgram(@PathVariable String uuid){
        return new ResponseEntity<>(
                openingProgramService.restoreOpeningProgram(uuid),HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteOpeningProgram(@PathVariable String uuid){
        return new ResponseEntity<>(
                openingProgramService.deleteHardOpeningProgram(uuid),HttpStatus.OK);
    }

    @PutMapping("/{uuid}/activate")
    public ResponseEntity<?> activateOpeningProgram(@PathVariable String uuid){
        return new ResponseEntity<>(
                openingProgramService.activateOpeningProgram(uuid),HttpStatus.OK);
    }

    @PutMapping("/{uuid}/deactivate")
    public ResponseEntity<?> deactivateOpeningProgram(@PathVariable String uuid){
        return new ResponseEntity<>(
                openingProgramService.deactivateOpeningProgram(uuid),HttpStatus.OK);
    }

    @PutMapping("/{uuid}/template")
    public ResponseEntity<?> setUpTemplate(@PathVariable String uuid, @RequestBody @Valid SetUpTemplate setUpTemplate){
        return new ResponseEntity<>(
                openingProgramService.setUpTemplate(uuid, setUpTemplate),HttpStatus.OK);
    }
}
