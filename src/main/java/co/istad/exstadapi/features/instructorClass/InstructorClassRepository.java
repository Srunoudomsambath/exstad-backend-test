package co.istad.exstadapi.features.instructorClass;

import co.istad.exstadapi.domain.Class;
import co.istad.exstadapi.domain.InstructorClass;
import co.istad.exstadapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InstructorClassRepository extends JpaRepository<InstructorClass, Integer> {

    Optional<InstructorClass> findByUuid(String uuid);

    boolean existsByUuid(String uuid);

    void deleteByUuid(String uuid);

    List<InstructorClass> findAllByInstructor(User instructor);

    List<InstructorClass> findAllBy_class(Class _class);
}
