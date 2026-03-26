package co.istad.exstadapi.features.enrollment;

import co.istad.exstadapi.domain.Enrollment;
import co.istad.exstadapi.domain.OpeningProgram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {

    List<Enrollment> findAllByIsAchieved(Boolean isAchieved);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    List<Enrollment> findAllByOpeningProgramAndIsAchieved(OpeningProgram openingProgram,Boolean isAchieved);

    Optional<Enrollment> findByUuid(String uuid);

    List<Enrollment> findAllByIsInterviewedAndIsAchievedAndIsPassed(Boolean isInterviewed, Boolean isAchieved, Boolean isPassed);
    List<Enrollment> findAllByOpeningProgramAndIsInterviewedAndIsAchievedAndIsPassed(OpeningProgram openingProgram, Boolean isInterviewed, Boolean isAchieved, Boolean isPassed);

    List<Enrollment> findAllByOpeningProgram(OpeningProgram openingProgram);
}
