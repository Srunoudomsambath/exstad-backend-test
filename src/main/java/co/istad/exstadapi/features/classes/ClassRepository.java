package co.istad.exstadapi.features.classes;

import co.istad.exstadapi.domain.Class;
import co.istad.exstadapi.domain.OpeningProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public interface ClassRepository extends JpaRepository<Class,Integer> {

    Optional<Class> findByUuid(String uuid);
    Optional<Class> findByClassCode(String classCode);
    Optional<Class> findByRoom(String room);
    Optional<Class> findByOpeningProgram(OpeningProgram openingProgram);
    List<Class> findAllByIsDeletedFalse();
    Optional<Class> findByRoomIgnoreCase(String room);
    boolean existsByUuid(String uuid);
    boolean existsByClassCode(String classCode);
    List<Class> findAllByOpeningProgramAndIsDeletedFalse(OpeningProgram openingProgram);
    Optional<Class> findByClassCodeIgnoreCase(String classCode);


    @Modifying
    @Query("UPDATE Class c SET c.isDeleted = true WHERE c.uuid = ?1")
    void softDeleteByUuid(String uuid);

    @Modifying
    @Query("UPDATE Class c SET c.isDeleted = false WHERE c.uuid = ?1")
    void restoreByUuid(String uuid);

    void deleteByUuid(String uuid);

    @Modifying
    @Query("UPDATE Class c SET c.isEnabled = false WHERE c.uuid = ?1")
    void disableByUuid(String uuid);

    @Modifying
    @Query("UPDATE Class c SET c.isEnabled = true WHERE c.uuid = ?1")
    void enableByUuid(String uuid);

    @Modifying
    @Query("UPDATE Class c SET c.isWeekend = true WHERE c.uuid = ?1")
    void setToWeekendByUuid(String uuid);

    @Modifying
    @Query("UPDATE Class c SET c.isWeekend = false WHERE c.uuid = ?1")
    void setToWeekdayByUuid(String uuid);

    List<Class> findAllByOpeningProgram(OpeningProgram openingProgram);
}
