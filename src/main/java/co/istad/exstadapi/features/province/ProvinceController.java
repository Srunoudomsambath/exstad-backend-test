package co.istad.exstadapi.features.province;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/provinces")
public class ProvinceController {
    private final ProvinceService provinceService;

    @GetMapping
    public ResponseEntity<?> getAllProvinces() {
        return new ResponseEntity<>(
                Map.of("provinces",provinceService.getAllProvinces()),
                HttpStatus.OK);
    }
}
