package eu.fischerserver.controlpage.controlpagebackend.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.fischerserver.controlpage.controlpagebackend.integration.config.ControlpageIntegrationTestBase;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.Group;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.BasicView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * End-to-end tests through the HTTP layer (JSON (de)serialization, mapping, persistence).
 * They pin down the API behaviour so that framework / library upgrades cannot silently change it.
 */
@AutoConfigureMockMvc
public class ApiRoundTripIntTest extends ControlpageIntegrationTestBase {
    private static final String VIEW_PAYLOAD = """
            {"id":0,"name":"Round trip view","groupId":0,"fields":[[{"id":0,"viewId":0,
             "action":{"type":"REST","id":0,"runPolicy":null,"restType":"GET","url":"http://example.invalid/x","body":null},
             "title":{"id":0,"text":"Hello","horizontalAlignment":"CENTER","verticalAlignment":"CENTER","color":"#ffffff"},
             "description":"d","backgroundId":0,"rowspan":1,"colspan":2}]]}
            """;

    // same as VIEW_PAYLOAD, but without the generated ids (lenient comparison only checks the given fields)
    private static final String EXPECTED_VIEW = """
            {"name":"Round trip view","groupId":0,"fields":[[{
             "action":{"type":"REST","runPolicy":null,"restType":"GET","url":"http://example.invalid/x","body":null},
             "title":{"text":"Hello","horizontalAlignment":"CENTER","verticalAlignment":"CENTER","color":"#ffffff"},
             "description":"d","backgroundId":0,"rowspan":1,"colspan":2}]]}
            """;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void saveAndLoadView() throws Exception {
        mvc.perform(post("/api/view").contentType(MediaType.APPLICATION_JSON).content(VIEW_PAYLOAD))
                .andExpect(status().isOk());

        String list = mvc.perform(get("/api/view/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.views.length()").value(1))
                .andExpect(jsonPath("$.views[0].name").value("Round trip view"))
                .andReturn().getResponse().getContentAsString();
        int id = objectMapper.readTree(list).get("views").get(0).get("id").asInt();

        mvc.perform(get("/api/view/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(EXPECTED_VIEW))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.fields[0][0].viewId").value(id));
    }

    @Test
    void loadUnknownViewIsBadRequest() throws Exception {
        mvc.perform(get("/api/view/999999"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void malformedJsonIsProblemDetail() throws Exception {
        mvc.perform(post("/api/view").contentType(MediaType.APPLICATION_JSON).content("{\"id\":\"x\""))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.title").value("Bad Request"));
    }

    @Test
    void uploadAndDownloadImage() throws Exception {
        byte[] data = {(byte) 0x89, 'P', 'N', 'G', 1, 2, 3};
        MockMultipartFile file = new MockMultipartFile("imageFile", "test.png", "image/png", data);

        String response = mvc.perform(multipart("/api/image").file(file))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        int id = objectMapper.readTree(response).get("imageId").asInt();

        byte[] downloaded = mvc.perform(get("/api/image/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG))
                .andReturn().getResponse().getContentAsByteArray();
        assertArrayEquals(data, downloaded);
    }

    /**
     * Pins the JSON shape of {@link Group}: back references (parent group, a view's group) are not serialized.
     */
    @Test
    void groupSerialization() throws Exception {
        Group child = new Group(2, List.of(), null, "child", List.of());
        BasicView view = BasicView.basicBuilder().id(7).name("v").build();
        Group parent = new Group(1, List.of(child), null, "parent", List.of(view));

        String json = objectMapper.writeValueAsString(parent);

        assertEquals(objectMapper.readTree("""
                        {"id":1,"childGroups":[{"id":2,"childGroups":[],"name":"child","views":[]}],
                         "name":"parent","views":[{"id":7,"name":"v"}]}
                        """),
                objectMapper.readTree(json));
    }
}
