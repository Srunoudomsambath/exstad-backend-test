package co.istad.exstadapi.domain;

import co.istad.exstadapi.audit.Auditable;
import co.istad.exstadapi.enums.AchievementType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "achievements")
public class Achievement extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String uuid;

    @Column(length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "opening_program_id")
    private OpeningProgram openingProgram;

    private String icon;

    @Column(nullable = false)
    private Boolean isDeleted;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AchievementType achievementType;

    @Column(length = 100)
    private String tag;

    @Column(length = 500)
    private String video;

    @Column(length = 500)
    private String link;

    @OneToMany(mappedBy = "achievement")
    private List<ScholarAchievement> scholarAchievements;
}
