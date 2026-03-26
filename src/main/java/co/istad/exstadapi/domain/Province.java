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
@Table(name = "provinces")
public class Province extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String uuid;

    @Column(unique = true, nullable = false, length = 150)
    private String englishName;

    @Column(unique = true, nullable = false, length = 150)
    private String khmerName;

    @Column(nullable = false)
    private Boolean isDeleted;

    @OneToMany(mappedBy = "province")
    private List<CurrentAddress> currentAddresses;

    @OneToMany(mappedBy = "province")
    private List<Scholar> scholars;

}
