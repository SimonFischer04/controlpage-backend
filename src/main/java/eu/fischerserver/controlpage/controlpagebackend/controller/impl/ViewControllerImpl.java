package eu.fischerserver.controlpage.controlpagebackend.controller.impl;

import eu.fischerserver.controlpage.controlpagebackend.controller.ViewController;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.FullView;
import eu.fischerserver.controlpage.controlpagebackend.service.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ViewControllerImpl implements ViewController {
    private final ViewService viewService;
    private final ConversionService conversionService;

    @Override
    public ResponseEntity<ViewListResponse> getViewList() {
        return ResponseEntity.ok(new ViewListResponse(viewService.findAllBasic()));
    }

    @Override
    public ResponseEntity<FullViewDTO> getView(@PathVariable int id) {
        final var fullView = viewService.findById(id);
        if (fullView == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(conversionService.convert(fullView, FullViewDTO.class));
    }

    @Override
    public ResponseEntity<?> saveView(@RequestBody FullViewDTO fullViewDTO) {
        final var fullView = conversionService.convert(fullViewDTO, FullView.class);
        viewService.save(fullView);
        return ResponseEntity.ok().build();
    }
}
