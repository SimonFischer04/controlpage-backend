package at.fischers.controlpagebackend.integration;

import at.fischers.controlpagebackend.dto.Field;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ViewPersistenceIntTest {
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
    }

    /**
     * Test: when Saving View (with changed background-image in Field) old image deleted
     */
    @Test
    void removeUnusedImages() {
        ImageEntity image = new ImageEntity(0, "TestImage2", "png", new byte[]{42});

        ViewEntity view = findAllViews().get(0);
        view.getFields().get(0).setBackground(image);
        persist(view);

        List<ImageEntity> images = findAllImages();

        assertEquals(images.size(), 1);
        assertEquals(images.get(0).getName(), "TestImage2");
    }

    /*
        Private Util-Methods
     */

    private ViewEntity createViewEntity() {
        GroupEntity headGroup = new GroupEntity(0, null, null, "Head-group");
        GroupEntity childGroup1 = new GroupEntity(0, null, headGroup, "ChildGroup1");
        GroupEntity childGroup2 = new GroupEntity(0, null, headGroup, "ChildGroup2");
        headGroup.setChildGroups(List.of(childGroup1, childGroup2));

        ImageEntity image = new ImageEntity(0, "TestImage1", "png", new byte[]{42});
        FieldEntity field = new FieldEntity(0, null, null, "TestField", image, 1, 1, 0, 0);

        ViewEntity view = new ViewEntity(0, "TestView", childGroup1, List.of(field));
        field.setView(view);
        return view;
    }

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

    //--finds
    private List<ViewEntity> findAllViews() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ViewEntity> cq = cb.createQuery(ViewEntity.class);
        Root<ViewEntity> root = cq.from(ViewEntity.class);
        CriteriaQuery<ViewEntity> findAll = cq.select(root);
        TypedQuery<ViewEntity> findAllQuery = entityManager.createQuery(findAll);

        return findAllQuery.getResultList();
    }

    private List<ImageEntity> findAllImages() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ImageEntity> cq = cb.createQuery(ImageEntity.class);
        Root<ImageEntity> root = cq.from(ImageEntity.class);
        CriteriaQuery<ImageEntity> findAll = cq.select(root);
        TypedQuery<ImageEntity> findAllQuery = entityManager.createQuery(findAll);

        return findAllQuery.getResultList();
    }
}
