package co.istad.exstadapi.domain;

import co.istad.exstadapi.audit.Auditable;
import co.istad.exstadapi.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "enrollments")
public class Enrollment extends Auditable {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "opening_program_id")
    private OpeningProgram openingProgram;

    @Column( nullable = false, length = 100)
    private String englishName;

    @Column( nullable = false, length = 100)
    private String khmerName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(nullable = false, length = 100, unique = true)
    private String phoneNumber;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(length = 500)
    private String avatar;

    @ManyToOne
    private Province province;

    @ManyToOne
    private CurrentAddress currentAddress;

    @ManyToOne
    private University university;

    @ManyToOne
    @JoinColumn(nullable = true)
    private Class _class;

    @Column(nullable = false, length = 200)
    private String educationQualification;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, String> extra;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    private Boolean isScholar;

    @Column(nullable = false)
    private Boolean isInterviewed;

    @Column(nullable = false)
    private Boolean isAchieved;

    @Column(nullable = false)
    private Boolean isPaid;

    @Column(nullable = false)
    private Boolean isPassed;

    @Column(precision = 10, scale = 2)
    private BigDecimal score;

    @OneToOne(mappedBy = "enrollment", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private AdmissionLetter admissionLetter;
}
