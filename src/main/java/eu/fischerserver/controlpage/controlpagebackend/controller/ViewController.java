package eu.fischerserver.controlpage.controlpagebackend.controller;

import eu.fischerserver.controlpage.controlpagebackend.controller.impl.ViewControllerImpl;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.action.Action;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.text.StyledText;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.BasicView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/view")
public interface ViewController {

    @GetMapping("all")
    ResponseEntity<ViewControllerImpl.ViewListResponse> getViewList();

    @GetMapping("{id}")
    ResponseEntity<ViewControllerImpl.FullViewDTO> getView(@PathVariable int id);

    @PostMapping
    ResponseEntity<?> saveView(@RequestBody ViewControllerImpl.FullViewDTO fullViewDTO);

    /*
        DTOs
     */

    record ViewListResponse(List<BasicView> views) {
    }

    record FullViewDTO(
            int id,
            String name,
            int groupId,

            List<List<FieldDTO>> fields
    ) {
    }

    record FieldDTO(
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
