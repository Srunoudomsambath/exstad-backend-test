package co.istad.exstadapi.features.openingProgram;

import co.istad.exstadapi.domain.OpeningProgram;
import co.istad.exstadapi.domain.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.jar.JarFile;

public interface OpeningProgramRepository extends JpaRepository<OpeningProgram, Integer>{
    Optional<OpeningProgram> findByUuid(String uuid);
    Optional<OpeningProgram> findBySlug(String slug);
    Optional<OpeningProgram> findByClasses_Uuid(String classUuid);
    boolean existsByUuid(String uuid);
    Optional<OpeningProgram> findByProgram(Program program);
    List<OpeningProgram> findAllByProgram(Program program);
    List<OpeningProgram> findAllByIsDeletedFalse();

    Optional<OpeningProgram> findByTitleIgnoreCase(String title);

    void deleteByUuid(String uuid);

    @Modifying
    @Query("UPDATE OpeningProgram op SET op.isDeleted = true WHERE op.uuid = ?1")
    void softDeleteByUuid(String uuid);

    @Modifying
    @Query("UPDATE OpeningProgram op SET op.isDeleted = false WHERE op.uuid = ?1")
    void restoreByUuid(String uuid);

    @Modifying
    @Query("UPDATE OpeningProgram op SET op.isActive = false WHERE op.uuid = ?1")
    void deactivateByUuid(String uuid);

    @Modifying
    @Query("UPDATE OpeningProgram op SET op.isActive = true WHERE op.uuid = ?1")
    void activateByUuid(String uuid);

    List<OpeningProgram> findAllByProgramAndIsDeletedFalse(Program program);
}

