package co.istad.exstadapi.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SocialLink {

    private String uuid = UUID.randomUUID().toString();

    private String link;

    private String type;

    private String title;

    private Boolean isDeleted;

    private Boolean isActive;
}
