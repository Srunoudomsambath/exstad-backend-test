package co.istad.exstadapi.features.program;

import co.istad.exstadapi.base.BasedMessage;
import co.istad.exstadapi.domain.vo.*;
import co.istad.exstadapi.features.program.Technology.dto.TechnologySetup;
import co.istad.exstadapi.features.program.curriculum.dto.CurriculumSetUp;
import co.istad.exstadapi.features.program.dto.ProgramRequest;
import co.istad.exstadapi.features.program.dto.ProgramResponse;
import co.istad.exstadapi.features.program.dto.ProgramUpdate;
import co.istad.exstadapi.features.program.faq.dto.FaqSetUp;
import co.istad.exstadapi.features.program.highlight.dto.HighlightSetUp;
import co.istad.exstadapi.features.program.learningOutcome.dto.LearningOutcomeSetUp;
import co.istad.exstadapi.features.program.programOverview.dto.ProgramOverviewSetUp;
import co.istad.exstadapi.features.program.requirement.dto.RequirementSetUp;
import co.istad.exstadapi.features.program.roadmap.dto.RoadmapSetUp;

import java.util.List;

public interface ProgramService {
    List<ProgramResponse> getAllPrograms();
    ProgramResponse getProgramByUuid(String uuid);
    ProgramResponse getProgramBySlug(String slug);
    ProgramResponse getProgramByTitle(String title);
    ProgramResponse createProgram(ProgramRequest programRequest);
    ProgramResponse updateProgram(String uuid, ProgramUpdate programUpdate);
    BasedMessage deleteProgram(String uuid);
    BasedMessage restoreProgram(String uuid);
    BasedMessage hardDeleteProgram(String uuid);
    ProgramResponse getProgramByOpeningProgramUuid(String openingProgramUuid);
    ProgramResponse setUpHighlights(String uuid, List<HighlightSetUp> highlightSetUps);
    ProgramResponse setUpProgramOverviews(String uuid, List<ProgramOverviewSetUp> programOverviewSetUps);
    ProgramResponse setUpRoadmaps(String uuid, List<RoadmapSetUp> roadmapSetUps);
    ProgramResponse setUpFaqs(String uuid, List<FaqSetUp> faqSetUps);
    ProgramResponse setUpRequirements(String uuid, List<RequirementSetUp> requirementSetUps);
    ProgramResponse setUpLearningOutcomes(String uuid, List<LearningOutcomeSetUp> learningOutcomeSetUps);
    ProgramResponse setUpCurricula(String uuid, List<CurriculumSetUp> curriculumSetUps);
    List<Highlight> getHighlights(String uuid);
    ProgramResponse setUpTechnologies (String uuid, List<TechnologySetup> technologySetUps);
    List<ProgramOverview> getProgramOverviews(String uuid);
    List<Roadmap> getRoadmaps(String uuid);
    List<Faq> getFaqs(String uuid);
    List<Requirement> getRequirements(String uuid);
    List<LearningOutcome> getLearningOutcomes(String uuid);
    List<Curriculum> getCurricula(String uuid);
    List<Technology> getTechnologies(String uuid);

//    ProgramResponse getProgramByOpeningProgramUuid(String openingProgramUuid);
}
