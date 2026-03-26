package co.istad.exstadapi.domain.vo;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Timeline {

    private String uuid = UUID.randomUUID().toString();

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;
}
