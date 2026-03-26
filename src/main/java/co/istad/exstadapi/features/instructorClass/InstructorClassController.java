package co.istad.exstadapi.features.instructorClass;

import co.istad.exstadapi.features.instructorClass.dto.InstructorClassRequest;
import co.istad.exstadapi.features.instructorClass.dto.InstructorClassResponse;
import co.istad.exstadapi.features.instructorClass.dto.InstructorClassUpdate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/instructor-classes")
public class InstructorClassController {
    private final InstructorClassService instructorClassService;

    @GetMapping
    public ResponseEntity<?> getAllInstructorClasses() {
        return new ResponseEntity<>(
                Map.of("instructors-classes",instructorClassService.getAllInstructorsClasses()), HttpStatus.OK);
    }

    @GetMapping("/instructors")
    public ResponseEntity<?> getAllInstructor() {
        return new ResponseEntity<>(
                Map.of("instructors",instructorClassService.getAllInstructors()), HttpStatus.OK);
    }

    @GetMapping("/by-class-uuid/{classUuid}")
    public ResponseEntity<?> getAllInstructorClassesByClassUuid(@PathVariable String classUuid) {
        return new ResponseEntity<>(
                Map.of("instructors-classes",instructorClassService.getAllInstructorsClassesByClassUuid(classUuid)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addInstructorIntoClass(@RequestBody @Valid InstructorClassRequest instructorClassRequest) {
        return new ResponseEntity<>(instructorClassService.addInstructorIntoClass(instructorClassRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getInstructorClassByUuid(@PathVariable String uuid) {
        return new ResponseEntity<>(instructorClassService.getInstructorClassByUuid(uuid), HttpStatus.OK);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> updateInstructorClassByUuid(@PathVariable String uuid, @RequestBody @Valid InstructorClassUpdate instructorClassUpdate) {
        return new ResponseEntity<>(instructorClassService.updateInstructorClassByUuid(uuid,instructorClassUpdate), HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}/delete")
    public ResponseEntity<?> softDeleteInstructorClassByUuid(@PathVariable String uuid) {
        return new ResponseEntity<>(instructorClassService.softDeleteInstructorClassByUuid(uuid), HttpStatus.OK);
    }

    @PutMapping("/{uuid}/restore")
    public ResponseEntity<?> restoreInstructorClassByUuid(@PathVariable String uuid) {
        return new ResponseEntity<>(instructorClassService.restoreInstructorClassByUuid(uuid), HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> hardDeleteInstructorClassByUuid(@PathVariable String uuid) {
        return new ResponseEntity<>(instructorClassService.hardDeleteInstructorClassByUuid(uuid), HttpStatus.OK);
    }

    @GetMapping("/instructors/{instructorUuid}/classes")
    public ResponseEntity<?> getAllClassesByOneInstructor(@PathVariable String instructorUuid) {
        return new ResponseEntity<>(instructorClassService.getAllClassesByOneInstructorUuid(instructorUuid), HttpStatus.OK);
    }

    @GetMapping("/classes/{classUuid}/instructors")
    public ResponseEntity<?> getAllInstructorsByOneClass(@PathVariable String classUuid) {
        return new ResponseEntity<>(instructorClassService.getAllInstructorsByOneClassUuid(classUuid), HttpStatus.OK);
    }
}
