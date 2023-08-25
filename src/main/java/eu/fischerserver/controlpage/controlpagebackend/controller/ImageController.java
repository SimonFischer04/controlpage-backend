package eu.fischerserver.controlpage.controlpagebackend.controller;

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
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping()
    public ResponseEntity<?> uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
        // check if uploaded file is an image
        if (!Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
            return ResponseEntity.badRequest().body("File type: " + file.getContentType() + ", is not an image");
        }

        Image img = new Image(0, file.getOriginalFilename(), file.getContentType(), file.getBytes());
        Image saved = imageService.save(img);
        return ResponseEntity.ok(new SaveImageResponse(saved.getId()));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getImage(@PathVariable int id) {
        final var image = imageService.findById(id);

        if (image == null)
            return ResponseEntity.badRequest().body(String.format("Image with id %d not found.", id));

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(image.getType()))
                .body(image.getImageData());
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(IllegalArgumentException.class)
//    public Map<String, String> handleIllegalArgumentExceptions(IllegalArgumentException ex) {
//        return new HashMap<>();
//    }

    public record SaveImageResponse(
            int imageId
    ) {
    }
}
