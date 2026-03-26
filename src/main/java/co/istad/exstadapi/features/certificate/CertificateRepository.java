package co.istad.exstadapi.features.certificate;

import co.istad.exstadapi.domain.Certificate;
import co.istad.exstadapi.domain.OpeningProgram;
import co.istad.exstadapi.domain.Scholar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CertificateRepository extends JpaRepository<Certificate, Integer> {

    List<Certificate> findAllByIsDeletedFalse();

    @Query("SELECT c FROM Certificate c " +
            "WHERE c.scholar = :scholar " +
            "AND c.openingProgram = :openingProgram")
    List<Certificate> findByScholarAndOpeningProgram(
            @Param("scholar") Scholar scholar,
            @Param("openingProgram") OpeningProgram openingProgram);
    Optional<Certificate> findByUuid(String uuid);
    List<Certificate> findByScholar(Scholar scholar);

    @Query("SELECT c FROM Certificate c " +
            "WHERE c.openingProgram = :openingProgram")
    List<Certificate> findByOpeningProgram(@Param("openingProgram") OpeningProgram openingProgram);


}

