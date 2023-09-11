package eu.fischerserver.controlpage.controlpagebackend.integration;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.text.HorizontalAlignment;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.text.VerticalAlignment;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.*;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.action.ActionEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.action.ViewActionEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.global.action.ViewActionType;
import eu.fischerserver.controlpage.controlpagebackend.repository.*;
import eu.fischerserver.controlpage.controlpagebackend.util.DummyEntityUtils;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ViewPersistenceIntTest {
    /*
        INTO:
        Assumption: you can only save whole View's in this project because then managing orphan/unused Entities is way easier.
            - This small data overhead is negligible here(also this is only during editing...)
     */

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ViewRepository viewRepository;

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private ActionRepository actionRepository;

    @BeforeEach
    void reInit() {
        // group needs to be saved before to bypass weird detached issues. No problem as in real use the groups are managed / saved extra anyway
        var group = groupRepository.save(DummyEntityUtils.getDummyGroupEntityTreeHead(0, false));
        var testView = DummyEntityUtils.getDummyViewEntity(0);
        testView.setGroup(group);
        viewRepository.save(testView);
        assertEquals(1, findAllViews().size());
    }

    /**
     * Test to persist a full view (with Field, Images,...).
     * Checks if one Entity (created by @BeforeEach is saved successfully)
     */
    @Test
    public void testPersist() {
        List<ViewEntity> views = findAllViews();

        ViewEntity view = views.get(0);
        List<FieldEntity> fields = view.getFields();
        FieldEntity field = fields.get(0);
        ActionEntity actionEntity = field.getAction();

        assertEquals(1, views.size());
        assertNotNull(view);

        assertEquals(8, fields.size());
        assertNotNull(field);
        assertNotNull(field.getBackground());

        assertNotNull(actionEntity);
        assertTrue(actionEntity instanceof ViewActionEntity);

        // empty list
        assertNotNull(view.getGroup());
        assertNotNull(view.getGroup().getChildGroups());
    }

    /*
        -----------------------------------------------------------------------------------------------------------------------------------
        Testing removing of unused entities (orphan removal)
        -----------------------------------------------------------------------------------------------------------------------------------
     */

    /**
     * Test: when Saving View (with changed background-image in Field) old image deleted
     */
    @Test
    public void testRemoveUnusedImages() {
        final var image = new ImageEntity(0, "an image to test removing unused images from db", "png", new byte[]{42});
        {
            /*
                check before value and then update background
             */
            final var view = findView();
            final var images = imageRepository.findAll();

            assertEquals(8, images.size());
            assertNotEquals(image.getName(), view.getFields().get(0).getBackground().getName());

            view.getFields().get(0).setBackground(image);
            viewRepository.save(view);
        }

        {
            /*
                fetch again and check for change + same number of images in db / old one got removed
             */
            final var view = findView();
            final var images = imageRepository.findAll();

            assertEquals(8, images.size());
            assertEquals(view.getFields().get(0).getBackground().getName(), images.get(images.size() - 1).getName());
        }
    }

    /**
     * Test: removing unused groups
     * //TODO: disabled, because i can't find an automatic way to auto delete group if no view is part of it
     * * (orphanRemoval doesn't exist on @ManyToOne)
     * * => has to be done in service -> test in service testClass
     * * //TODO: delete this test(if service test exists)
     */
    @Test
    @Disabled
    public void testRemoveUnusedGroups() {
        ViewEntity viewEntity = findView();
        viewEntity.setGroup(null);
        viewRepository.save(viewEntity);

        assertEquals(0, groupRepository.findAll().size());
    }

    /**
     * Test: remove unused fields
     */
    @Test
    public void testRemoveUnusedFields() {
        ViewEntity viewEntity = findView();
        viewEntity.getFields().clear();
        viewRepository.save(viewEntity);

        assertEquals(0, fieldRepository.findAll().size());
    }

    /**
     * Test: remove unused actions
     */
    @Test
    public void testRemoveUnusedAction() {
        {
            /*
                check before removing an action
             */
            assertEquals(8, actionRepository.findAll().size());
        }

        {
            /*
                remove action from field and check if one less in db
             */
            final var viewEntity = findView();
            viewEntity.getFields().get(0).setAction(null);
            viewRepository.save(viewEntity);

            assertEquals(7, actionRepository.findAll().size());
        }
    }

    /*
        -----------------------------------------------------------------------------------------------------------------------------------
        Test cascading(f.e I forgot to add cascade to group[in viewEntity], changed the group name and wondered why the name didn't change)
        -----------------------------------------------------------------------------------------------------------------------------------
     */

    /**
     * Test: cascading from ViewEntity to GroupEntity
     */
    @Test
    public void testGroupCascading() {
        ViewEntity viewEntity = findView();
        GroupEntity groupEntity = viewEntity.getGroup();
        groupEntity.setName("AnotherName");
        viewRepository.save(viewEntity);

        /*
            CRUD...
         */

        // CREATE: tested in testPersist()

        // READ: tested like everywhere

        // UPDATE
        GroupEntity reFetchedGroup = groupRepository.findById(groupEntity.getId()).orElse(null);
        assertNotNull(reFetchedGroup);
        assertEquals(reFetchedGroup.getName(), "AnotherName");

        // DELETE
        viewRepository.delete(viewEntity);
        assertEquals(0, groupRepository.findAll().size());
    }

    /**
     * Test: cascading from ViewEntity to FieldEntity
     */
    @Test
    public void testFieldCascading() {
        ViewEntity viewEntity = findView();
        FieldEntity fieldEntity = viewEntity.getFields().get(0);
        fieldEntity.setTitle(DummyEntityUtils.getDummyStyledTextEntity());
        viewRepository.save(viewEntity);

        /*
            CRUD...
         */

        // CREATE: tested in testPersist()

        // READ: tested like everywhere

        // UPDATE
        FieldEntity reFetchedEntity = fieldRepository.findById(fieldEntity.getId()).orElse(null);
        assertNotNull(reFetchedEntity);
        assertEquals(fieldEntity.getTitle().getText(), reFetchedEntity.getTitle().getText());

        // DELETE
        viewRepository.delete(viewEntity);
        assertEquals(0, fieldRepository.findAll().size());
    }

    /**
     * Test: cascading from ViewEntity over Field to Action
     */
    @Test
    public void testActionCascading() {
        ViewEntity viewEntity = findView();
        ViewActionEntity actionEntity = (ViewActionEntity) viewEntity.getFields().get(0).getAction();
        actionEntity.setViewActionType(ViewActionType.SWITCH_TO);
        viewRepository.save(viewEntity);

        /*
            CRUD...
         */

        // CREATE: tested in testPersist()

        // READ: tested like everywhere

        // UPDATE
        ViewActionEntity reFetchedAction = (ViewActionEntity) actionRepository.findAll().get(0);
        assertEquals(reFetchedAction.getViewActionType(), ViewActionType.SWITCH_TO);

        // DELETE
        viewRepository.delete(viewEntity);
        assertEquals(0, actionRepository.findAll().size());
    }

    /**
     * Test: cascading from ViewEntity over FieldEntity to ImageEntity
     */

    @Test
    public void testImageCascading() {
        ViewEntity viewEntity = findView();

        /*
            CRUD...
         */

        // CREATE: tested in testPersist()

        // READ: tested like everywhere

        // UPDATE: updating an image doesn't really make sense -> a new one is created each time

        // DELETE
        viewRepository.delete(viewEntity);
        assertEquals(0, imageRepository.findAll().size());
    }

    /*
        -----------------------------------------------------------------------------------------------------------------------------------
        Private Util-Methods
        -----------------------------------------------------------------------------------------------------------------------------------
     */

    /*
        --finds
     */

    //get dummy view from entityManager(doing this because unsure if id is always 0)
    private ViewEntity findView() {
        return findAllViews().get(0);
    }

    private List<ViewEntity> findAllViews() {
        return viewRepository.findAll();
    }
}
