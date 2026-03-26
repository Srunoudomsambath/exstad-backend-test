package co.istad.exstadapi.features.certificate;

import co.istad.exstadapi.base.BasedMessage;
import co.istad.exstadapi.features.certificate.dto.CertificateResponse;
import co.istad.exstadapi.features.certificate.dto.CertificateRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CertificateService {

    CertificateResponse generateCertificate(String programSlug, CertificateRequestDto request);

    CertificateResponse verifyCertificate(String programSlug, MultipartFile file,String certificateUuid);

    List<CertificateResponse> getAllCertificates();

    List<CertificateResponse> getCertificateByScholarAndOpeningProgram(String scholarUuid, String openingProgramUuid);
    List<CertificateResponse> getCertificateByScholar(String scholarUuid);
    List<CertificateResponse> getCertificateByOpeningProgram(String openingProgramUuid);

//    BasedMessage deleteCertificateByScholarAndOpeningProgram(String scholarUuid, String openingProgramUuid);
//    void removeTemplate(String uuid);
}
