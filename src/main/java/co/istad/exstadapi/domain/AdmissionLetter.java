package co.istad.exstadapi.domain;

import co.istad.exstadapi.audit.Auditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "admission_letters")
public class AdmissionLetter extends Auditable {

    @Id
    private Integer id;

    @Column(nullable = false, unique = true)
    private String uuid;

    @OneToOne
    @MapsId
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;

    @Column(nullable = false)
    private Boolean isDeleted;

    @Column(length = 500)
    private String admissionUrl;


}
