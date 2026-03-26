package co.istad.exstadapi.domain;

import co.istad.exstadapi.audit.Auditable;
import co.istad.exstadapi.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String uuid; // keycloak uuid

    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(length = 50, nullable = false)
    private String englishName;

    @Column(length = 50, nullable = false)
    private String khmerName;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(nullable = false, length = 10)
    private String gender;

    @Column(nullable = false)
    private Boolean isDeleted;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Scholar scholar;

    @OneToMany(mappedBy = "instructor")
    private List<InstructorClass> instructorClasses;
}
