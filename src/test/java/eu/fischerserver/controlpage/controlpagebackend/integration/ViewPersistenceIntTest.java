package eu.fischerserver.controlpage.controlpagebackend.integration;

import eu.fischerserver.controlpage.controlpagebackend.model.entity.*;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.action.ActionEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.action.ViewActionEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.global.action.ViewActionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ViewPersistenceIntTest {
    /*
        INTO:
        Assumption: you can only save whole View's in this project because then managing orphan/unused Entities is way easier.
            - This small data overhead is negligible here(also this is only during editing...)
     */

    private static EntityManager entityManager;

    @BeforeEach
    void reInit() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa-h2-removal");
        entityManager = factory.createEntityManager();
        persist(createViewEntity());
        assertEquals(1, findAllViews().size());
    }

    /**
     * Test to persist a full view (with Field, Images,...).
     * Checks if one Entity (created by @BeforeEach is saved successfully)
     */
    @Test
    void testPersist() {
        List<ViewEntity> views = findAllViews();

        ViewEntity view = views.get(0);
        List<FieldEntity> fields = view.getFields();
        FieldEntity field = fields.get(0);
        ActionEntity actionEntity = field.getAction();

        assertEquals(1, views.size());
        assertNotNull(view);

        assertEquals(1, fields.size());
        assertNotNull(field);
        assertNotNull(field.getBackground());

        assertNotNull(actionEntity);
        assertTrue(actionEntity instanceof ViewActionEntity);

        assertNotNull(view.getGroup());
        assertNull(view.getGroup().getChildGroups());
        assertNotNull(view.getGroup().getParentGroup());
        System.out.println(view.getGroup().getParentGroup());
        assertNotNull(view.getGroup().getParentGroup().getChildGroups());
        view.getGroup().getParentGroup().getChildGroups().forEach(System.out::println);
        assertEquals(2, view.getGroup().getParentGroup().getChildGroups().size());
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
    void testRemoveUnusedImages() {
        ImageEntity image = new ImageEntity(0, "TestImage2", "png", new byte[]{42});

        ViewEntity view = findAllViews().get(0);
        view.getFields().get(0).setBackground(image);
        persist(view);

        List<ImageEntity> images = findAllImages();

        assertEquals(1, images.size());
        assertEquals("TestImage2", images.get(0).getName());
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
    void testRemoveUnusedGroups() {
        ViewEntity viewEntity = findView();
        viewEntity.setGroup(null);
        persist(viewEntity);

        assertEquals(0, findAllGroups().size());
    }

    /**
     * Test: remove unused fields
     */
    @Test
    void testRemoveUnusedFields() {
        ViewEntity viewEntity = findView();
        viewEntity.getFields().clear();
        persist(viewEntity);

        assertEquals(0, findAllFields().size());
    }

    /**
     * Test: remove unused actions
     */
    @Test
    void testRemoveUnusedAction() {
        ViewEntity viewEntity = findView();
        viewEntity.getFields().get(0).setAction(null);
        persist(viewEntity);

        assertEquals(0, findAllActions().size());
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
    void testGroupCascading() {
        ViewEntity viewEntity = findView();
        GroupEntity groupEntity = viewEntity.getGroup();
        groupEntity.setName("AnotherName");
        persist(viewEntity);

        /*
            CRUD...
         */

        // CREATE: tested in testPersist()

        // READ: tested like everywhere

        // UPDATE
        GroupEntity reFetchedGroup = entityManager.find(GroupEntity.class, groupEntity.getId());
        assertEquals(reFetchedGroup.getName(), "AnotherName");

        // DELETE
        remove(viewEntity);
        assertEquals(0, findAllGroups().size());
    }

    /**
     * Test: cascading from ViewEntity to FieldEntity
     */
    @Test
    void testFieldCascading() {
        ViewEntity viewEntity = findView();
        FieldEntity fieldEntity = viewEntity.getFields().get(0);
        fieldEntity.setTitle(new StyledTextEntity("A Title"));
        persist(viewEntity);

        /*
            CRUD...
         */

        // CREATE: tested in testPersist()

        // READ: tested like everywhere

        // UPDATE
        FieldEntity reFetchedEntity = entityManager.find(FieldEntity.class, fieldEntity.getId());
        assertEquals(reFetchedEntity.getTitle().getText(), "A Title");

        // DELETE
        remove(viewEntity);
        assertEquals(0, findAllFields().size());
    }

    /**
     * Test: cascading from ViewEntity over Field to Action
     */
    @Test
    void testActionCascading() {
        ViewEntity viewEntity = findView();
        ViewActionEntity actionEntity = (ViewActionEntity) viewEntity.getFields().get(0).getAction();
        actionEntity.setViewActionType(ViewActionType.SWITCH_TO);
        persist(viewEntity);

        /*
            CRUD...
         */

        // CREATE: tested in testPersist()

        // READ: tested like everywhere

        // UPDATE
        ViewActionEntity reFetchedAction = (ViewActionEntity) findAllActions().get(0);
        assertEquals(reFetchedAction.getViewActionType(), ViewActionType.SWITCH_TO);

        // DELETE
        remove(viewEntity);
        assertEquals(0, findAllActions().size());
    }

    /**
     * Test: cascading from ViewEntity over FieldEntity to ImageEntity
     */

    @Test
    void testImageCascading() {
        ViewEntity viewEntity = findView();

        /*
            CRUD...
         */

        // CREATE: tested in testPersist()

        // READ: tested like everywhere

        // UPDATE: updating an image doesnt really make sense -> a new one is created each time

        // DELETE
        remove(viewEntity);
        assertEquals(0, findAllImages().size());
    }

    /*
        -----------------------------------------------------------------------------------------------------------------------------------
        Private Util-Methods
        -----------------------------------------------------------------------------------------------------------------------------------
     */

    private ViewEntity createViewEntity() {
        GroupEntity headGroup = new GroupEntity(0, null, null, "HeadGroup", null);
        GroupEntity childGroup1 = new GroupEntity(0, null, headGroup, "ChildGroup1", null);
        GroupEntity childGroup2 = new GroupEntity(0, null, headGroup, "ChildGroup2", null);
        headGroup.setChildGroups(List.of(childGroup1, childGroup2));

        ImageEntity image = new ImageEntity(0, "TestImage1", "png", new byte[]{42});
        ViewActionEntity action = new ViewActionEntity(ViewActionType.CLOSE, -1);
        FieldEntity field = new FieldEntity(0, null, action, new StyledTextEntity("TestField"), "description", image, 1, 1, 0, 0);

        ViewEntity view = new ViewEntity(0, "TestView", childGroup1, new ArrayList<>(List.of(field)));
        field.setView(view);
        return view;
    }

    // only persist method (see reason at the very top)
    // [NOTE: the saving of the "sub-entities" here done manually is done automatically by spring when using a real DB]
    private void persist(ViewEntity view) {
        entityManager.getTransaction().begin();
        entityManager.persist(view);
        if (view.getGroup() != null) {
            entityManager.persist(view.getGroup());
            if (view.getGroup().getParentGroup() != null) {
                entityManager.persist(view.getGroup().getParentGroup());
            }
        }
        if (view.getFields() != null) {
            view.getFields().forEach(v -> {
                if (v.getAction() != null) {
                    entityManager.persist(v.getAction());
                }
                entityManager.persist(v);
            });
        }
        entityManager.getTransaction().commit();
    }

    // only persist method (see reason at the very top)
    private void remove(ViewEntity viewEntity) {
        entityManager.getTransaction().begin();
        entityManager.remove(viewEntity);
        entityManager.getTransaction().commit();
    }

    /*
        --finds
     */

    //get dummy view from entityManager(doing this because unsure if id is always 0)
    private ViewEntity findView() {
        return findAllViews().get(0);
    }

    private List<GroupEntity> findAllGroups() {
        return findAll(GroupEntity.class);
    }

    @SuppressWarnings("SameParameterValue")
    private GroupEntity findGroupByName(String groupName) {
        return findAllGroups().stream().filter(groupEntity -> groupEntity.getName().equals(groupName)).findFirst().orElse(null);
    }

    private List<ViewEntity> findAllViews() {
        return findAll(ViewEntity.class);
    }

    private List<ImageEntity> findAllImages() {
        return findAll(ImageEntity.class);
    }

    private List<FieldEntity> findAllFields() {
        return findAll(FieldEntity.class);
    }

    private List<ActionEntity> findAllActions() {
        return findAll(ActionEntity.class);
    }

    private <T> List<T> findAll(Class<T> clazz) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);
        Root<T> root = cq.from(clazz);
        CriteriaQuery<T> findAll = cq.select(root);
        TypedQuery<T> findAllQuery = entityManager.createQuery(findAll);

        return findAllQuery.getResultList();
    }
}
