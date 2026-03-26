package co.istad.exstadapi.features.badge;

import co.istad.exstadapi.base.BasedMessage;
import co.istad.exstadapi.features.badge.dto.BadgeRequest;
import co.istad.exstadapi.features.badge.dto.BadgeResponse;
import co.istad.exstadapi.features.badge.dto.BadgeUpdate;

import java.util.List;

public interface BadgeService {
    List<BadgeResponse> getAllBadges();
    BadgeResponse getBadgeByUuid(String uuid);
    BadgeResponse createBadge(BadgeRequest badgeRequest);
    BadgeResponse updateBadge(String uuid, BadgeUpdate badgeUpdate);
    BasedMessage deleteBadge(String uuid);
    BasedMessage restoreBadge(String uuid);
    BasedMessage hardDeleteBadge(String uuid);
}
