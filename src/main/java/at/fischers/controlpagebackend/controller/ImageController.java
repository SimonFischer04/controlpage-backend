package at.fischers.controlpagebackend.controller;

import at.fischers.controlpagebackend.dto.Image;
import at.fischers.controlpagebackend.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping()
    public ResponseEntity<Image> uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
        Image img = new Image(0, file.getOriginalFilename(), file.getContentType(), file.getBytes());
        Image saved = imageService.save(img);
        return ResponseEntity.ok(saved);
    }
}
