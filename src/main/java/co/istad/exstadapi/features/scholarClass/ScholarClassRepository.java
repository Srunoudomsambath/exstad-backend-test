package co.istad.exstadapi.features.scholarClass;

import co.istad.exstadapi.domain.Class;
import co.istad.exstadapi.domain.Scholar;
import co.istad.exstadapi.domain.ScholarClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ScholarClassRepository extends JpaRepository<ScholarClass, Integer> {

    Optional<ScholarClass> findByUuid(String uuid);

    List<ScholarClass> findAllByIsDeletedFalse();

    List<ScholarClass> findAllByScholar(Scholar scholar);

    List<ScholarClass> findAllBy_class(Class _class);


    boolean existsByUuid(String uuid);

    void deleteByUuid(String uuid);

    @Modifying
    @Query("UPDATE ScholarClass sc SET sc.isDeleted = true WHERE sc.uuid = ?1")
    void softDeleteByUuid(String uuid);

    @Modifying
    @Query("UPDATE ScholarClass sc SET sc.isDeleted = false WHERE sc.uuid = ?1")
    void restoreByUuid(String uuid);

    @Modifying
    @Query("UPDATE ScholarClass sc SET sc.isPaid = true WHERE sc.uuid = ?1")
    void markAsPaidByUuid(String uuid);

    @Modifying
    @Query("UPDATE ScholarClass sc SET sc.isPaid = false WHERE sc.uuid = ?1")
    void markAsUnpaidByUuid(String uuid);

    @Modifying
    @Query("UPDATE ScholarClass sc SET sc.isReminded = true WHERE sc.uuid = ?1")
    void markAsRemindedByUuid(String uuid);

    @Modifying
    @Query("UPDATE ScholarClass sc SET sc.isReminded = false WHERE sc.uuid = ?1")
    void markAsUnremindedByUuid(String uuid);

}
