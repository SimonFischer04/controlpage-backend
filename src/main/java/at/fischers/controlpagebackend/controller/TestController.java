package at.fischers.controlpagebackend.controller;

import at.fischers.controlpagebackend.model.dto.TestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    @PostMapping("/1")
    public ResponseEntity<?> test(@RequestBody TestDTO test) {
        System.out.println();
        return ResponseEntity.ok().build();
    }
}
