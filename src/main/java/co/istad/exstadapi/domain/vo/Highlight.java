package co.istad.exstadapi.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Highlight {
    private String label;
    private String value;
    private String desc;
}
