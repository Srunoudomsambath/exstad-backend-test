package co.istad.exstadapi.features.program.faq;

import co.istad.exstadapi.domain.vo.Faq;
import co.istad.exstadapi.features.program.ProgramService;
import co.istad.exstadapi.features.program.dto.ProgramResponse;
import co.istad.exstadapi.features.program.faq.dto.FaqSetUp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/programs")
public class FaqController {
    private final ProgramService programService;

    @PutMapping("/{uuid}/faqs")
    public ResponseEntity<ProgramResponse> updateFaqs(
            @PathVariable String uuid,
            @RequestBody @Valid List<@Valid FaqSetUp> faqSetUps
    ) {
        return ResponseEntity.ok(programService.setUpFaqs(uuid, faqSetUps));
    }

    @GetMapping("/{uuid}/faqs")
    public ResponseEntity<List<Faq>> getFaqs(@PathVariable String uuid) {
        return ResponseEntity.ok(programService.getFaqs(uuid));
    }
}
