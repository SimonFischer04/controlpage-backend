package at.fischers.controlpagebackend.controller;

import at.fischers.controlpagebackend.dto.Image;
import at.fischers.controlpagebackend.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
        return ResponseEntity.ok(saved);
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(IllegalArgumentException.class)
//    public Map<String, String> handleIllegalArgumentExceptions(IllegalArgumentException ex) {
//        return new HashMap<>();
//    }
}
