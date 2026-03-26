package co.istad.exstadapi.init;

import co.istad.exstadapi.domain.Province;
import co.istad.exstadapi.features.province.ProvinceRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ProvinceInit {

    private final ProvinceRepository provinceRepository;

    @PostConstruct
    void initProvince() {
        if (provinceRepository.count() == 0) {

            List<Province> provinces = List.of(
                    build("Phnom Penh", "ភ្នំពេញ"),
                    build("Banteay Meanchey", "បន្ទាយមានជ័យ"),
                    build("Battambang", "បាត់ដំបង"),
                    build("Kampong Cham", "កំពង់ចាម"),
                    build("Kampong Chhnang", "កំពង់ឆ្នាំង"),
                    build("Kampong Speu", "កំពង់ស្ពឺ"),
                    build("Kampong Thom", "កំពង់ធំ"),
                    build("Kampot", "កំពត"),
                    build("Kandal", "កណ្តាល"),
                    build("Kep", "កែប"),
                    build("Koh Kong", "កោះកុង"),
                    build("Kratié", "ក្រចេះ"),
                    build("Mondulkiri", "មណ្ឌលគីរី"),
                    build("Oddar Meanchey", "ឧត្តរមានជ័យ"),
                    build("Pailin", "ប៉ៃលិន"),
                    build("Preah Vihear", "ព្រះវិហារ"),
                    build("Prey Veng", "ព្រៃវែង"),
                    build("Pursat", "ពោធិ៍សាត់"),
                    build("Ratanakiri", "រតនគីរី"),
                    build("Siem Reap", "សៀមរាប"),
                    build("Preah Sihanouk", "ព្រះសីហនុ"),
                    build("Stung Treng", "ស្ទឹងត្រែង"),
                    build("Svay Rieng", "ស្វាយរៀង"),
                    build("Takeo", "តាកែវ"),
                    build("Tbong Khmum", "ត្បូងឃ្មុំ")
            );
            provinceRepository.saveAll(provinces);
        }
    }

    private Province build(String english, String khmer) {
        return Province.builder()
                .uuid(UUID.randomUUID().toString())
                .englishName(english)
                .khmerName(khmer)
                .isDeleted(false)
                .build();
    }
}
