package co.istad.exstadapi.init;

import co.istad.exstadapi.domain.CurrentAddress;
import co.istad.exstadapi.domain.Province;
import co.istad.exstadapi.features.currentAddress.CurrentAddressRepository;
import co.istad.exstadapi.features.province.ProvinceRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@DependsOn("provinceInit")
public class CurrentAddressInit {

    private final CurrentAddressRepository currentAddressRepository;
    private final ProvinceRepository provinceRepository;

    @PostConstruct
    void initCurrentAddresses() {
        if (currentAddressRepository.count() == 0) {


            Province phnomPenh = provinceRepository.findByEnglishName("Phnom Penh")
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Phnom Penh province not found!"));

            List<CurrentAddress> khans = List.of(
                    build("Tuol Kouk", "ទួលគោក", phnomPenh),
                    build("Sen Sok", "សែនសុខ", phnomPenh),
                    build("Russey Keo", "ឬស្សីកែវ", phnomPenh),
                    build("Prek Pnov", "ព្រែកព្នៅ", phnomPenh),
                    build("Prampir Makara", "ប្រាំពីរមករា", phnomPenh),
                    build("Pou Senchey", "ពោធិ៍សែនជ័យ", phnomPenh),
                    build("Mean Chey", "មានជ័យ", phnomPenh),
                    build("Kamboul", "កំបូល", phnomPenh),
                    build("Daun Penh", "ដូនពេញ", phnomPenh),
                    build("Dangkao", "ដង្កោ", phnomPenh),
                    build("Chroy Changva", "ជ្រោយចង្វា", phnomPenh),
                    build("Chbar Ampov", "ច្បារអំពៅ", phnomPenh),
                    build("Chamkar Mon", "ចំការមន", phnomPenh),
                    build("Boeng Keng Kang", "បឹងកេងកង", phnomPenh)
            );

            currentAddressRepository.saveAll(khans);
        }
    }

    private CurrentAddress build(String english, String khmer, Province province) {
        return CurrentAddress.builder()
                .uuid(UUID.randomUUID().toString())
                .englishName(english)
                .khmerName(khmer)
                .isDeleted(false)
                .province(province)
                .build();
    }
}