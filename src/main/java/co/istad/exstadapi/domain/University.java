package co.istad.exstadapi.domain;

import co.istad.exstadapi.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "universities")
public class University extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String uuid;

    @Column(unique = true, nullable = false, length = 150)
    private String englishName;

    @Column(unique = true, nullable = false, length = 150)
    private String khmerName;

    @Column(length = 50)
    private String shortName;

    @Column(nullable = false)
    private Boolean isDeleted;

    @OneToMany(mappedBy = "university")
    private List<Scholar> scholars;
}

