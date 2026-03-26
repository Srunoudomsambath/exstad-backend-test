package co.istad.exstadapi.features.enrollment;

import co.istad.exstadapi.base.BasedMessage;
import co.istad.exstadapi.domain.Class;
import co.istad.exstadapi.domain.Enrollment;
import co.istad.exstadapi.domain.OpeningProgram;
import co.istad.exstadapi.domain.Scholar;
import co.istad.exstadapi.features.classes.ClassRepository;
import co.istad.exstadapi.features.enrollment.dto.EnrollmentRequest;
import co.istad.exstadapi.features.enrollment.dto.EnrollmentRequestUpdate;
import co.istad.exstadapi.features.enrollment.dto.EnrollmentResponse;
import co.istad.exstadapi.features.enrollment.dto.SetScoreExamScholar;
import co.istad.exstadapi.features.openingProgram.OpeningProgramRepository;
import co.istad.exstadapi.mapper.EnrollmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentMapper enrollmentMapper;
    private final OpeningProgramRepository openingProgramRepository;


    @Override
    public EnrollmentResponse createEnrollment(EnrollmentRequest enrollmentRequest) {
        if (enrollmentRepository.existsByEmail(enrollmentRequest.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already taken");
        }
        if (enrollmentRepository.existsByPhoneNumber(enrollmentRequest.phoneNumber())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number is already taken");
        }
        Enrollment enrollment = enrollmentMapper.toEnrollment(enrollmentRequest);
        if (enrollmentRequest.amount()==null){
            enrollment.setAmount(BigDecimal.ZERO);
        } else {
            enrollment.setAmount(enrollmentRequest.amount());
        }
        if (enrollmentRequest.isScholar()==null){
            enrollment.setIsScholar(false);
        } else {
            enrollment.setIsScholar(enrollmentRequest.isScholar());
        }
        enrollment.setScore(BigDecimal.ZERO);
        enrollment.setUuid(UUID.randomUUID().toString());
        enrollment.setIsInterviewed(false);
        enrollment.setIsAchieved(false);
        enrollment.setIsPaid(false);
        enrollment.setIsPassed(false);
        enrollment = enrollmentRepository.save(enrollment);
        return enrollmentMapper.fromEnrollment(enrollment);
    }

    @Override
    public List<EnrollmentResponse> getAllEnrollments() {
        return enrollmentRepository.findAll().stream().map(
                enrollmentMapper::fromEnrollment
        ).toList();
    }

    @Override
    public List<EnrollmentResponse> getAllInterviewedEnrollments() {
        return enrollmentRepository.findAllByIsInterviewedAndIsAchievedAndIsPassed(true, false,false ).stream().map(
                enrollmentMapper::fromEnrollment
        ).toList();
    }

    @Override
    public List<EnrollmentResponse> getAllPassedEnrollments() {
        return enrollmentRepository.findAllByIsInterviewedAndIsAchievedAndIsPassed(true, false,true ).stream().map(
                enrollmentMapper::fromEnrollment
        ).toList();
    }

    @Override
    public List<EnrollmentResponse> getAllAchievedEnrollments() {
        return enrollmentRepository.findAllByIsAchieved(true).stream().map(
                enrollmentMapper::fromEnrollment
        ).toList();
    }


    @Override
    public EnrollmentResponse getEnrollment(String uuid) {
        return enrollmentMapper.fromEnrollment(getEnrollmentByUuid(uuid));
    }

    @Override
    public BasedMessage markIsScholar(String uuid) {
        Enrollment enrollment = enrollmentRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not found"));
        enrollment.setIsScholar(true);
        enrollmentRepository.save(enrollment);
        return new BasedMessage("Enrollment has been marked as scholar");
    }

    @Override
    public EnrollmentResponse setScoreExamScholar(String uuid, SetScoreExamScholar setScoreExamScholar) {
        Enrollment enrollment = enrollmentRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not found"));
        enrollment.setScore(setScoreExamScholar.score());
        enrollment = enrollmentRepository.save(enrollment);
        return enrollmentMapper.fromEnrollment(enrollment);
    }

    @Override
    public List<EnrollmentResponse> getAllEnrollmentsByOpeningProgramUuid(String openingProgramUuid) {
        OpeningProgram program = openingProgramRepository.findByUuid(openingProgramUuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening program not found")
        );
        return enrollmentRepository.findAllByOpeningProgram(program).stream().map(
                enrollmentMapper::fromEnrollment
        ).toList();
    }

    @Override
    public List<EnrollmentResponse> getAllInterviewedEnrollmentsByOpeningProgramUuid(String openingProgramUuid) {
        OpeningProgram program = openingProgramRepository.findByUuid(openingProgramUuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening program not found")
        );
        return enrollmentRepository.findAllByOpeningProgramAndIsInterviewedAndIsAchievedAndIsPassed(program,true, false,false ).stream().map(
                enrollmentMapper::fromEnrollment
        ).toList();
    }

    @Override
    public List<EnrollmentResponse> getAllPassedEnrollmentsByOpeningProgramUuid(String openingProgramUuid) {
        OpeningProgram program = openingProgramRepository.findByUuid(openingProgramUuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening program not found")
        );
        return enrollmentRepository.findAllByOpeningProgramAndIsInterviewedAndIsAchievedAndIsPassed(program,true, false,true ).stream().map(
                enrollmentMapper::fromEnrollment
        ).toList();
    }

    @Override
    public List<EnrollmentResponse> getAllAchievedEnrollmentsByOpeningProgramUuid(String openingProgramUuid) {
        OpeningProgram program = openingProgramRepository.findByUuid(openingProgramUuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening program not found")
        );
        return enrollmentRepository.findAllByOpeningProgramAndIsAchieved(program, true).stream().map(
                enrollmentMapper::fromEnrollment
        ).toList();
    }

    @Override
    public EnrollmentResponse updateEnrollment(String uuid, EnrollmentRequestUpdate enrollmentRequestUpdate) {
        Enrollment enrollment = getEnrollmentByUuid(uuid);
        enrollmentMapper.toEnrollmentPartially(enrollmentRequestUpdate, enrollment);
        enrollment = enrollmentRepository.save(enrollment);
        return enrollmentMapper.fromEnrollment(enrollment);
    }

    private Enrollment getEnrollmentByUuid(String uuid) {
        return enrollmentRepository.findByUuid(uuid).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not found")
        );
    }
}
