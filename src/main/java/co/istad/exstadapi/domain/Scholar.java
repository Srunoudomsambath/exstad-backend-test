package co.istad.exstadapi.domain;

import co.istad.exstadapi.audit.Auditable;
import co.istad.exstadapi.domain.vo.Career;
import co.istad.exstadapi.domain.vo.SocialLink;
import co.istad.exstadapi.domain.vo.Specialist;
import co.istad.exstadapi.enums.ScholarStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "scholars")
public class Scholar extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String uuid;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;

    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;

    @ManyToOne
    @JoinColumn(name = "current_address_id")
    private CurrentAddress currentAddress;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    private List<SocialLink> socialLink;

    @Column(length = 50)
    private String nickname;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<Specialist> specialist;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<Career> careers;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<String> completedCourses;

    @Column(length = 500)
    private String avatar;

    @Column(nullable = false)
    private Boolean isDeleted;

    @Column(length = 20, unique = true, nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    ScholarStatus status;

    @Column(length = 20)
    private String phoneFamilyNumber;

    @Column(nullable = false)
    private Boolean isPublic;

    @Column(nullable = false)
    private Boolean isAbroad;

    @Column(nullable = false)
    private Boolean isEmployed;

    @Column(columnDefinition = "TEXT")
    private String quote;

    @OneToMany(mappedBy = "scholar")
    private List<ScholarBadge> scholarsBadges;

    @OneToMany(mappedBy = "scholar")
    private List<ScholarClass> scholarClasses;

    @OneToMany(mappedBy = "scholar")
    private List<ScholarAchievement> scholarAchievements;

    @OneToMany(mappedBy = "scholar")
    private List<Transcript> transcripts;

    @OneToMany(mappedBy = "scholar")
    private List<Certificate> certificates;
}

