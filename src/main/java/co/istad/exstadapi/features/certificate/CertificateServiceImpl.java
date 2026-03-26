package co.istad.exstadapi.features.certificate;

import co.istad.exstadapi.domain.Certificate;
import co.istad.exstadapi.domain.OpeningProgram;
import co.istad.exstadapi.domain.Scholar;
import co.istad.exstadapi.features.certificate.dto.CertificateResponse;
import co.istad.exstadapi.features.certificate.dto.CertificateRequestDto;
import co.istad.exstadapi.features.document.DocumentService;
import co.istad.exstadapi.features.document.dto.DocumentResponse;
import co.istad.exstadapi.features.openingProgram.OpeningProgramRepository;
import co.istad.exstadapi.features.scholar.ScholarRepository;
import co.istad.exstadapi.mapper.CertificateMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CertificateServiceImpl implements CertificateService {

    private final DocumentService documentService;
    private final ScholarRepository scholarRepository;
    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;
    private final OpeningProgramRepository openingProgramRepository;

    @Override
    public CertificateResponse generateCertificate(String programSlug,CertificateRequestDto request) {
        try {
            if (request.scholarUuid() == null || request.scholarUuid().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"At least one student name is required.");
            }
            if (request.bgImage() == null || request.bgImage().trim().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Template filename is required.");
            }

            // Use JasperReports that is already compiled (.jasper file)
            InputStream reportStream = getClass().getResourceAsStream("/generates/certificate.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);

            OpeningProgram openingProgram = openingProgramRepository.findByUuid(request.openingProgramUuid())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Opening Program not found"));

            Scholar scholar = scholarRepository.findByUuid(request.scholarUuid()).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scholar not found")
            );

            // Prepare parameters (data) for JasperReports
            Map<String, Object> parameters = new HashMap<>();
            if(scholar.getUser().getGender().equals("Male")){
                parameters.put("studentName", "Mr. "+scholar.getUser().getEnglishName().toUpperCase());
            }
            else if(scholar.getUser().getGender().equals("Female")){
                parameters.put("studentName", "Ms. "+scholar.getUser().getEnglishName().toUpperCase());
            }
            parameters.put("bgImage", request.bgImage());

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    new JREmptyDataSource()
            );

            byte[]  pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

            // Upload PDF to MinIO
            String fileName = "certificate.pdf";
            MultipartFile multipartFile = new InMemoryMultipartFile(
                    "file",               // form field name
                    fileName,             // original filename
                    "application/pdf",    // content type
                    pdfBytes              // content
            );

            String certiFilename =
                    scholar.getUser().getEnglishName().replaceAll("\\s+", "_").toLowerCase() + "_" +
                            openingProgram.getTitle().replaceAll("\\s+", "_").toLowerCase() + "_gen" +
                            openingProgram.getGeneration() + "_certificate_" +
                            DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now()) + "_" +
                            UUID.randomUUID().toString().substring(0, 8);
            DocumentResponse documentResponse = documentService.uploadDocument(
                    programSlug,
                    openingProgram.getGeneration(),
                    "certificate",
                    certiFilename,
                    multipartFile
            );

            // Save certificate info to database
            Certificate certificate = new Certificate();
            certificate.setUuid(UUID.randomUUID().toString());
            certificate.setScholar(scholar);
            certificate.setOpeningProgram(openingProgram);
            certificate.setTempCertificateUrl(documentResponse.uri());
            certificate.setFileName(documentResponse.name());
            certificate.setIsDisabled(false);
            certificate.setIsDeleted(false);
            certificate.setIsVerified(false);
            certificate.setVerifiedAt(null);
            // Set certificateUrl to null because it's not verified yet
            certificate.setCertificateUrl(null);
            certificateRepository.save(certificate);

            return certificateMapper.toCertificateResponse(certificate);
        } catch (Exception e) {
            log.error("Certificate generation failed", e);
            throw new RuntimeException("Failed to generate certificate", e);
        }
    }

    @Override
    public CertificateResponse verifyCertificate(String programSlug, MultipartFile file, String certificateUuid) {
        Certificate certificate = certificateRepository.findByUuid(certificateUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Certificate not found"));

        OpeningProgram openingProgram = openingProgramRepository.findByUuid(certificate.getOpeningProgram().getUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Opening Program not found"));

        DocumentResponse documentResponse = documentService.uploadDocument(programSlug, openingProgram.getGeneration(), "certificate", "null", file);

        certificate.setIsVerified(true);
        certificate.setVerifiedAt(LocalDate.now());
        certificate.setCertificateUrl(documentResponse.uri());
        certificateRepository.save(certificate);
        return certificateMapper.toCertificateResponse(certificate);
    }

    @Override
    public List<CertificateResponse> getAllCertificates() {
        List<Certificate> certificates = certificateRepository.findAll();
        return certificates.stream().map(certificateMapper::toCertificateResponse).toList();
    }

    @Override
    public List<CertificateResponse> getCertificateByScholarAndOpeningProgram(String scholarUuid, String openingProgramUuid) {
        Scholar scholar = scholarRepository.
                findByUuid(scholarUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Scholar not found"));
        OpeningProgram openingProgram = openingProgramRepository.findByUuid(openingProgramUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Opening Program not found"));

        List<Certificate> certificates = certificateRepository.findByScholarAndOpeningProgram(scholar, openingProgram)
                .stream().toList();
        if(certificates.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Certificate not found");
        }
        return certificates.stream().map(certificateMapper::toCertificateResponse).toList();
    }

    @Override
    public List<CertificateResponse> getCertificateByScholar(String scholarUuid) {
        Scholar scholar = scholarRepository.
                findByUuid(scholarUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Scholar not found"));
        List<Certificate> certificates = certificateRepository.findByScholar(scholar);
        return certificates.stream().map(certificateMapper::toCertificateResponse).toList();
    }

    @Override
    public List<CertificateResponse> getCertificateByOpeningProgram(String openingProgramUuid) {
        OpeningProgram openingProgram = openingProgramRepository.findByUuid(openingProgramUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Opening Program not found"));
        List<Certificate> certificates = certificateRepository.findByOpeningProgram(openingProgram);
        return certificates.stream().map(certificateMapper::toCertificateResponse).toList();
    }


//    @Override
//    public void removeTemplate(String uuid) {
//        OpeningProgram o = openingProgramRepository.findByUuid(uuid)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Opening Program not found"));
//        o.getTemplates().remove(0);
//        openingProgramRepository.save(o);
//    }

//    @Override
//    public BasedMessage deleteCertificateByScholarAndOpeningProgram(String scholarUuid, String openingProgramUuid) {
//        Scholar scholar = scholarRepository.
//                findByUuid(scholarUuid).orElseThrow(() -> new IllegalArgumentException("Scholar not found"));
//        OpeningProgram openingProgram = openingProgramRepository.findByUuid(openingProgramUuid)
//                .orElseThrow(() -> new IllegalArgumentException("Opening Program not found"));
//
//        List<Certificate> certificates = certificateRepository.findByScholarAndOpeningProgram(scholar, openingProgram)
//                .stream().toList();
//
//        certificates.setIsDeleted(true);
//        certificateRepository.save(certificates);
//        return new BasedMessage("Certificate deleted successfully");
//    }
}