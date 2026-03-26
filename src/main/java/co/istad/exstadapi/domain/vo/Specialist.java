package co.istad.exstadapi.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Specialist {

    private String uuid;

    private String country;

    private String specialist;

    private String universityName;

    private String about;

    private String degreeType;
}
