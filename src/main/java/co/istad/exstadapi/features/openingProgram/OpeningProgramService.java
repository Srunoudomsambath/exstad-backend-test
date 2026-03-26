package co.istad.exstadapi.features.openingProgram;

import co.istad.exstadapi.base.BasedMessage;
import co.istad.exstadapi.domain.vo.*;
import co.istad.exstadapi.features.openingProgram.activity.dto.ActivitySetUp;
import co.istad.exstadapi.features.openingProgram.curriculum.dto.OPCurriculumSetUp;
import co.istad.exstadapi.features.openingProgram.detail.dto.DetailSetUp;
import co.istad.exstadapi.features.openingProgram.dto.OpeningProgramRequest;
import co.istad.exstadapi.features.openingProgram.dto.OpeningProgramResponse;
import co.istad.exstadapi.features.openingProgram.dto.OpeningProgramUpdate;
import co.istad.exstadapi.features.openingProgram.dto.SetUpTemplate;
import co.istad.exstadapi.features.openingProgram.learningOutcome.dto.OPLearningOutcomeSetUp;
import co.istad.exstadapi.features.openingProgram.requirement.dto.OPRequirementSetUp;
import co.istad.exstadapi.features.openingProgram.roadmap.dto.OPRoadmapSetUp;
import co.istad.exstadapi.features.openingProgram.timeline.dto.TimelineSetUp;
import co.istad.exstadapi.features.scholar.dto.ScholarResponse;

import java.util.List;

public interface OpeningProgramService {

    List<OpeningProgramResponse> getAllOpeningPrograms();
    List<OpeningProgramResponse> getAllOpeningProgramsByProgramSlug(String programSlug);
    OpeningProgramResponse getOpeningProgramByUuid(String uuid);
    OpeningProgramResponse getOpeningProgramBySlug(String slug);
    OpeningProgramResponse getOpeningProgramByTitle(String title);
    OpeningProgramResponse createOpeningProgram(OpeningProgramRequest openingProgramRequest);
    OpeningProgramResponse updateOpeningProgram(String uuid, OpeningProgramUpdate openingProgramUpdate);
    BasedMessage deleteSoftOpeningProgram(String uuid);
    BasedMessage restoreOpeningProgram(String uuid);
    BasedMessage deleteHardOpeningProgram(String uuid);
    BasedMessage activateOpeningProgram(String uuid);
    BasedMessage deactivateOpeningProgram(String uuid);
    String setUpTemplate(String uuid, SetUpTemplate setUpTemplate);
    OpeningProgramResponse setUpActivities(String uuid, List<ActivitySetUp> activitySetUps);
    OpeningProgramResponse setUpTimelines(String uuid, List<TimelineSetUp> timelineSetUps);
    OpeningProgramResponse setUpCurricula(String uuid, List<OPCurriculumSetUp> opCurriculumSetUps);
    OpeningProgramResponse setUpRoadmaps(String uuid, List<OPRoadmapSetUp> opRoadmapSetUps);
    OpeningProgramResponse setUpLearningOutcomes(String uuid, List<OPLearningOutcomeSetUp> opLearningOutcomeSetUps);
    OpeningProgramResponse setUpRequirements(String uuid, List<OPRequirementSetUp> opRequirementSetUps);
    OpeningProgramResponse setUpDetails(String uuid, List<DetailSetUp> detailSetUps);
    List<OpeningProgramResponse> getAllOpeningProgramByScholarUuid(String scholarUuid);
    List<Activity> getActivities(String uuid);
    List<Timeline> getTimelines(String uuid);
    List<Curriculum> getCurricula(String uuid);
    List<Roadmap> getRoadmaps(String uuid);
    List<LearningOutcome> getLearningOutcomes(String uuid);
    List<Requirement> getRequirements(String uuid);
    List<Detail> getDetails(String uuid);
}
