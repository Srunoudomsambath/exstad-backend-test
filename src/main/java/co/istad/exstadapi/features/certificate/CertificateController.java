package co.istad.exstadapi.features.certificate;

import co.istad.exstadapi.base.BasedMessage;
import co.istad.exstadapi.features.certificate.dto.CertificateRequestDto;
import co.istad.exstadapi.features.certificate.dto.CertificateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class CertificateController {


    private final CertificateService certificateService;

    @PostMapping("/generate-certificates/{programSlug}")
    public ResponseEntity<?> generateCertificate(@PathVariable String programSlug, @RequestBody @Valid CertificateRequestDto request) {
        try {
            return ResponseEntity.ok(certificateService.generateCertificate(programSlug, request));
        } catch (Exception e) {
            log.error("Certificate generation failed", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to generate certificate", e);
        }
    }

    @PostMapping("/verify-certificates/{programSlug}/{certificateUuid}")
    public ResponseEntity<?> verifyCertificate(
            @PathVariable String programSlug,
            @RequestPart("file") MultipartFile file,
            @PathVariable String certificateUuid)
    {
        return new ResponseEntity<>(certificateService.verifyCertificate(programSlug , file ,certificateUuid), HttpStatus.OK);
    }

    @GetMapping("/certificates/{scholarUuid}/opening-program/{openingProgramUuid}")
    public List<CertificateResponse> getCertificateByScholar(@PathVariable String scholarUuid, @PathVariable String openingProgramUuid){
        return certificateService.getCertificateByScholarAndOpeningProgram(scholarUuid, openingProgramUuid);
    }

//    @PutMapping("/certificates/{scholarUuid}/opening-program/{openingProgramUuid}/delete")
//    public BasedMessage deleteCertificateByScholar(@PathVariable String scholarUuid, @PathVariable String openingProgramUuid){
//        return certificateService.deleteCertificateByScholarAndOpeningProgram(scholarUuid, openingProgramUuid);
//    }

    @GetMapping("/certificates")
    public ResponseEntity<?> getAllCertificates(){
        return ResponseEntity.ok(certificateService.getAllCertificates());
    }

//    @PutMapping("/certificates/{uuid}")
//    void removeTemplateByOpeningProgramUuid(@PathVariable String uuid){
//        certificateService.removeTemplate(uuid);
//    }

    @GetMapping("/certificates/scholars/{scholarUuid}")
    public ResponseEntity<?> getCertificateByScholar(@PathVariable String scholarUuid){
        return ResponseEntity.ok(certificateService.getCertificateByScholar(scholarUuid));
    }

    @GetMapping("/certificates/opening-programs/{openingProgramUuid}")
    public ResponseEntity<?> getCertificateByOpeningProgram(@PathVariable String openingProgramUuid) {
        return ResponseEntity.ok(certificateService.getCertificateByOpeningProgram(openingProgramUuid));
    }

}
