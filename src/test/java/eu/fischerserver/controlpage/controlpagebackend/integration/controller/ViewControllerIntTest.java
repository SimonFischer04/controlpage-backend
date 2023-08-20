package eu.fischerserver.controlpage.controlpagebackend.integration.controller;

import eu.fischerserver.controlpage.controlpagebackend.controller.ViewController;
import eu.fischerserver.controlpage.controlpagebackend.service.ViewService;
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
