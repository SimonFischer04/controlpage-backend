package eu.fischerserver.controlpage.controlpagebackend.controller;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.action.Action;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.text.StyledText;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.BasicView;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.FullView;
import eu.fischerserver.controlpage.controlpagebackend.service.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/view")
@RequiredArgsConstructor
public class ViewController {
    private final ViewService viewService;
    private final ConversionService conversionService;

    @GetMapping("all")
    public ResponseEntity<ViewListResponse> getViewList() {
        return ResponseEntity.ok(new ViewListResponse(viewService.findAllBasic()));
    }

    @GetMapping("{id}")
    public ResponseEntity<FullViewDTO> getView(@PathVariable int id) {
        final var fullView = viewService.findById(id);
        if (fullView == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(conversionService.convert(fullView, FullViewDTO.class));
    }

    @PostMapping
    public ResponseEntity<?> saveView(@RequestBody FullViewDTO fullViewDTO) {
        final var fullView = conversionService.convert(fullViewDTO, FullView.class);
        viewService.save(fullView);
        return ResponseEntity.ok().build();

    }

    public record ViewListResponse(List<BasicView> views) {
    }

    public record FullViewDTO(
            int id,
            String name,
            int groupId,

            List<List<FieldDTO>> fields
    ) {
    }

    public record FieldDTO(
            int id,

            int viewId,

            Action action,

            StyledText title,
            String description,

            int backgroundId,

            int rowspan,
            int colspan
    ) {
    }
}
