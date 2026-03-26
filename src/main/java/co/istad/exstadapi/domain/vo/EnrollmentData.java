package co.istad.exstadapi.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentData {

    private Integer id;

    private String fullNameEn;

    private String fullNameKh;

    private String gender;

    private String currentAddress;

    private String dateOfBirth;

    private String imageUrl;

    private String grade;

    private String phoneNumber;

    private String university;

    private String educationQualification;

    private Map<String, String> additionalInformation;

    private Boolean isActive;
}
