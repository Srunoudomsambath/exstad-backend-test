package co.istad.exstadapi.features.scholar.dto;

import lombok.Builder;

@Builder
public record SocialLinkResponse (
        String uuid,
        String title,
        String type,
        String link,
        Boolean isActive
){
}
