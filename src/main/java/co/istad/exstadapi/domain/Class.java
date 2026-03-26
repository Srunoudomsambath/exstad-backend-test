package co.istad.exstadapi.domain;

import co.istad.exstadapi.audit.Auditable;
import co.istad.exstadapi.enums.Shift;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "classes")
public class Class extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "opening_program_id")
    private OpeningProgram openingProgram;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Shift shift;

    @Column(nullable = false)
    private Boolean isWeekend;

    @Column(length = 60, nullable = false)
    private String room;

    @Column(length = 20, nullable = false, unique = true)
    private String classCode;

    @Column(length = 500)
    private String telegram;

    @Column(nullable = false)
    private Integer totalSlot;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private Boolean isDeleted;

    @Column(nullable = false)
    private Boolean isEnabled;

    @OneToMany(mappedBy = "_class")
    private List<InstructorClass> instructorClasses;

    @OneToMany(mappedBy = "_class")
    private List<ScholarClass> scholarClasses;

    @OneToMany(mappedBy = "_class")
    private List<Enrollment> enrollments;
}
