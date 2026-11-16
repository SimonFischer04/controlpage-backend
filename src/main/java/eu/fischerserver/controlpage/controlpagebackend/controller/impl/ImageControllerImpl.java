package eu.fischerserver.controlpage.controlpagebackend.controller.impl;

import eu.fischerserver.controlpage.controlpagebackend.controller.ImageController;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.Image;
import eu.fischerserver.controlpage.controlpagebackend.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class ImageControllerImpl implements ImageController {
    private final ImageService imageService;

    @Override
    public ResponseEntity<?> getImage(@PathVariable int id) {
        final var image = imageService.findById(id);

        if (image == null)
            return ResponseEntity.badRequest().body(String.format("Image with id %d not found.", id));

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(image.type()))
                .body(image.imageData());
    }

    @Override
    public ResponseEntity<?> uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
        // check if uploaded file is an image
        if (!Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
            return ResponseEntity.badRequest().body("File type: " + file.getContentType() + ", is not an image");
        }

        Image img = new Image(0, file.getOriginalFilename(), file.getContentType(), file.getBytes());
        Image saved = imageService.save(img);
        return ResponseEntity.ok(new ImageController.SaveImageResponse(saved.id()));
    }
}
