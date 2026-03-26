package co.istad.exstadapi.domain;

import co.istad.exstadapi.audit.Auditable;
import co.istad.exstadapi.enums.DocumentType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "documents")
public class Document extends Auditable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column(unique = true, nullable = false, length = 150)
    private String name;

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false)
    int gen;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @Column(nullable = false, length = 10)
    private String extension;

    @Column(nullable = false, length = 50)
    private String mimeType;

    @Column(nullable = false)
    private Boolean isDeleted;
}
