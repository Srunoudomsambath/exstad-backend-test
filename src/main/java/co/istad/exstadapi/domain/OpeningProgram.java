package co.istad.exstadapi.domain;

import co.istad.exstadapi.audit.Auditable;
import co.istad.exstadapi.domain.vo.*;
import co.istad.exstadapi.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "opening_programs")
public class OpeningProgram extends Auditable {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    @Column(nullable = false, unique = true)
    private String uuid;

    @Column(length = 30, nullable = false)
    private String deadline;

    @Column(length = 60, nullable = false)
    private String title;

    @Column(length = 60, nullable = false)
    private String slug;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> templates;

    @Column(length = 500)
    private String thumbnail;

    @Column(length = 500)
    private String posterUrl;

    @Column(nullable = false)
    private Integer totalSlot;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal registerFee;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal originalFee;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal scholarship;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(length = 255)
    private String telegramGroup;

    @Column(nullable = false)
    private Integer generation;

    @Column(nullable = false)
    private Boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    @Column(length = 500)
    private String qrCodeUrl;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<Detail> details;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<Timeline> timelines;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<Curriculum> curricula;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<Roadmap> roadmaps;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<LearningOutcome> learningOutcomes;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<Requirement> requirements;

    @Column(length = 100)
    private String duration;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<Activity> activities;

    @Column(length = 500)
    private String curriculumPdfUri;

    @Column(nullable = false)
    private Boolean isActive;

    @OneToMany(mappedBy = "openingProgram")
    private List<Class> classes;

    @OneToMany(mappedBy = "openingProgram")
    private List<Achievement> achievements;

    @OneToMany(mappedBy = "openingProgram")
    private List<Enrollment> enrollments;

    @OneToMany(mappedBy = "openingProgram")
    private List<Transcript> transcripts;

    @OneToMany(mappedBy = "openingProgram")
    private List<Certificate> certificates;
}
