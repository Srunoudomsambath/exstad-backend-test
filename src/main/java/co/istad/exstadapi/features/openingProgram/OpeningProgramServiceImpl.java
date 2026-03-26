package co.istad.exstadapi.features.openingProgram;

import co.istad.exstadapi.base.BasedMessage;
import co.istad.exstadapi.domain.*;
import co.istad.exstadapi.domain.Class;
import co.istad.exstadapi.domain.vo.*;
import co.istad.exstadapi.features.classes.ClassRepository;
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
import co.istad.exstadapi.features.program.ProgramRepository;
import co.istad.exstadapi.features.scholar.ScholarRepository;
import co.istad.exstadapi.features.scholar.ScholarService;
import co.istad.exstadapi.features.scholar.dto.ScholarResponse;
import co.istad.exstadapi.features.scholarClass.ScholarClassRepository;
import co.istad.exstadapi.features.scholarClass.ScholarClassService;
import co.istad.exstadapi.mapper.OpeningProgramMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OpeningProgramServiceImpl implements OpeningProgramService {
    private final OpeningProgramRepository openingProgramRepository;
    private final OpeningProgramMapper openingProgramMapper;
    private final ScholarRepository scholarRepository;
    private final ScholarClassRepository scholarClassRepository;
    private final ProgramRepository programRepository;

    @Override
    public List<OpeningProgramResponse> getAllOpeningPrograms() {
        List<OpeningProgram> openingPrograms = openingProgramRepository.findAllByIsDeletedFalse();
        return openingPrograms.
                stream()
                .map(openingProgramMapper::toOpeningProgramResponse)
                .toList();
    }

    @Override
    public List<OpeningProgramResponse> getAllOpeningProgramsByProgramSlug(String programSlug) {
        Program program = programRepository.findBySlug(programSlug).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Program not found")
        );
        return openingProgramRepository.findAllByProgramAndIsDeletedFalse(program).
                stream()
                .map(openingProgramMapper::toOpeningProgramResponse)
                .toList();
    }

    @Override
    public OpeningProgramResponse getOpeningProgramByUuid(String uuid) {
        OpeningProgram openingProgram = openingProgramRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program not found"));
        return openingProgramMapper.toOpeningProgramResponse(openingProgram);
    }

    @Override
    public OpeningProgramResponse getOpeningProgramBySlug(String slug) {
        OpeningProgram openingProgram = openingProgramRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program not found"));
        return openingProgramMapper.toOpeningProgramResponse(openingProgram);
    }

    @Override
    public OpeningProgramResponse getOpeningProgramByTitle(String title) {
        OpeningProgram openingProgram = openingProgramRepository.findByTitleIgnoreCase(title)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program not found"));
        return openingProgramMapper.toOpeningProgramResponse(openingProgram);
    }

    @Override
    public OpeningProgramResponse createOpeningProgram(OpeningProgramRequest openingProgramRequest) {
        OpeningProgram openingProgram = openingProgramMapper.fromOpeningProgramRequest(openingProgramRequest);
        openingProgram.setTemplates(List.of());
        openingProgram.setAchievements(null);
        openingProgram.setCurricula(null);
        openingProgram.setDetails(null);
        openingProgram.setActivities(null);
        openingProgram.setTimelines(null);
        openingProgram.setLearningOutcomes(null);
        openingProgram.setRequirements(null);

        openingProgram.setIsActive(true);
        openingProgram.setUuid(UUID.randomUUID().toString());
        openingProgram.setIsDeleted(false);
        openingProgram = openingProgramRepository.save(openingProgram);
        return openingProgramMapper.toOpeningProgramResponse(openingProgram);
    }

    @Override
    public OpeningProgramResponse updateOpeningProgram(String uuid, OpeningProgramUpdate openingProgramUpdate) {
        OpeningProgram openingProgram = openingProgramRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program not found"));
        openingProgramMapper.updateOpeningProgramFromRequest(openingProgramUpdate, openingProgram);
        openingProgram = openingProgramRepository.save(openingProgram);
        return openingProgramMapper.toOpeningProgramResponse(openingProgram);
    }

    @Transactional
    @Override
    public BasedMessage deleteSoftOpeningProgram(String uuid) {
        if (!openingProgramRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program not found");
        }
        openingProgramRepository.softDeleteByUuid(uuid);
        return new BasedMessage("Opening Program soft deleted successfully");
    }

    @Transactional
    @Override
    public BasedMessage deleteHardOpeningProgram(String uuid) {
        if (!openingProgramRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program not found");
        }
        openingProgramRepository.deleteByUuid(uuid);
        return new BasedMessage("Opening Program hard deleted successfully");
    }

    @Transactional
    @Override
    public BasedMessage activateOpeningProgram(String uuid) {
        if (!openingProgramRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program not found");
        }
        openingProgramRepository.activateByUuid(uuid);
        return new BasedMessage("Opening Program activated successfully");
    }

    @Transactional
    @Override
    public BasedMessage deactivateOpeningProgram(String uuid) {
        if (!openingProgramRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program not found");
        }
        openingProgramRepository.deactivateByUuid(uuid);
        return new BasedMessage("Opening Program deactivated successfully");
    }

    @Override
    public String setUpTemplate(String uuid, SetUpTemplate setUpTemplate) {
        OpeningProgram openingProgram = openingProgramRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program not found"));
        openingProgram.getTemplates().add(setUpTemplate.template());
        openingProgramRepository.save(openingProgram);
        return setUpTemplate.template();
    }

    @Override
    public OpeningProgramResponse setUpActivities(String uuid, List<ActivitySetUp> activitySetUps) {
        OpeningProgram openingProgram = openingProgramRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program not found"));
        List<Activity> activities = activitySetUps.stream()
                        .map(activitySetUp -> {
                            Activity activity = new Activity();
                            activity.setTitle(activitySetUp.title());
                            activity.setDescription(activitySetUp.description());
                            activity.setImage(activitySetUp.image());
                            return activity;
                        }).toList();
        openingProgram.setActivities(activities);
        openingProgram = openingProgramRepository.save(openingProgram);
        return openingProgramMapper.toOpeningProgramResponse(openingProgram);
    }

    @Override
    public OpeningProgramResponse setUpTimelines(String uuid, List<TimelineSetUp> timelineSetUps) {
        OpeningProgram openingProgram = openingProgramRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program not found"));
        List<Timeline> timelines = timelineSetUps.stream()
                .map(timelineSetUp -> {
                    Timeline timeline = new Timeline();
                    timeline.setTitle(timelineSetUp.title());
                    timeline.setStartDate(timelineSetUp.startDate());
                    timeline.setEndDate(timelineSetUp.endDate());
                    return timeline;
                }).toList();
        openingProgram.setTimelines(timelines);
        openingProgram = openingProgramRepository.save(openingProgram);
        return openingProgramMapper.toOpeningProgramResponse(openingProgram);
    }

    @Override
    public OpeningProgramResponse setUpCurricula(String uuid, List<OPCurriculumSetUp> opCurriculumSetUps) {
        OpeningProgram openingProgram = openingProgramRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program not found"));
        List<Curriculum> curricula = opCurriculumSetUps.stream()
                .map(opCurriculumSetUp -> {
                    Curriculum curriculum = new Curriculum();
                    curriculum.setOrder(opCurriculumSetUp.order());
                    curriculum.setTitle(opCurriculumSetUp.title());
                    curriculum.setSubtitle(opCurriculumSetUp.subtitle());
                    curriculum.setDescription(opCurriculumSetUp.description());
                    return curriculum;
                }).toList();
        openingProgram.setCurricula(curricula);
        openingProgram = openingProgramRepository.save(openingProgram);
        return openingProgramMapper.toOpeningProgramResponse(openingProgram);
    }

    @Override
    public OpeningProgramResponse setUpRoadmaps(String uuid, List<OPRoadmapSetUp> opRoadmapSetUps) {
        OpeningProgram openingProgram = openingProgramRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program not found"));
        List<Roadmap> roadmaps = opRoadmapSetUps.stream()
                        .map(opRoadmapSetUp -> {
                            Roadmap roadmap = new Roadmap();
                            roadmap.setEdges(opRoadmapSetUp.edges().stream()
                                    .map(edgeSetUp -> {
                                        Roadmap.Edge edge = new Roadmap.Edge();
                                        edge.setId(edgeSetUp.id());
                                        edge.setSource(edgeSetUp.source());
                                        edge.setTarget(edgeSetUp.target());
                                        edge.setAnimated(edgeSetUp.animated());
                                        return edge;
                                    }).toList());
                            roadmap.setNodes(opRoadmapSetUp.nodes().stream()
                                    .map(nodeSetUp -> {
                                        Roadmap.Node node = new Roadmap.Node();
                                        node.setType(nodeSetUp.type());
                                        node.setPosition(new Roadmap.Position(
                                                nodeSetUp.position().x(),
                                                nodeSetUp.position().y()
                                        ));
                                        node.setData(new Roadmap.NodeData(
                                                nodeSetUp.data().label(),
                                                nodeSetUp.data().description()
                                        ));
                                        return node;
                                    }).toList());
                            return roadmap;
                        }).toList();
        openingProgram.setRoadmaps(roadmaps);
        openingProgram = openingProgramRepository.save(openingProgram);
        return openingProgramMapper.toOpeningProgramResponse(openingProgram);
    }

    @Override
    public OpeningProgramResponse setUpLearningOutcomes(String uuid, List<OPLearningOutcomeSetUp> opLearningOutcomeSetUps) {
        OpeningProgram openingProgram = openingProgramRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program not found"));
        List<LearningOutcome> learningOutcomes = opLearningOutcomeSetUps.stream()
                .map(opLearningOutcomeSetUp -> {
                    LearningOutcome learningOutcome = new LearningOutcome();
                    learningOutcome.setTitle(opLearningOutcomeSetUp.title());
                    learningOutcome.setSubtitle(opLearningOutcomeSetUp.subtitle());
                    learningOutcome.setDescription(opLearningOutcomeSetUp.description());
                    return learningOutcome;
                }).toList();
        openingProgram.setLearningOutcomes(learningOutcomes);
        openingProgram = openingProgramRepository.save(openingProgram);
        return openingProgramMapper.toOpeningProgramResponse(openingProgram);
    }

    @Override
    public OpeningProgramResponse setUpRequirements(String uuid, List<OPRequirementSetUp> opRequirementSetUps) {
        OpeningProgram openingProgram = openingProgramRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program not found"));
        List<Requirement> requirements = opRequirementSetUps.stream()
                .map(opRequirementSetUp -> {
                    Requirement requirement = new Requirement();
                    requirement.setTitle(opRequirementSetUp.title());
                    requirement.setSubtitle(opRequirementSetUp.subtitle());
                    requirement.setDescription(opRequirementSetUp.description());
                    return requirement;
                }).toList();
        openingProgram.setRequirements(requirements);
        openingProgram = openingProgramRepository.save(openingProgram);
        return openingProgramMapper.toOpeningProgramResponse(openingProgram);
    }

    @Override
    public OpeningProgramResponse setUpDetails(String uuid, List<DetailSetUp> detailSetUps) {
        OpeningProgram openingProgram = openingProgramRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program not found"));
        List<Detail> details = detailSetUps.stream()
                .map(detailSetUp -> {
                    Detail detail = new Detail();
                    detail.setTitle(detailSetUp.title());
                    detail.setDescription(detailSetUp.description());
                    detail.setIsActive(detailSetUp.isActive());
                    return detail;
                }).toList();
        openingProgram.setDetails(details);
        openingProgram = openingProgramRepository.save(openingProgram);
        return openingProgramMapper.toOpeningProgramResponse(openingProgram);
    }

    @Override
    public List<OpeningProgramResponse> getAllOpeningProgramByScholarUuid(String scholarUuid) {
        Scholar scholar = scholarRepository.findByUuid(scholarUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Scholar not found"));
        List<ScholarClass> scholarClasses = scholarClassRepository.findAllByScholar(scholar)
                .stream()
                .filter(scholarClass -> !scholarClass.getIsDeleted())
                .toList();
        List<Class> classes = scholarClasses.stream()
                .map(ScholarClass::get_class)
                .toList();
        List<OpeningProgram> openingPrograms = classes.stream()
                .map(Class::getOpeningProgram)
                .toList();
        return openingPrograms.stream()
                .map(openingProgramMapper::toOpeningProgramResponse)
                .toList();
    }

    @Override
    public List<Activity> getActivities(String uuid) {
        OpeningProgram openingProgram = openingProgramRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program not found"));
        return openingProgram.getActivities();
    }

    @Override
    public List<Timeline> getTimelines(String uuid) {
        OpeningProgram openingProgram = openingProgramRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program not found"));
        return openingProgram.getTimelines();
    }

    @Override
    public List<Curriculum> getCurricula(String uuid) {
        OpeningProgram openingProgram = openingProgramRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program not found"));
        return openingProgram.getCurricula();
    }

    @Override
    public List<Roadmap> getRoadmaps(String uuid) {
        OpeningProgram openingProgram = openingProgramRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program not found"));
        return openingProgram.getRoadmaps();
    }

    @Override
    public List<LearningOutcome> getLearningOutcomes(String uuid) {
        OpeningProgram openingProgram = openingProgramRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program not found"));
        return openingProgram.getLearningOutcomes();
    }

    @Override
    public List<Requirement> getRequirements(String uuid) {
        OpeningProgram openingProgram = openingProgramRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program not found"));
        return openingProgram.getRequirements();
    }

    @Override
    public List<Detail> getDetails(String uuid) {
        OpeningProgram openingProgram = openingProgramRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program not found"));
        return openingProgram.getDetails();
    }

    @Transactional
    @Override
    public BasedMessage restoreOpeningProgram(String uuid) {
        if (!openingProgramRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program not found");
        }
        openingProgramRepository.restoreByUuid(uuid);
        return new BasedMessage("Opening Program restored successfully");
    }
}
