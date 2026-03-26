package co.istad.exstadapi.features.province;

import co.istad.exstadapi.domain.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProvinceRepository extends JpaRepository<Province, Integer> {

    Optional<Province> findByEnglishName(String englishName);

    List<Province> findAllByIsDeletedFalse();
}
