package co.istad.exstadapi.domain;

import co.istad.exstadapi.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "certificates")
public class Certificate extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "opening_program_id")
    private OpeningProgram openingProgram;

    private String fileName;

    @ManyToOne
    @JoinColumn(name = "scholar_id")
    private Scholar scholar;

    private String tempCertificateUrl;

    @Column(nullable = false)
    private Boolean isVerified;

    private LocalDate verifiedAt;

    private String certificateUrl;

    @Column(nullable = false)
    private Boolean isDisabled;

    @Column(nullable = false)
    private Boolean isDeleted;
}
