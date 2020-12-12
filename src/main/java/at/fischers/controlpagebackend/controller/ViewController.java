package at.fischers.controlpagebackend.controller;

import at.fischers.controlpagebackend.controller.dto.response.ViewListResponse;
import at.fischers.controlpagebackend.dto.FullView;
import at.fischers.controlpagebackend.service.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/view")
@RequiredArgsConstructor
public class ViewController {
    private final ViewService viewService;

    @GetMapping("all")
    public ResponseEntity<ViewListResponse> getViews() {
        return ResponseEntity.ok(new ViewListResponse(viewService.findAllBasic()));
    }

    @GetMapping("{id}")
    public ResponseEntity<FullView> getView(@PathVariable int id) {
        return ResponseEntity.ok(viewService.findById(id));
    }
}
