package co.istad.exstadapi.audit;
import co.istad.exstadapi.features.user.UserResolver;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class AuditableMapper {

    @Autowired
    protected UserResolver userResolver;

    public AuditableDto toDto(Auditable auditable) {
        if (auditable == null) return null;
        return new AuditableDto(
                userResolver.getUserName(auditable.getCreatedBy()),
                userResolver.getUserName(auditable.getUpdatedBy()),
                auditable.getCreatedAt(),
                auditable.getUpdatedAt()
        );
    }
}

