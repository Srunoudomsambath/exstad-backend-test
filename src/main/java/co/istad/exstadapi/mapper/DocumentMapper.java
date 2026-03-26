package co.istad.exstadapi.mapper;

import co.istad.exstadapi.audit.AuditableMapper;
import co.istad.exstadapi.domain.Document;
import co.istad.exstadapi.features.document.dto.DocumentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MapperHelper.class, AuditableMapper.class})
public interface DocumentMapper {

    @Mapping(source = "document", target = "uri", qualifiedByName = "toPublicAccessDocument")
    @Mapping(source = "document", target = "name", qualifiedByName = "toFullDocumentName")
    @Mapping(source = "program.slug", target = "programSlug")
    @Mapping(source = ".", target = "audit")
    DocumentResponse fromDocument(Document document);
}
