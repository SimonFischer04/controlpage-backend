package at.fischers.controlpagebackend.controller;

import at.fischers.controlpagebackend.controller.dto.response.ViewListResponse;
import at.fischers.controlpagebackend.dto.view.BasicView;
import at.fischers.controlpagebackend.dto.view.FullView;
import at.fischers.controlpagebackend.dto.view.FullViewDTO;
import at.fischers.controlpagebackend.service.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/view")
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
