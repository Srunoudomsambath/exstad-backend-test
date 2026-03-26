package co.istad.exstadapi.features.program;

import co.istad.exstadapi.base.BasedMessage;
import co.istad.exstadapi.domain.OpeningProgram;
import co.istad.exstadapi.domain.Program;
import co.istad.exstadapi.domain.vo.*;
import co.istad.exstadapi.features.openingProgram.OpeningProgramRepository;
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
import co.istad.exstadapi.mapper.ProgramMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {
    private final ProgramRepository programRepository;
    private final ProgramMapper programMapper;
    private final OpeningProgramRepository openingProgramRepository;

    @Override
    public List<ProgramResponse> getAllPrograms() {
        List<Program> programs = programRepository.findAllByIsDeletedFalse();
        return programs
                .stream()
                .map(programMapper::toProgramResponse)
                .toList();
    }

    @Override
    public ProgramResponse getProgramByUuid(String uuid) {
        Program program = programRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Program not found"
                ));
        return programMapper.toProgramResponse(program);
    }

    @Override
    public ProgramResponse getProgramBySlug(String slug) {
        Program program = programRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Program not found"
                ));
        return programMapper.toProgramResponse(program);
    }

    @Override
    public ProgramResponse getProgramByTitle(String title) {
        Program program = programRepository.findByTitleIgnoreCase(title)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Program not found"
                ));
        return programMapper.toProgramResponse(program);
    }
    @Override
    public ProgramResponse getProgramByOpeningProgramUuid(String openingProgramUuid) {
        OpeningProgram openingProgram = openingProgramRepository.findByUuid(openingProgramUuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opening Program not found"));
        Program program = programRepository.findByOpeningPrograms(openingProgram)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Program not found"));
        return programMapper.toProgramResponse(program);
    }


    @Override
    public ProgramResponse createProgram(ProgramRequest programRequest) {
        if (programRepository.existsBySlug(programRequest.slug())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Slug already exists");
        }
        if (programRepository.existsByTitleIgnoreCase(programRequest.title())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Title already exists");
        }
        Program program = programMapper.fromProgramRequest(programRequest);
        program.setHighlights(null);
        program.setProgramOverviews(null);
        program.setRoadmaps(null);
        program.setFaqs(null);
        program.setRequirements(null);
        program.setLearningOutcomes(null);
        program.setCurricula(null);
        program.setUuid(UUID.randomUUID().toString());
        program.setIsDeleted(false);
        program = programRepository.save(program);
        return programMapper.toProgramResponse(program);
    }

    @Override
    public ProgramResponse updateProgram(String uuid, ProgramUpdate programUpdate) {
        Program program = programRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Program not found"
                ));
        programMapper.toProgramUpdate(programUpdate, program);
        program = programRepository.save(program);
        return programMapper.toProgramResponse(program);
    }

    @Transactional
    @Override
    public BasedMessage deleteProgram(String uuid) {
        if (!programRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Program not found"
            );
        }
        programRepository.softDeleteByUuid(uuid);
        return new BasedMessage("Program deleted successfully");
    }

    @Transactional
    @Override
    public BasedMessage restoreProgram(String uuid) {
        if (!programRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Program not found"
            );
        }
        programRepository.undeleteByUuid(uuid);
        return new BasedMessage("Program restored successfully");
    }

    @Transactional
    @Override
    public BasedMessage hardDeleteProgram(String uuid) {
        if (!programRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Program not found"
            );
        }
        programRepository.deleteByUuid(uuid);
        return new BasedMessage("Program hard deleted successfully");
    }

    @Override
    public ProgramResponse setUpHighlights(String uuid, List<HighlightSetUp> highlightSetUps) {
        Program program = programRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Program not found"));
        List<Highlight> highlights = highlightSetUps.stream()
                .map(highlightSetUp -> {
                    Highlight highlight = new Highlight();
                    highlight.setLabel(highlightSetUp.label());
                    highlight.setValue(highlightSetUp.value());
                    highlight.setDesc(highlightSetUp.desc());
                    return highlight;
                })
                .toList();
        program.setHighlights(highlights);
        Program savedProgram = programRepository.save(program);

        return programMapper.toProgramResponse(savedProgram);
    }


    @Override
    public ProgramResponse setUpProgramOverviews(String uuid, List<ProgramOverviewSetUp> programOverviewSetUps) {
        Program program = programRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Program not found"));
        List<ProgramOverview> programOverviews = programOverviewSetUps.stream()
                .map(programOverviewSetUp -> {
                    ProgramOverview programOverview = new ProgramOverview();
                    programOverview.setTitle(programOverviewSetUp.title());
                    programOverview.setDescription(programOverviewSetUp.description());
                    return programOverview;
                })
                .toList();
        program.setProgramOverviews(programOverviews);
        program = programRepository.save(program);
        return programMapper.toProgramResponse(program);
    }

    @Override
    public ProgramResponse setUpRoadmaps(String uuid, List<RoadmapSetUp> roadmapSetUps) {
        Program program = programRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Program not found"));
        List<Roadmap> roadmaps = roadmapSetUps.stream()
                .map(roadmapSetUp -> {
                    Roadmap roadmap = new Roadmap();
                    roadmap.setEdges(roadmapSetUp.edges().stream()
                            .map(edgeSetUp -> {
                                Roadmap.Edge edge = new Roadmap.Edge();
                                edge.setId(edgeSetUp.id());
                                edge.setAnimated(edgeSetUp.animated());
                                edge.setSource(edgeSetUp.source());
                                edge.setTarget(edgeSetUp.target());
                                return edge;
                            })
                            .toList());
                    roadmap.setNodes(roadmapSetUp.nodes().stream()
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
                            })
                            .toList());
                    return roadmap;
                })
                .toList();
        program.setRoadmaps(roadmaps);
        program = programRepository.save(program);
        return programMapper.toProgramResponse(program);
    }


    @Override
    public ProgramResponse setUpFaqs(String uuid, List<FaqSetUp> faqSetUps) {
        Program program = programRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Program not found"));
        List<Faq> faqs = faqSetUps.stream()
                .map(faqSetUp -> new Faq(
                        faqSetUp.faq().stream()
                                .map(faqSection -> new FaqSection(
                                        faqSection.title(),
                                        faqSection.faqs().stream()
                                                .map(faqItem -> new FaqItem(
                                                        UUID.randomUUID().toString(),
                                                        faqItem.question(),
                                                        faqItem.answer()
                                                ))
                                                .toList()
                                ))
                                .toList()
                ))
                .toList();
        program.setFaqs(faqs);
        program = programRepository.save(program);
        return programMapper.toProgramResponse(program);
    }

    @Override
    public ProgramResponse setUpRequirements(String uuid, List<RequirementSetUp> requirementSetUps) {
        Program program = programRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Program not found"));
        List<Requirement> requirements = requirementSetUps.stream()
                .map(requirementSetUp -> {
                    Requirement requirement = new Requirement();
                    requirement.setTitle(requirementSetUp.title());
                    requirement.setSubtitle(requirementSetUp.subtitle());
                    requirement.setDescription(requirementSetUp.description());
                    return requirement;
                }).toList();
        program.setRequirements(requirements);
        program = programRepository.save(program);
        return programMapper.toProgramResponse(program);
    }

    @Override
    public ProgramResponse setUpLearningOutcomes(String uuid, List<LearningOutcomeSetUp> learningOutcomeSetUps) {
        Program program = programRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Program not found"));
        List<LearningOutcome> learningOutcomes = learningOutcomeSetUps.stream()
                .map(learningOutcomeSetUp -> {
                    LearningOutcome learningOutcome = new LearningOutcome();
                    learningOutcome.setTitle(learningOutcomeSetUp.title());
                    learningOutcome.setDescription(learningOutcomeSetUp.description());
                    learningOutcome.setSubtitle(learningOutcomeSetUp.subtitle());
                    return learningOutcome;
                }).toList();
        program.setLearningOutcomes(learningOutcomes);
        program = programRepository.save(program);
        return programMapper.toProgramResponse(program);
    }


    @Override
    public ProgramResponse setUpCurricula(String uuid, List<CurriculumSetUp> curriculumSetUps) {
        Program program = programRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Program not found"));
        List<Curriculum> curriculumSet = curriculumSetUps.stream().map(curriculumSetUp -> {
            Curriculum curriculum = new Curriculum();
            curriculum.setUuid(UUID.randomUUID().toString());
            curriculum.setTitle(curriculumSetUp.title());
            curriculum.setSubtitle(curriculumSetUp.subtitle());
            curriculum.setOrder(curriculumSetUp.order());
            curriculum.setDescription(curriculumSetUp.description());
            return curriculum;
        }).toList();
        program.setCurricula(curriculumSet);
        program = programRepository.save(program);
        return programMapper.toProgramResponse(program);
    }

    @Override
    public List<Highlight> getHighlights(String uuid) {
        Program program = programRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Program not found"));
        return program.getHighlights();
    }

    @Override
    public List<ProgramOverview> getProgramOverviews(String uuid) {
        Program program = programRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Program not found"));
        return program.getProgramOverviews();
    }

    @Override
    public List<Roadmap> getRoadmaps(String uuid) {
        Program program = programRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Program not found"));
        return program.getRoadmaps();
    }

    @Override
    public List<Faq> getFaqs(String uuid) {
        Program program = programRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Program not found"));
        return program.getFaqs();
    }

    @Override
    public List<Requirement> getRequirements(String uuid) {
        Program program = programRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Program not found"));
        return program.getRequirements();
    }

    @Override
    public List<LearningOutcome> getLearningOutcomes(String uuid) {
        Program program = programRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Program not found"));
        return program.getLearningOutcomes();
    }

    @Override
    public List<Curriculum> getCurricula(String uuid) {
        Program program = programRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Program not found"));
        return program.getCurricula();
    }

    @Override
    public List<Technology> getTechnologies(String uuid) {
        Program program = programRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Program not found"));
        return program.getTechnologies();
    }

    @Override
    public ProgramResponse setUpTechnologies(String uuid, List<TechnologySetup> technologySetups) {
        Program program = programRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Program not found"));
        List<Technology> technologies = technologySetups.stream()
                .map(technologySetup -> {
                    Technology technology = new Technology();
                    technology.setTitle(technologySetup.title());
                    technology.setDescription(technologySetup.description());
                    technology.setImage(technologySetup.image());
                    return technology;
                })
                .toList();
        program.setTechnologies(technologies);
        program = programRepository.save(program);
        return programMapper.toProgramResponse(program);
    }


}
