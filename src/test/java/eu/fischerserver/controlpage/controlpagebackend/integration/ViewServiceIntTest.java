package eu.fischerserver.controlpage.controlpagebackend.integration;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.Field;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.text.HorizontalAlignment;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.text.StyledText;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.text.VerticalAlignment;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.BasicView;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.FullView;
import eu.fischerserver.controlpage.controlpagebackend.repository.ViewRepository;
import eu.fischerserver.controlpage.controlpagebackend.service.ViewService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests saving and reading views the way the application does it:
 * through the service, the mappers and the configured database.
 */
@SpringBootTest
public class ViewServiceIntTest {
    @Autowired
    private ViewService viewService;
    @Autowired
    private ViewRepository viewRepository;

    private final String viewName = "ViewServiceIntTest " + UUID.randomUUID();

    @AfterEach
    void removeTestView() {
        findTestViewId().ifPresent(viewRepository::deleteById);
    }

    /**
     * Test: a new view (as a client sends it: new fields have the id -1) is saved with all its fields
     */
    @Test
    void testSaveNewView() {
        viewService.save(newView(2, 2));

        final var saved = findTestView();

        assertEquals(viewName, saved.getName());
        assertEquals(List.of(List.of("0/0", "0/1"), List.of("1/0", "1/1")), titles(saved));
        // every field got its own id from the database
        final var ids = fieldIds(saved);
        assertEquals(4, new HashSet<>(ids).size());
        ids.forEach(id -> assertTrue(id > 0));
    }

    /**
     * Test: saving a view again (with a new row) keeps the existing fields and adds the new ones
     */
    @Test
    void testSaveExistingViewWithNewRow() {
        viewService.save(newView(1, 2));
        final var saved = findTestView();
        final var idsBefore = fieldIds(saved);

        saved.getFields().add(new ArrayList<>(List.of(newField("1/0"), newField("1/1"))));
        viewService.save(saved);

        final var savedAgain = findTestView();
        assertEquals(saved.getId(), savedAgain.getId());
        assertEquals(List.of(List.of("0/0", "0/1"), List.of("1/0", "1/1")), titles(savedAgain));
        final var idsAfter = fieldIds(savedAgain);
        assertEquals(idsBefore, idsAfter.subList(0, 2));
        assertEquals(4, new HashSet<>(idsAfter).size());
    }

    /**
     * Test: saving a view again without one of its rows removes the fields of that row
     */
    @Test
    void testSaveExistingViewWithoutRow() {
        viewService.save(newView(2, 2));
        final var saved = findTestView();
        final var idsBefore = fieldIds(saved);

        saved.getFields().remove(1);
        viewService.save(saved);

        final var savedAgain = findTestView();
        assertEquals(List.of(List.of("0/0", "0/1")), titles(savedAgain));
        assertEquals(idsBefore.subList(0, 2), fieldIds(savedAgain));
    }

    /*
        Utils
     */

    private FullView newView(int rows, int columns) {
        final List<List<Field>> fields = new ArrayList<>();
        for (int y = 0; y < rows; y++) {
            final List<Field> row = new ArrayList<>();
            for (int x = 0; x < columns; x++) {
                row.add(newField("%d/%d".formatted(y, x)));
            }
            fields.add(row);
        }
        return FullView.fullBuilder().id(0).name(viewName).fields(fields).build();
    }

    private Field newField(String title) {
        return Field.builder()
                // like a client: a negative id marks a new field
                .id(-1)
                .title(StyledText.builder()
                        .text(title)
                        .horizontalAlignment(HorizontalAlignment.CENTER)
                        .verticalAlignment(VerticalAlignment.CENTER)
                        .color("#ffffff")
                        .build())
                .rowspan(1)
                .colspan(1)
                .build();
    }

    private java.util.Optional<Integer> findTestViewId() {
        return viewService.findAllBasic().stream()
                .filter(view -> viewName.equals(view.getName()))
                .map(BasicView::getId)
                .findFirst();
    }

    private FullView findTestView() {
        final var view = viewService.findById(findTestViewId().orElseThrow());
        assertNotNull(view);
        return view;
    }

    private List<List<String>> titles(FullView view) {
        return view.getFields().stream()
                .map(row -> row.stream().map(field -> field.title().text()).toList())
                .toList();
    }

    private List<Integer> fieldIds(FullView view) {
        return view.getFields().stream().flatMap(List::stream).map(Field::id).toList();
    }
}
