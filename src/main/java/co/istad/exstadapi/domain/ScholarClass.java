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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "scholars_classes")
public class ScholarClass extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class _class;

    @ManyToOne
    @JoinColumn(name = "scholar_id")
    private Scholar scholar;

    @Column(nullable = false)
    private Boolean isReminded;

    @Column(nullable = false)
    private Boolean isPaid;

    @Column(nullable = false)
    private Boolean isDeleted;
}
