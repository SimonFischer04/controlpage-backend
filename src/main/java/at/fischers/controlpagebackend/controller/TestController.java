package at.fischers.controlpagebackend.controller;

import at.fischers.controlpagebackend.dto.Field;
import at.fischers.controlpagebackend.dto.TestDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    @PostMapping(value = "/1", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> test(@RequestBody TestDTO test) {
        System.out.println();
        return ResponseEntity.ok().build();
    }
}
