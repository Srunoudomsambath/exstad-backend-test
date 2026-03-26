package co.istad.exstadapi.features.province;

import co.istad.exstadapi.domain.Province;
import co.istad.exstadapi.features.province.dto.ProvinceResponse;
import co.istad.exstadapi.mapper.ProvinceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {
    private final ProvinceRepository provinceRepository;
    private final ProvinceMapper provinceMapper;
    @Override
    public List<ProvinceResponse> getAllProvinces() {
        List<Province> provinces = provinceRepository.findAllByIsDeletedFalse();
        return provinces.stream()
                .filter(province -> province.getIsDeleted().equals(false))
                .map(provinceMapper::toProvinceResponse)
                .toList();
    }
}
