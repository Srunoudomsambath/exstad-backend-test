package co.istad.exstadapi.features.program;

import co.istad.exstadapi.features.program.dto.ProgramRequest;
import co.istad.exstadapi.features.program.dto.ProgramUpdate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/programs")
public class ProgramController {
    private final ProgramService programService;

    @GetMapping
    public ResponseEntity<?> getAllPrograms() {
        return new ResponseEntity<>(
                Map.of("programs",programService.getAllPrograms()),
                HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getProgramByUuid(@PathVariable String uuid) {
        return new ResponseEntity<>(
                programService.getProgramByUuid(uuid), HttpStatus.OK);
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<?> getProgramBySlug(@PathVariable String slug) {
        return new ResponseEntity<>(
                programService.getProgramBySlug(slug), HttpStatus.OK);
    }

    @GetMapping("/opening-program/{openingProgramUuid}")
    public ResponseEntity<?> getProgramByOpeningProgramUuid(@PathVariable String openingProgramUuid){
        return new ResponseEntity<>(
                programService.getProgramByOpeningProgramUuid(openingProgramUuid), HttpStatus.OK);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<?> getProgramByTitle(@PathVariable String title) {
        return new ResponseEntity<>(
                programService.getProgramByTitle(title), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createProgram(@RequestBody @Valid ProgramRequest programRequest){
        return new ResponseEntity<>(
                programService.createProgram(programRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> updateProgram(@PathVariable String uuid, @RequestBody @Valid ProgramUpdate programUpdate){
        return new ResponseEntity<>(
                programService.updateProgram(uuid, programUpdate), HttpStatus.OK);
    }

    @PutMapping("/{uuid}/delete")
    public ResponseEntity<?> deleteProgram(@PathVariable String uuid) {
        return new ResponseEntity<>(
                programService.deleteProgram(uuid), HttpStatus.OK);
    }

    @PutMapping("/{uuid}/restore")
    public ResponseEntity<?> restoreProgram(@PathVariable String uuid) {
        return new ResponseEntity<>(
                programService.restoreProgram(uuid), HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> hardDeleteProgram(@PathVariable String uuid) {
        return new ResponseEntity<>(
                programService.hardDeleteProgram(uuid), HttpStatus.OK);
    }

//    @GetMapping("/opening-program/{openingProgramUuid}")
//    public ResponseEntity<?> getProgramByOpeningProgramUuid(@PathVariable String openingProgramUuid){
//        return new ResponseEntity<>(
//                programService.getProgramByOpeningProgramUuid(openingProgramUuid), HttpStatus.OK);
//    }
}
