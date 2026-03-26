package co.istad.exstadapi.features.scholarClass;

import co.istad.exstadapi.features.scholarClass.dto.ScholarClassRequest;
import co.istad.exstadapi.features.scholarClass.dto.ScholarClassUpdate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/scholar-classes")
public class ScholarClassController {
    private final ScholarClassService scholarClassService;

    @GetMapping
    public ResponseEntity<?> getAllScholarClasses() {
        return new ResponseEntity<>(
                Map.of("scholar-classes",scholarClassService.getAllScholarClasses()), HttpStatus.OK);
    }

    @GetMapping("/by-class-code/{classCode}")
    public ResponseEntity<?> getAllScholarClassesByClassCode(@PathVariable String classCode) {
        return new ResponseEntity<>(
                Map.of("scholar-classes",scholarClassService.getAllScholarClassesByClassCode(classCode)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createScholarClass(@Valid @RequestBody ScholarClassRequest scholarClassRequest) {
        return new ResponseEntity<>(scholarClassService.createScholarIntoClass(scholarClassRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getScholarClass(@PathVariable String uuid) {
        return new ResponseEntity<>(scholarClassService.getScholarClassByUuid(uuid), HttpStatus.OK);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?> updateScholarClass(@PathVariable String uuid, @Valid @RequestBody ScholarClassUpdate scholarClassUpdate) {
        return new ResponseEntity<>(scholarClassService.updateScholarClassByUuid(uuid, scholarClassUpdate), HttpStatus.OK);
    }

    @PutMapping("/{uuid}/delete")
    public ResponseEntity<?> deleteScholarClass(@PathVariable String uuid) {
        return new ResponseEntity<>(scholarClassService.softDeleteScholarClassByUuid(uuid), HttpStatus.OK);
    }

    @PutMapping("/{uuid}/restore")
    public ResponseEntity<?> restoreScholarClass(@PathVariable String uuid) {
        return new ResponseEntity<>(scholarClassService.restoreScholarClassByUuid(uuid), HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<?> hardDeleteScholarClass(@PathVariable String uuid) {
        return new ResponseEntity<>(scholarClassService.hardDeleteScholarClassByUuid(uuid), HttpStatus.OK);
    }

    @GetMapping("/scholars/{scholarUuid}/classes")
    public ResponseEntity<?> getAllClassesByOneScholar(@PathVariable String scholarUuid) {
        return new ResponseEntity<>(scholarClassService.getAllClassesByOneScholarUuid(scholarUuid), HttpStatus.OK);
    }

    @GetMapping("/classes/{classUuid}/scholars")
    public ResponseEntity<?> getAllScholarsByOneClass(@PathVariable String classUuid) {
        return new ResponseEntity<>(scholarClassService.getAllScholarsByOneClassUuid(classUuid), HttpStatus.OK);
    }

    @GetMapping("/classes/{classUuid}/scholar-classes")
    public ResponseEntity<?> getAllScholarsClassesByOneClass(@PathVariable String classUuid) {
        return new ResponseEntity<>(scholarClassService.getAllScholarsClassesByOneClassUuid(classUuid), HttpStatus.OK);
    }

    @PutMapping("/{uuid}/mark-as-paid")
    public ResponseEntity<?> markAsPaid(@PathVariable String uuid) {
        return new ResponseEntity<>(scholarClassService.markAsPaid(uuid), HttpStatus.OK);
    }

    @PutMapping("/{uuid}/mark-as-unpaid")
    public ResponseEntity<?> markAsUnpaid(@PathVariable String uuid) {
        return new ResponseEntity<>(scholarClassService.markAsUnpaid(uuid), HttpStatus.OK);
    }

    @PutMapping("/{uuid}/mark-as-reminded")
    public ResponseEntity<?> markAsReminded(@PathVariable String uuid) {
        return new ResponseEntity<>(scholarClassService.markAsReminded(uuid), HttpStatus.OK);
    }

    @PutMapping("/{uuid}/mark-as-unreminded")
    public ResponseEntity<?> markAsUnreminded(@PathVariable String uuid) {
        return new ResponseEntity<>(scholarClassService.markAsUnreminded(uuid), HttpStatus.OK);
    }
}
