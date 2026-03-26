package co.istad.exstadapi.domain;

import co.istad.exstadapi.audit.Auditable;
import co.istad.exstadapi.domain.vo.TranscriptData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transcripts")
public class Transcript extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "opening_program_id")
    private OpeningProgram openingProgram;

    @ManyToOne
    @JoinColumn(name = "scholar_id")
    private Scholar scholar;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private TranscriptData transcriptData;

    @Column(length = 500)
    private String qrCodeUrl;

    @Column(length = 500)
    private String tempTranscriptUrl;

    @Column(length = 500)
    private String transcriptUrl;

    @Column(nullable = false)
    private Boolean isVerified;

    @Column(nullable = false)
    private Boolean isDeleted;

    private LocalDate verifiedAt;
}
