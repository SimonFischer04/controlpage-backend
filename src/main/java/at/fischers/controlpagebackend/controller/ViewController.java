package at.fischers.controlpagebackend.controller;

import at.fischers.controlpagebackend.dto.response.ViewListResponse;
import at.fischers.controlpagebackend.entity.View;
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
        return ResponseEntity.ok(new ViewListResponse(viewService.findAll()));
    }

    @GetMapping("{id}")
    public ResponseEntity<View> getView(@PathVariable int id) {
        return ResponseEntity.ok(viewService.findById(id));
    }
}
