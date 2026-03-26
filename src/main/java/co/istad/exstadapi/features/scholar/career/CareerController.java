package co.istad.exstadapi.features.scholar.career;

import co.istad.exstadapi.features.scholar.ScholarService;
import co.istad.exstadapi.features.scholar.career.dto.CareerSetup;
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
public class CareerController {
    private final ScholarService scholarService;

    @PutMapping("/assign-careers/{scholarUuid}")
    public ResponseEntity<?> setUpCareer(@PathVariable String scholarUuid, @Valid @RequestBody List<@Valid CareerSetup> careerSetups) {
        return new ResponseEntity<>(scholarService.setUpCareer(scholarUuid,careerSetups),HttpStatus.OK);
    }

    @GetMapping("/careers/{scholarUuid}")
    public ResponseEntity<?> getCareersByScholarUuid(@PathVariable String scholarUuid) {
        return new ResponseEntity<>(scholarService.getCareers(scholarUuid),HttpStatus.OK);
    }
}
