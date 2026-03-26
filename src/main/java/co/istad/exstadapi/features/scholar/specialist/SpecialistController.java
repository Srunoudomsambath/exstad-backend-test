package co.istad.exstadapi.features.scholar.specialist;

import co.istad.exstadapi.features.scholar.ScholarService;
import co.istad.exstadapi.features.scholar.specialist.dto.SpecialistSetup;
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
@RequestMapping(path = "/api/v1/scholars")
public class SpecialistController {
    private final ScholarService scholarService;

    @PutMapping("/assign-specialists/{scholarUuid}")
    public ResponseEntity<?> setSpecialistToAlumniScholars(@PathVariable String scholarUuid, @RequestBody @Valid List<@Valid SpecialistSetup> specialistSetup) {
        return new ResponseEntity<>(scholarService.setUpSpecialist(scholarUuid, specialistSetup), HttpStatus.OK);
    }

    @GetMapping("/specialists/{scholarUuid}")
    public ResponseEntity<?> getSpecialistsByScholarUuid(@PathVariable String scholarUuid) {
        return new ResponseEntity<>(scholarService.getSpecialistSetups(scholarUuid), HttpStatus.OK);
    }
}
