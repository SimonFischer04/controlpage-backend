package at.fischers.controlpagebackend.controller;

import at.fischers.controlpagebackend.dto.Field;
import at.fischers.controlpagebackend.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/field")
@RequiredArgsConstructor
public class FieldController {
    private final FieldService fieldService;

    @PostMapping
    public ResponseEntity<?> saveField(@RequestBody Field field) {
        fieldService.save(field);
        return ResponseEntity.ok().build();
    }
}
