package at.fischers.controlpagebackend.unit.util.mapper;

import at.fischers.controlpagebackend.dto.Field;
import at.fischers.controlpagebackend.dto.Group;
import at.fischers.controlpagebackend.dto.view.BasicView;
import at.fischers.controlpagebackend.dto.view.FullView;
import at.fischers.controlpagebackend.entity.FieldEntity;
import at.fischers.controlpagebackend.entity.GroupEntity;
import at.fischers.controlpagebackend.entity.ViewEntity;
import at.fischers.controlpagebackend.util.mapper.ViewMapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ViewMapperTest {
    /**
     * Test: mapping a {@link FieldEntity} to a {@link BasicView}
     */
    @Test
    void testMappingEntityToBasicDTO() {
        /*
            Test 1: mapping with 1 view per group
         */
        {
            GroupEntity groupEntity = new GroupEntity(0, new ArrayList<>(), null, "TestGroup", null);
            ViewEntity viewEntity = new ViewEntity(42, "TestView", groupEntity, null);

            BasicView basicView = ViewMapper.mapEntityToBasicDTO(viewEntity);
            assertNotNull(basicView);
            assertEquals(basicView.getId(), 42);
            assertEquals(basicView.getName(), "TestView");
            assertNotNull(basicView.getGroup());
            assertEquals(basicView.getGroup().getName(), "TestGroup");
        }

        /*
            Test 2: mapping with 3 Views per group (the mapper must also handle other views present in the views group)
         */
        {
            GroupEntity groupEntity1 = new GroupEntity(0, new ArrayList<>(), null, "TestGroup2", new ArrayList<>());
            ViewEntity viewEntity1 = new ViewEntity(43, "TV43", groupEntity1, null);
            ViewEntity viewEntity2 = new ViewEntity(44, "TV44", groupEntity1, null);
            ViewEntity viewEntity3 = new ViewEntity(45, "TV45", groupEntity1, null);
            groupEntity1.getViews().addAll(List.of(viewEntity1, viewEntity2, viewEntity3));

            BasicView basicView1 = ViewMapper.mapEntityToBasicDTO(viewEntity1);
            assertNotNull(basicView1);
            assertNotNull(basicView1.getGroup());
            assertNotNull(basicView1.getGroup().getViews());
            assertEquals(basicView1.getGroup().getViews().size(), 3);
        }
    }

    /**
     * Test: mapping a {@link FieldEntity} to a {@link FullView}
     */
    @Test
    void testMappingEntityToFullDTO() {
        /*
            Test 1: mapping ViewEntity with Fields set (with 1 view per group)
         */
        {
            GroupEntity groupEntity = new GroupEntity(0, new ArrayList<>(), null, "TestGroup", null);
            List<FieldEntity> fieldEntities = List.of(
                    new FieldEntity(0, null, null, "Field1", null, 1, 1, 1, 1),
                    new FieldEntity(1, null, null, "Field2", null, 1, 1, 0, 0),
                    new FieldEntity(2, null, null, "Field3", null, 1, 1, 1, 0),
                    new FieldEntity(3, null, null, "Field4", null, 1, 1, 0, 1));
            ViewEntity viewEntity = new ViewEntity(42, "TestView", groupEntity, fieldEntities);

            FullView fullView = ViewMapper.mapEntityToFullDTO(viewEntity);
            assertNotNull(fullView);
            assertEquals(fullView.getId(), 42);
            assertEquals(fullView.getName(), "TestView");
            assertNotNull(fullView.getGroup());
            // get (y) . get (x)
            assertEquals(fullView.getFields().get(0).get(0).getTitle(), "Field2");
            assertEquals(fullView.getFields().get(1).get(1).getTitle(), "Field1");
            assertEquals(fullView.getFields().get(0).get(1).getTitle(), "Field3");
            assertEquals(fullView.getFields().get(1).get(0).getTitle(), "Field4");
        }

        /*
            Test 2: mapping ViewEntity with fields null (in fact should not be null when fetched from database - should be empty list, but just in case...) creates NullPointerException?
         */
        {
            assertDoesNotThrow(() -> {
                ViewMapper.mapEntityToFullDTO(new ViewEntity(42, "V2", null, null));
            });
        }

        // NOTE: testing if more than 1 view per group works is not needed here because it is already tested in "testMappingEntityToBasicDTO()" if the "forEachGroupView" works
    }

    /**
     * Test: mapping a {@link FullView} (includes testing of {@link BasicView}) to a {@link ViewEntity}
     */
    @Test
    void testMappingDTOToEntity() {
        /*
            Test 1: mapping View with fields set
         */
        Group childGroup1 = new Group(0, null, null, "TestGroup1", null);
        Group childGroup2 = new Group(1, null, null, "TestGroup2", null);
        Group headGroup = new Group(2, List.of(childGroup1, childGroup2), null, "HeadGroup", null);
        childGroup1.setParentGroup(headGroup);
        childGroup2.setParentGroup(headGroup);
        FullView fullView = new FullView(0, "TestView", childGroup1, null);

        List<List<Field>> fields = new ArrayList<>();
        fields.add(List.of(new Field(5, fullView, null, "T1", null, 1, 1), new Field(3, fullView, null, "T2", null, 1, 1)));
        fields.add(List.of(new Field(2, fullView, null, "T3", null, 1, 1), new Field(1, fullView, null, "T4", null, 1, 1)));
        fullView.setFields(fields);

        ViewEntity viewEntity = ViewMapper.mapDTOToEntity(fullView);

        assertNotNull(viewEntity);
        assertNotNull(viewEntity.getGroup());
        assertEquals(viewEntity.getGroup().getName(), "TestGroup1");
        assertEquals(viewEntity.getGroup().getViews().get(0), viewEntity);

        List<FieldEntity> fieldEntities = viewEntity.getFields();
        assertNotNull(fieldEntities);
        assertEquals(fieldEntities.size(), 4);

        FieldEntity testFieldT2 = fieldEntities.get(1);
        assertEquals(testFieldT2.getTitle(), "T2");
        assertEquals(testFieldT2.getXPos(), 1);
        assertEquals(testFieldT2.getYPos(), 0);

        FieldEntity testFieldT3 = fieldEntities.get(2);
        assertEquals(testFieldT3.getTitle(), "T3");
        assertEquals(testFieldT3.getXPos(), 0);
        assertEquals(testFieldT3.getYPos(), 1);

        /*
            Test 2: mapping View with fields null (in fact should not be null when fetched from database - should be empty list, but just in case...) creates NullPointerException?
         */

        assertDoesNotThrow(() -> {
            ViewMapper.mapDTOToEntity(new FullView(42, "V2", null, null));
        });
    }
}
