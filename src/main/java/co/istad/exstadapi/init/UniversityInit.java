package co.istad.exstadapi.init;

import co.istad.exstadapi.domain.University;
import co.istad.exstadapi.features.university.UniversityRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class UniversityInit {

    private final UniversityRepository universityRepository;

    @PostConstruct
    void initUniversity() {
        if (universityRepository.count() == 0) {

            List<University> universities = List.of(
                    build("Royal University of Phnom Penh", "សាកលវិទ្យាល័យភូមិន្ទភ្នំពេញ", "RUPP"),
                    build("American University of Phnom Penh", "សាកលវិទ្យាល័យអាមេរិកាំងភ្នំពេញ", "AUPP"),
                    build("Asia Euro University", "សាកលវិទ្យាល័យអាស៊ីអ៊ឺរ៉ុប", "AEU"),
                    build("Beltei International University", "សាកលវិទ្យាល័យអន្តរជាតិបែលធី", "BELTEI"),
                    build("Build Bright University", "សាកលវិទ្យាល័យប៊ែលប្រៃ", "BBU"),
                    build("Cambodian Mekong University", "សាកលវិទ្យាល័យមេគង្គកម្ពុជា", "CMU"),
                    build("CamTech University", "សាកលវិទ្យាល័យខេមធិក", "CamTech"),
                    build("Cambodia University for Specialties", "សាកលវិទ្យាល័យឯកទេសកម្ពុជា", "CUS"),
                    build("Chea Sim University", "សាកលវិទ្យាល័យជាស៊ីម", "CSU"),
                    build("East Asia Management University", "សាកលវិទ្យាល័យគ្រប់គ្រងអាស៊ីអាគ្នេយ៍", "EAMU"),
                    build("Human Resource University", "សាកលវិទ្យាល័យធនធានមនុស្ស", "HRU"),
                    build("IIC University of Technology", "សាកលវិទ្យាល័យបច្ចេកវិទ្យា IIC", "IIC"),
                    build("International University", "សាកលវិទ្យាល័យអន្តរជាតិ", "IU"),
                    build("Khemarak University", "សាកលវិទ្យាល័យខេមរៈ", "KU"),
                    build("Limkokwing University", "សាកលវិទ្យាល័យលីមកក់វីង", "Limkokwing"),
                    build("National University of Management", "សាកលវិទ្យាល័យជាតិគ្រប់គ្រង", "NUM"),
                    build("Norton University", "សាកលវិទ្យាល័យណូតុន", "NU"),
                    build("Parth Chiet University", "សាកលវិទ្យាល័យបាតជាតិ", "PCU"),
                    build("Parnassastra University of Cambodia", "សាកលវិទ្យាល័យបារាំងសាស្ត្រ", "PUC"),
                    build("Paragon International University", "សាកលវិទ្យាល័យអន្តរជាតិផារ៉ាហ្គន", "Paragon IU"),
                    build("Phnom Penh International University", "សាកលវិទ្យាល័យភ្នំពេញអន្តរជាតិ", "PPIU"),
                    build("Preah Sihanouk Raja Buddhist University", "សាកលវិទ្យាល័យព្រះសីហនុរាជពុទ្ធសាសនា", "PSRBU"),
                    build("Royal University of Agriculture", "សាកលវិទ្យាល័យកសិកម្ម", "RUA"),
                    build("Royal University of Fine Arts", "សាកលវិទ្យាល័យសិល្បៈ", "RUFA"),
                    build("Royal University of Law and Economics", "សាកលវិទ្យាល័យច្បាប់និងសេដ្ឋកិច្ច", "RULE"),
                    build("The University of Cambodia", "សាកលវិទ្យាល័យកម្ពុជា", "UC"),
                    build("University of Battambang", "សាកលវិទ្យាល័យបាត់ដំបង", "UBB"),
                    build("University of Management and Economics", "សាកលវិទ្យាល័យគ្រប់គ្រងនិងសេដ្ឋកិច្ច", "UME"),
                    build("University of Puthisastra", "សាកលវិទ្យាល័យពុទ្ធិសាស្ត្រ", "UP"),
                    build("Western University of Cambodia", "សាកលវិទ្យាល័យវ៉េស្ទើន", "WU"),
                    build("Azedla Institute of Business", "វិទ្យាស្ថានអាជីវកម្មអាស៊ែតឡា", "Azedla"),
                    build("Cambodia Academy of Digital Technology", "វិទ្យាស្ថានបច្ចេកវិទ្យាឌីជីថលកម្ពុជា", "CADT"),
                    build("CamEd Business School", "វិទ្យាស្ថានអាជីវកម្មកែមអេដ", "CamEd"),
                    build("Economics and Finance Institute", "វិទ្យាស្ថានសេដ្ឋកិច្ចនិងហិរញ្ញវត្ថុ", "EFI"),
                    build("Institute of Technology of Cambodia", "វិទ្យាស្ថានបច្ចេកវិទ្យាកម្ពុជា", "ITC"),
                    build("ISTAD", "វិទ្យាស្ថានអាយស្តាដ", "ISTAD"),
                    build("National Institute of Business", "វិទ្យាស្ថានអាជីវកម្មជាតិ", "NIB"),
                    build("National Institute of Education", "វិទ្យាស្ថានអប់រំជាតិ", "NIE"),
                    build("National Polytechnic Institute of Cambodia", "វិទ្យាស្ថានប៉ូលីតេកនិកជាតិ", "NPIC"),
                    build("Prek Leap National College of Agriculture", "វិទ្យាស្ថានកសិកម្មជាតិព្រែកលៀប", "Prek Leap"),
                    build("Royal Academy of Cambodia", "រាជបណ្ឌិត្យសភាកម្ពុជា", "RAC"),
                    build("Setec Institute", "វិទ្យាស្ថានសេតិក", "SETEC"),
                    build("Vanda Institute", "វិទ្យាស្ថានវណ្ណដា", "Vanda"),
                    build("High School", "វិទ្យាល័យ", "High School"),
                    build("IT Step Academy", "វិទ្យាស្ថាន IT Step", "IT Step")
            );

            universityRepository.saveAll(universities);
        }
    }

    private University build(String english, String khmer, String shortName) {
        return University.builder()
                .uuid(UUID.randomUUID().toString())
                .englishName(english)
                .khmerName(khmer)
                .shortName(shortName)
                .isDeleted(false)
                .build();
    }
}
