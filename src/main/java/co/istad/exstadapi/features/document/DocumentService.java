package co.istad.exstadapi.features.document;

import co.istad.exstadapi.features.document.dto.DocumentResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {

    DocumentResponse uploadDocument(String programSlug, int gen, String documentType, String filename, MultipartFile file);
    ResponseEntity<Resource> previewDocument(String filename);
    ResponseEntity<Resource> downloadDocument(String filename);
    ResponseEntity<Resource> downloadDocumentsAsZip(List<String> filenames);
    DocumentResponse getDocumentByFileName(String filename);
    DocumentResponse updateDocumentByFileName(String filename, MultipartFile file);
    List<DocumentResponse> getAllImages();
}
