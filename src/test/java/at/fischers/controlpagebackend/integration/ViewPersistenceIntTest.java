package at.fischers.controlpagebackend.integration;

import at.fischers.controlpagebackend.dto.Field;
import at.fischers.controlpagebackend.dto.Group;
import at.fischers.controlpagebackend.dto.view.FullView;
import at.fischers.controlpagebackend.entity.FieldEntity;
import at.fischers.controlpagebackend.entity.GroupEntity;
import at.fischers.controlpagebackend.entity.ImageEntity;
import at.fischers.controlpagebackend.entity.ViewEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.text.View;

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

        assertEquals(views.size(), 1);
        assertNotNull(view);
        assertEquals(fields.size(), 1);
        assertNotNull(field);
        assertNotNull(field.getBackground());

        assertNotNull(view.getGroup());
        assertNull(view.getGroup().getChildGroups());
        assertNotNull(view.getGroup().getParentGroup());
        System.out.println(view.getGroup().getParentGroup());
        assertNotNull(view.getGroup().getParentGroup().getChildGroups());
        view.getGroup().getParentGroup().getChildGroups().forEach(System.out::println);
        assertEquals(view.getGroup().getParentGroup().getChildGroups().size(), 2);
    }

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

        assertEquals(images.size(), 1);
        assertEquals(images.get(0).getName(), "TestImage2");
    }

    /**
     * Test: removing all children if parent deleted
     * TODO: delete/change this because only VIEW persisting!
     */
    @Test
    void testRemoveGroup() {
        GroupEntity group = findGroupByName("HeadGroup");
        remove(group);
        assertEquals(findAllGroups().size(), 0);
    }

    // TODO: test cascading(f.e I forgot to add cascade to group, changed the group name and wondered why the name didn't change)

    /*
        Private Util-Methods
     */

    private ViewEntity createViewEntity() {
        GroupEntity headGroup = new GroupEntity(0, null, null, "HeadGroup", null);
        GroupEntity childGroup1 = new GroupEntity(0, null, headGroup, "ChildGroup1", null);
        GroupEntity childGroup2 = new GroupEntity(0, null, headGroup, "ChildGroup2", null);
        headGroup.setChildGroups(List.of(childGroup1, childGroup2));

        ImageEntity image = new ImageEntity(0, "TestImage1", "png", new byte[]{42});
        FieldEntity field = new FieldEntity(0, null, null, "TestField", image, 1, 1, 0, 0);

        ViewEntity view = new ViewEntity(0, "TestView", childGroup1, List.of(field));
        field.setView(view);
        return view;
    }

    // only persist method (see reason at the very top)
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
            view.getFields().forEach(v -> entityManager.persist(v));
        }
        entityManager.getTransaction().commit();
    }

    // TODO: (see remove Test)
    private void remove(GroupEntity groupEntity) {
        // manually setting group of each view to null -> tested elsewhere (testing class of service)
        findAllViews().forEach(viewEntity -> {
            viewEntity.setGroup(null);
            persist(viewEntity);
        });

        entityManager.getTransaction().begin();
        entityManager.remove(groupEntity);
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

    private <T> List<T> findAll(Class<T> clazz) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);
        Root<T> root = cq.from(clazz);
        CriteriaQuery<T> findAll = cq.select(root);
        TypedQuery<T> findAllQuery = entityManager.createQuery(findAll);

        return findAllQuery.getResultList();
    }
}
