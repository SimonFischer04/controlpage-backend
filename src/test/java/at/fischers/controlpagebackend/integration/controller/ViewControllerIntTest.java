package at.fischers.controlpagebackend.integration.controller;

import at.fischers.controlpagebackend.controller.ViewController;
import at.fischers.controlpagebackend.service.ViewService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ViewController.class)
//@AutoConfigureMockMvc
public class ViewControllerIntTest {
    @MockBean
    private ViewService mockService;

    @Autowired
    private MockMvc mockMvc;

}
