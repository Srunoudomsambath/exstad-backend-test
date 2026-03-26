package co.istad.exstadapi.features.scholarBadge;

import co.istad.exstadapi.features.scholarBadge.dto.ScholarBadgeRequest;
import co.istad.exstadapi.features.scholarBadge.dto.ScholarBadgeRequestUpdate;
import co.istad.exstadapi.features.scholarBadge.dto.ScholarBadgeResponse;

public interface ScholarBadgeService {

    ScholarBadgeResponse createScholarBadge(ScholarBadgeRequest scholarBadgeRequest);
    ScholarBadgeResponse findByUuid(String uuid);
    ScholarBadgeResponse updateScholarBadgeByUuid(String uuid, ScholarBadgeRequestUpdate scholarBadgeRequestUpdate);
    void deleteScholarBadge(String uuid);
}
