package co.istad.exstadapi.features.document;

import co.istad.exstadapi.features.document.dto.DocumentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/documents")
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/{programSlug}/{gen}/{documentType}")
    public DocumentResponse upload(
            @PathVariable String programSlug,
            @PathVariable int gen,
            @PathVariable String documentType,
            @RequestParam(defaultValue = "null", required = false) String filename,
            @RequestPart("file") MultipartFile file)
    {
        return documentService.uploadDocument(programSlug, gen, documentType, filename, file);
    }

    @GetMapping("/{fileName}")
    public DocumentResponse getImageByName(@PathVariable String fileName) {
        return documentService.getDocumentByFileName(fileName);
    }

    @PutMapping("/{fileName}")
    public DocumentResponse updateImageByFileName(@PathVariable String fileName,@RequestPart("file") MultipartFile file) {
        return documentService.updateDocumentByFileName(fileName, file);
    }

    @GetMapping
    public List<DocumentResponse> getAllImages() {
        return documentService.getAllImages();
    }
}
