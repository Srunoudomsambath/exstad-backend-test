package co.istad.exstadapi.features.document;

import co.istad.exstadapi.domain.Document;
import co.istad.exstadapi.enums.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
    Optional<Document> findByNameAndExtension(String name, String extension);

    List<Document> findAllByIsDeletedFalse();

    Optional<Document> findByNameAndDocumentTypeIn(String name, List<DocumentType> types);

    List<Document> findByDocumentTypeIn(List<DocumentType> types);
}
