package co.istad.exstadapi.features.classes;

import co.istad.exstadapi.features.classes.dto.ClassRequest;
import co.istad.exstadapi.features.classes.dto.ClassUpdate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/classes")
public class ClassController {
    private final ClassService classService;

    @GetMapping
    public ResponseEntity<?> getAllClasses() {
        return new ResponseEntity<>(
                Map.of("classes",classService.getAllClasses()), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getClassByUuid(@PathVariable String uuid) {
        return new ResponseEntity<>(classService.getClassByUuid(uuid), HttpStatus.OK);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> getClassByCode(@PathVariable String code) {
        return new ResponseEntity<>(classService.getClassByClassCode(code), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getClassByName(@PathVariable String name) {
        return new ResponseEntity<>(classService.getClassByName(name), HttpStatus.OK);
    }

    @GetMapping("/opening-program/{openingProgramTitle}")
    public ResponseEntity<?> getClassesByOpeningProgramTitle(@PathVariable String openingProgramTitle) {
        return new ResponseEntity<>(
                Map.of("classes",classService.getClassByOpeningProgramTitle(openingProgramTitle)), HttpStatus.OK);
    }

    @GetMapping("/opening-program-uuid/{openingProgramUuid}")
    public ResponseEntity<?> getClassesByOpeningProgramUuid(@PathVariable String openingProgramUuid) {
        return new ResponseEntity<>(
                Map.of("classes",classService.getAllClassesByOpeningProgramUuid(openingProgramUuid)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createClass(@RequestBody @Valid ClassRequest classRequest) {
        return new ResponseEntity<>(classService.createClass(classRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> updateClass(@PathVariable String uuid, @RequestBody @Valid ClassUpdate classUpdate) {
        return new ResponseEntity<>(classService.updateClass(uuid, classUpdate), HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> deleteClass(@PathVariable String uuid) {
        return new ResponseEntity<>(classService.hardDeleteClass(uuid), HttpStatus.OK);
    }

    @PutMapping("/{uuid}/delete")
    public ResponseEntity<?> softDeleteClass(@PathVariable String uuid) {
        return new ResponseEntity<>(classService.softDeleteClass(uuid), HttpStatus.OK);
    }

    @PutMapping("/{uuid}/restore")
    public ResponseEntity<?> restoreClass(@PathVariable String uuid) {
        return new ResponseEntity<>(classService.restoreClass(uuid), HttpStatus.OK);
    }

    @PutMapping("/{uuid}/disable")
    public ResponseEntity<?> disableClass(@PathVariable String uuid) {
        return new ResponseEntity<>(classService.disableClass(uuid), HttpStatus.OK);
    }

    @PutMapping("/{uuid}/enable")
    public ResponseEntity<?> enableClass(@PathVariable String uuid) {
        return new ResponseEntity<>(classService.enableClass(uuid), HttpStatus.OK);
    }

    @PutMapping("/{uuid}/weekend")
    public ResponseEntity<?> setToWeekend(@PathVariable String uuid) {
        return new ResponseEntity<>(classService.setToWeekend(uuid), HttpStatus.OK);
    }

    @PutMapping("/{uuid}/weekday")
    public ResponseEntity<?> setToWeekday(@PathVariable String uuid) {
        return new ResponseEntity<>(classService.setToWeekday(uuid), HttpStatus.OK);
    }
}
