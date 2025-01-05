package eu.fischerserver.controlpage.controlpagebackend.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/api/image")
public interface ImageController {

    @GetMapping("{id}")
    ResponseEntity<?> getImage(@PathVariable int id);

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<?> uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException;

    /*
        DTOs
     */

    record SaveImageResponse(int imageId) {
    }
}
