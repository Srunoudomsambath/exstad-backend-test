package co.istad.exstadapi.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Career {

    private String uuid;

    private BigDecimal salary;

    private String company;

    private String position;

    private String interest;

    private String companyType;
}
