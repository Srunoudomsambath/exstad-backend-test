package co.istad.exstadapi.features.university;

import co.istad.exstadapi.domain.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UniversityRepository extends JpaRepository<University, Integer> {
    Optional<University> findByUuid(String uuid);
    List<University> findAllByIsDeletedFalse();
    boolean existsByEnglishName(String englishName);
    boolean existsByKhmerName(String khmerName);
    boolean existsByUuid(String uuid);

    Optional<University> findByEnglishName(String englishName);
    @Modifying
    @Query("UPDATE University AS u SET u.isDeleted = true WHERE u.uuid = ?1")
    void deleteSoft(String uuid);
}
