package eu.fischerserver.controlpage.controlpagebackend.controller;

import eu.fischerserver.controlpage.controlpagebackend.model.dto.ViewListResponse;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.FullViewDTO;
import eu.fischerserver.controlpage.controlpagebackend.service.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/view")
@RequiredArgsConstructor
public class ViewController {
    private final ViewService viewService;

    @GetMapping("all")
    public ResponseEntity<ViewListResponse> getViewList() {
        return ResponseEntity.ok(new ViewListResponse(viewService.findAllBasic()));
    }

    @GetMapping("{id}")
    public ResponseEntity<FullViewDTO> getView(@PathVariable int id) {
        return ResponseEntity.ok(new FullViewDTO(viewService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<?> saveView(@RequestBody FullViewDTO fullView) {
        viewService.save(fullView);
        return ResponseEntity.ok().build();
    }
}
