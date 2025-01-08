package eu.fischerserver.controlpage.controlpagebackend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/api/image")
@Tag(name = "image-controller", description = "Everything related to images")
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
