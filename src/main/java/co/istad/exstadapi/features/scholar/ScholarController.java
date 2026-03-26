package co.istad.exstadapi.features.scholar;

import co.istad.exstadapi.base.BasedMessage;
import co.istad.exstadapi.enums.ScholarStatus;
import co.istad.exstadapi.features.scholar.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/scholars")
public class ScholarController {

    private final ScholarService scholarService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllScholars() {
        return new ResponseEntity<>(
                Map.of("scholars", scholarService.findAllScholars()), HttpStatus.OK);
    }

    @GetMapping("/abroad")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllAbroadScholars() {
        return new ResponseEntity<>(
                Map.of("scholars", scholarService.getAllAbroadScholars()), HttpStatus.OK);
    }

    @GetMapping("/status/{scholarStatus}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllScholarsByStatus(@PathVariable("scholarStatus") String scholarStatus) {
        return new ResponseEntity<>(
                Map.of("scholars", scholarService.getAllScholarsByStatus(ScholarStatus.valueOf(scholarStatus.trim().toUpperCase()))), HttpStatus.OK
        );
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public ScholarResponse getScholarByUuid(@PathVariable String uuid) {
        return scholarService.findByUuid(uuid);
    }

    @GetMapping("/username/{username}")
    @ResponseStatus(HttpStatus.OK)
    public ScholarResponse getScholarByUsername(@PathVariable String username) {
        return scholarService.findByUsername(username);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ScholarResponse> getScholarsByName(@RequestParam(defaultValue = "") String username, @RequestParam(defaultValue = "") String name) {
        if (!(name == null || name.isEmpty())) {
            return scholarService.searchByEnglishName(name);
        }else if(!(username == null || username.isEmpty())){
            return scholarService.searchByUsername(username);
        }
        return List.of();
    }

    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getScholarCount() {
        return new ResponseEntity<>(Map.of("scholars", scholarService.countScholars()), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ScholarResponse createScholar(@RequestBody @Valid ScholarRequest scholarRequest) {
        return scholarService.createScholar(scholarRequest);
    }

    @PostMapping("/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ScholarResponse> createMultipleScholars(@RequestBody @Valid List<ScholarRequest> scholarRequests) {
        return scholarService.createMultipleScholars(scholarRequests);
    }

    @PatchMapping("/{uuid}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ScholarResponse updateScholar(@PathVariable String uuid, @RequestBody @Valid ScholarRequestUpdate scholarRequestUpdate){
        return scholarService.updateScholar(uuid, scholarRequestUpdate);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ScholarResponse getMe() {
        return scholarService.getCurrentScholar();
    }

    @PatchMapping("/me")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ScholarResponse updateMe(@RequestBody @Valid ScholarRequestUpdate scholarRequestUpdate) {
        return scholarService.updateCurrentScholar(scholarRequestUpdate);
    }


    @GetMapping("/{uuid}/social-links")
    @ResponseStatus(HttpStatus.OK)
    public List<SocialLinkResponse> getScholarSocialLinks(@PathVariable String uuid) {
        return scholarService.getScholarSocialLink(uuid);
    }

    @PostMapping("/{uuid}/social-links")
    @ResponseStatus(HttpStatus.CREATED)
    public SocialLinkResponse addScholarSocialLink(@PathVariable String uuid, @RequestBody @Valid SocialLinkRequest socialLinkRequest) {
        return scholarService.setUpScholarSocialLink(uuid, socialLinkRequest);
    }

    @PatchMapping("/{scholarUuid}/social-link/{socialLinkUuid}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SocialLinkResponse updateSocialLinkStatus(@PathVariable String scholarUuid, @PathVariable String socialLinkUuid, @RequestBody Boolean status) {
        return scholarService.updateSocialLinkStatus(scholarUuid, socialLinkUuid, status);
    }

    @DeleteMapping("/{scholarUuid}/social-link/{socialLinkUuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public BasedMessage deleteSocialLink(@PathVariable String scholarUuid, @PathVariable String socialLinkUuid) {
        scholarService.deleteSocialLink(scholarUuid, socialLinkUuid);
        return new BasedMessage("Scholar deleted successfully");
    }
    
    @PutMapping("/{uuid}/soft-delete")
    @ResponseStatus(HttpStatus.OK)
    public BasedMessage softDeleteScholar(@PathVariable String uuid) {
        return scholarService.softDeleteScholarByUuid(uuid);
    }

    @PutMapping("/{uuid}/restore")
    @ResponseStatus(HttpStatus.OK)
    public BasedMessage restoreScholar(@PathVariable String uuid) {
        return scholarService.restoreScholarByUuid(uuid);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public BasedMessage hardDeleteScholar(@PathVariable String uuid) {
        return scholarService.hardDeleteScholarByUuid(uuid);
    }

    @GetMapping("/{uuid}/opening-program")
    public ResponseEntity<?> getAllScholarsByOpeningProgramUuid(@PathVariable String uuid) {
        return new ResponseEntity<>(
                Map.of("opening-program-scholars", scholarService.getAllScholarsByOpeningProgramUuid(uuid)), HttpStatus.OK);
    }

    @PutMapping("/{uuid}/is-employed")
    public ResponseEntity<?> isEmployedScholar(@PathVariable String uuid) {
        return new ResponseEntity<>(
                scholarService.markIsEmployed(uuid), HttpStatus.OK);
    }

    @PutMapping("/{uuid}/is-unemployed")
    public ResponseEntity<?> isUnemployedScholar(@PathVariable String uuid) {
        return new ResponseEntity<>(
                scholarService.unmarkIsEmployed(uuid), HttpStatus.OK);
    }

    @PutMapping("/{uuid}/is-abroad")
    public ResponseEntity<?> isAbroadScholar(@PathVariable String uuid) {
        return new ResponseEntity<>(
                scholarService.markIsAbroad(uuid), HttpStatus.OK);
    }

    @PutMapping("/{uuid}/is-not-abroad")
    public ResponseEntity<?> isNotAbroadScholar(@PathVariable String uuid) {
        return new ResponseEntity<>(
                scholarService.unmarkIsAbroad(uuid), HttpStatus.OK);
    }

    @GetMapping("/class-room/{classRoomName}")
    public ResponseEntity<?> getAllScholarsByClassRoomName(@PathVariable String classRoomName) {
        return new ResponseEntity<>(
                scholarService.getAllScholarsByClassRoomName(classRoomName), HttpStatus.OK);
    }

    @GetMapping("/program/{programUuid}")
    public ResponseEntity<?> getAllScholarsByProgramUuid(@PathVariable String programUuid) {
        return new ResponseEntity<>(
                scholarService.getAllScholarsByProgramUuid(programUuid), HttpStatus.OK);
    }

    @PutMapping("/{scholarUuid}/completed-course/{openingProgramUuid}")
    public ResponseEntity<?> markCompletedCourse(@PathVariable String scholarUuid, @PathVariable String openingProgramUuid) {
        return new ResponseEntity<>(
                scholarService.markCompletedCourse(scholarUuid, openingProgramUuid), HttpStatus.OK);
    }

    @PutMapping("/{scholarUuid}/remove-completed-course/{openingProgramUuid}")
    public ResponseEntity<?> removeCompletedCourse(@PathVariable String scholarUuid, @PathVariable String openingProgramUuid) {
        return new ResponseEntity<>(
                scholarService.unmarkCompletedCourse(scholarUuid, openingProgramUuid), HttpStatus.OK);
    }

    @GetMapping("/{scholarUuid}/completed-courses")
    public ResponseEntity<?> getAllCompletedCoursesByScholarUuid(@PathVariable String scholarUuid) {
        return new ResponseEntity<>(
                Map.of("completed-courses", scholarService.getAllCompletedCoursesByScholarUuid(scholarUuid)), HttpStatus.OK);
    }
}

