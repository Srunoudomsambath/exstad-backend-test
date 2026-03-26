package co.istad.exstadapi.features.province;

import co.istad.exstadapi.features.province.dto.ProvinceResponse;

import java.util.List;

public interface ProvinceService {
    List<ProvinceResponse> getAllProvinces();
}
