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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "scholar_badges")
public class ScholarBadge extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "scholar_id")
    private Scholar scholar;

    @ManyToOne
    @JoinColumn(name = "badge_id")
    private Badge badge;

    private LocalDate completionDate;

    @Column(nullable = false)
    private boolean isDeleted;
}


