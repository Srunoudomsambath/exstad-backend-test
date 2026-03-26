package co.istad.exstadapi.features.scholar;

import co.istad.exstadapi.domain.Scholar;
import co.istad.exstadapi.enums.ScholarStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ScholarRepository extends JpaRepository<Scholar, Integer> {
    Optional<Scholar> findByUuid(String uuid);

    List<Scholar> findAllByIsDeletedFalse();

    boolean existsByUuid(String uuid);

    boolean existsByPhoneNumber(String phoneNumber);

    void deleteByUuid(String uuid);

    @Modifying
    @Query("UPDATE Scholar s SET s.isDeleted = true WHERE s.uuid = ?1")
    void softDeleteByUuid(String uuid);

    @Modifying
    @Query("UPDATE Scholar s SET s.isDeleted = false WHERE s.uuid = ?1")
    void restoreByUuid(String uuid);

    @Query("SELECT DISTINCT s FROM Scholar s " +
           "JOIN s.scholarClasses sc " +
           "JOIN sc._class c " +
           "JOIN c.openingProgram op " +
           "WHERE op.uuid = :openingProgramUuid AND s.isDeleted = false AND sc.isDeleted = false")
    List<Scholar> findAllByOpeningProgramUuid(String openingProgramUuid);

    List<Scholar> findAllByStatusAndIsDeletedFalse(ScholarStatus status);
    List<Scholar> findAllByIsAbroadTrueAndIsDeletedFalse();

}