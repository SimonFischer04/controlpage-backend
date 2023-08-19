package at.fischers.controlpagebackend.integration.controller;

import at.fischers.controlpagebackend.controller.ImageController;
import at.fischers.controlpagebackend.model.domain.Image;
import at.fischers.controlpagebackend.service.ImageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ImageController.class)
public class ImageControllerIntTest {
    @MockBean
    private ImageService mockService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    /**
     * Tests an image upload with everything valid.
     * Checks if the ImageService(mocked here) is called.
     * (Service is tested separately)
     */
    @Test
    public void testValidUpload() throws Exception {
        // Arrange
        Image expectedResultImage = new Image((int) (Math.random() * 100), "testImage.png", "image/png", new byte[]{127, 42, 5});
        Mockito.when(mockService.save(new Image(0, "testImage.png", "image/png", new byte[]{127, 42, 5}))).thenReturn(expectedResultImage);
        MockMultipartFile file = new MockMultipartFile("imageFile", "testImage.png", "image/png", new byte[]{127, 42, 5});

        // Act
        mvc.perform(MockMvcRequestBuilders.multipart("/api/image")
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA))

                // Assert
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResultImage)))
        ;

        // Assert2 - verify that the service was called exactly once
        Mockito.verify(mockService).save(Mockito.any());
    }

    /**
     * Test upload of in invalid/text-file (only images allowed!)
     */
    @Test
    public void testUploadTextFile() throws Exception {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("imageFile", "testFile.txt", "text/plain", new byte[]{127, 127, 127});

        // Act
        mvc.perform(MockMvcRequestBuilders.multipart("/api/image")
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA))

                // Assert
                .andExpect(status().isBadRequest())
        ;

        // Assert2 - verify that the service was called never called (returned before with BAD_REQUEST)
        Mockito.verifyNoInteractions(mockService);
    }
}
