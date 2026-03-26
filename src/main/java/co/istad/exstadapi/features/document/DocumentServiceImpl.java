package co.istad.exstadapi.features.document;

import co.istad.exstadapi.domain.Document;
import co.istad.exstadapi.domain.Program;
import co.istad.exstadapi.enums.DocumentType;
import co.istad.exstadapi.features.document.dto.DocumentResponse;
import co.istad.exstadapi.features.program.ProgramRepository;
import co.istad.exstadapi.mapper.DocumentMapper;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final MinioClient minioClient;
    private final DocumentMapper documentMapper;
    private final ProgramRepository programRepository;

    @Value("${minio.bucket}")
    private String bucketName;

    private final String FILENAME_KEY = "filename";
    private final String EXTENSION_KEY = "extension";

    @Override
    public DocumentResponse uploadDocument(String programSlug, int gen, String documentType, String preferredFileName, MultipartFile file) {
        if (file.getOriginalFilename() == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty");
        }

        Program program = programSlug.equals("null") ? null : programRepository.findBySlugAndIsDeletedFalse(programSlug).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Program not found")
        );

        DocumentType fileDocumentType = DocumentType.fromKey(documentType);

        Map<String, String> fileNameAndExtension = getFileNameAndExtension(file.getOriginalFilename());

        String extension = fileNameAndExtension.get(EXTENSION_KEY);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMMdd-HHmmss"));
        String filename = preferredFileName.equals("null") ? String.format("%s-%s", UUID.randomUUID().toString().replace("-", ""), timestamp) : preferredFileName.toLowerCase();

        validateDocumentType(fileNameAndExtension.get(EXTENSION_KEY), fileDocumentType);

        String objectPath = getObjectPath(program, gen, fileDocumentType, filename, extension);

        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectPath)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (Exception e) {
            log.error("Cannot connect to Minio", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload document");
        }

        Document document = Document.builder()
                .name(filename)
                .program(program)
                .gen(gen)
                .documentType(fileDocumentType)
                .extension(extension)
                .fileSize(file.getSize())
                .mimeType(file.getContentType())
                .isDeleted(false)
                .build();
        document = documentRepository.save(document);
        return documentMapper.fromDocument(document);

    }

    @Override
    public ResponseEntity<Resource> previewDocument(String filename) {
        Document document = getDocument(filename);
        String objectPath = getObjectPath(document.getProgram(), document.getGen(), document.getDocumentType(), document.getName(), document.getExtension());

        try {
            InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectPath)
                            .build()
            );

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(document.getMimeType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + document.getName() + "." + document.getExtension() + "\"")
                    .body(new InputStreamResource(stream));

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get document");
        }
    }


    @Override
    public ResponseEntity<Resource> downloadDocument(String filename) {
        Document document = getDocument(filename);

        String objectPath = getObjectPath(document.getProgram(), document.getGen(), document.getDocumentType(), document.getName(), document.getExtension());

        try {
            InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectPath)
                            .build()
            );

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(document.getMimeType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + document.getName() + "." + document.getExtension() + "\"")
                    .body(new InputStreamResource(stream));

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get document");
        }
    }

    @Override
    public ResponseEntity<Resource> downloadDocumentsAsZip(List<String> filenames) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(baos);

            for (String filename : filenames) {
                Document document = getDocument(filename);

                String objectPath = getObjectPath(
                        document.getProgram(),
                        document.getGen(),
                        document.getDocumentType(),
                        document.getName(),
                        document.getExtension()
                );

                try (InputStream stream = minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(bucketName)
                                .object(objectPath)
                                .build()
                )) {
                    // Add file to ZIP
                    ZipEntry entry = new ZipEntry(document.getName() + "." + document.getExtension());
                    zos.putNextEntry(entry);

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = stream.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }

                    zos.closeEntry();
                }
            }

            zos.close();

            ByteArrayResource resource = new ByteArrayResource(baos.toByteArray());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=documents.zip")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(resource.contentLength())
                    .body(resource);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create zip file", e);
        }
    }

    @Override
    public DocumentResponse getDocumentByFileName(String filename) {
        return documentMapper.fromDocument(getDocument(filename));
    }

    @Override
    public DocumentResponse updateDocumentByFileName(String filename, MultipartFile file) {
        if (file.getOriginalFilename() == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty");
        }

        Document document = getDocument(filename);

        Map<String, String> fileNameAndExtension = getFileNameAndExtension(file.getOriginalFilename());
        if(!fileNameAndExtension.get(EXTENSION_KEY).equals(document.getExtension())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File extension does not match");
        }

        String objectPath = getObjectPath(document.getProgram(), document.getGen(), document.getDocumentType(), document.getName(), document.getExtension());

        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectPath)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (Exception e) {
            log.error("Cannot connect to Minio", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload document");
        }
        document.setUpdatedAt(Instant.now());
        document = documentRepository.save(document);
        return documentMapper.fromDocument(document);
    }

    @Override
    public List<DocumentResponse> getAllImages() {
        List<Document> images = documentRepository.findAllByIsDeletedFalse();

        return images.stream()
                .map(documentMapper::fromDocument)
                .toList();
    }

    private Document getDocument(String filename) {
        Map<String, String> file = getFileNameAndExtension(filename);
        return documentRepository.findByNameAndExtension(file.get(FILENAME_KEY), file.get(EXTENSION_KEY)).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found")
        );

    }

    private void validateDocumentType(String extension, DocumentType documentType) {
        for (String file : documentType.getSupportedFiles()) {
            if (extension.toLowerCase().equals(file)) {
                return;
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid document or file type");
    }

    private String getObjectPath(Program program, int gen, DocumentType documentType, String filename, String extension) {
        String programPath = program == null ? "general" : program.getSlug();
        String genPath = gen == 0 ? "general": "gen" + gen;
        return String.format(
                "%s/%s/%s/%s.%s",
                programPath,
                genPath,
                documentType.getFolderPath(),
                filename,
                extension.replace(".", "")
        );
    }

    //    This has no dot '.'
    private Map<String, String> getFileNameAndExtension(String originalFileName) {
        int lastDotIndex = originalFileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid filename");
        }
        String name = originalFileName.substring(0, lastDotIndex);
        String extension = originalFileName.substring(lastDotIndex + 1).toLowerCase();
        return Map.of(
                FILENAME_KEY, name,
                EXTENSION_KEY, extension
        );
    }
}
