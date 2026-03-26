package co.istad.exstadapi.features.university;

import co.istad.exstadapi.base.BasedMessage;
import co.istad.exstadapi.features.university.dto.UniversityRequest;
import co.istad.exstadapi.features.university.dto.UniversityUpdate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/universities")
@CrossOrigin(origins = "http://localhost:3000")
public class UniversityController {
    private final UniversityService universityService;

    @GetMapping
    public ResponseEntity<?> getUniversities() {
        return new ResponseEntity<>(
                Map.of("universities",universityService.getAllUniversities()),
                HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getUniversityByUuid(@PathVariable String uuid) {
        return new ResponseEntity<>(
                universityService.getUniversityByUuid(uuid), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUniversity(@RequestBody @Valid UniversityRequest universityRequest){
        return new ResponseEntity<>(
                universityService.createUniversity(universityRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/{uuid}")
    public BasedMessage deleteUniversityByUuid(@PathVariable String uuid) {
        return universityService.deleteUniversityByUuid(uuid);
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<?> updateUniversityByUuid(@PathVariable String uuid, @RequestBody @Valid UniversityUpdate universityUpdate) {
        return new ResponseEntity<>(
                universityService.updateUniversityByUuid(uuid, universityUpdate), HttpStatus.OK);
    }
}
