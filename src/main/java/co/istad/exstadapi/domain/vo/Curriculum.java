package co.istad.exstadapi.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Curriculum {
    private String uuid = UUID.randomUUID().toString();
    private Integer order;
    private String title;
    private String subtitle;
    private List<String> description;
}
