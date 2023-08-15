package at.fischers.controlpagebackend.unit.util.mapper.groupmapper;

import at.fischers.controlpagebackend.dto.Group;
import at.fischers.controlpagebackend.dto.Image;
import at.fischers.controlpagebackend.dto.view.BasicView;
import at.fischers.controlpagebackend.dto.view.FullView;
import at.fischers.controlpagebackend.entity.GroupEntity;
import at.fischers.controlpagebackend.entity.ViewEntity;
import at.fischers.controlpagebackend.service.ImageService;
import at.fischers.controlpagebackend.util.mapper.groupmapper.GroupMapper;
import at.fischers.controlpagebackend.util.mapper.groupmapper.GroupMapperDTOToEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GroupMapperDTOToEntityTest {
    final ImageService mockImageService = new ImageService() {
        @Override
        public Image findById(int id) {
            return null;
        }

        @Override
        public Image save(Image image) {
            return null;
        }
    };

    /*
        Note: i know this is kind of code duplication to the GroupMapperDTOToEntityTest, but building a "framework" (superclass,...)
        would create more overhead / is over-engineered
     */

    private static Group child11, child1, child2, head;

    @BeforeAll
    static void init() {
        /*
            init for GroupEntity -> Group
         */
        child11 = new Group(11, null, null, "Child11", null);
        child1 = new Group(1, List.of(child11), null, "Child1", null);
        child11.setParentGroup(child1);

        child2 = new Group(2, null, null, "Child2", null);

        head = new Group(0, List.of(child1, child2), null, "HeadGroup", null);
        child1.setParentGroup(head);
        child2.setParentGroup(head);
    }

    /**
     * Other tests
     */
    @Test
    void testMapDTOToEntity() {
        /*
            Test 1: test mapping of 3 Views in Group
         */
        {
            Group group = new Group(1, Collections.emptyList(), null, "GroupName", new ArrayList<>());
            BasicView v1 = new BasicView(1, "View1", group);
            FullView v2 = new FullView(2, "View2", group, Collections.emptyList());
            BasicView v3 = new BasicView(3, "View3", group);
            group.getViews().addAll(List.of(v1, v2, v3));

            GroupEntity groupEntity = GroupMapper.mapDTOToEntity(mockImageService, group);
            assertNotNull(groupEntity);
            assertNotNull(groupEntity.getViews());
            assertEquals(groupEntity.getViews().size(), 3);
        }
    }

    /**
     * Test DTOToEntity mapper with a group depth of 3 -> test layer 1
     */
    @Test
    void testMapDTOToEntityLayer1() {
        /*
            Group tree:

            Layer       |               Tree
            1)          |   headEntity
                        |       |--------------------------|
            2)          |   childEntity1            childEntity2
                        |       |
            3)          |   childEntity11
         */


        /*
            Test 1: mapping head group Entity
         */
        {
            GroupEntity headGroupEntity = GroupMapperDTOToEntity.mapDTOToEntity(mockImageService, head);
            assertNotNull(headGroupEntity);
            assertEquals(headGroupEntity.getName(), "HeadGroup");

            assertNotNull(headGroupEntity.getChildGroups());
            assertEquals(headGroupEntity.getChildGroups().size(), 2);
        }
    }

    /**
     * Test DTOToEntity mapper with a group depth of 3 -> test layer 2
     */
    @Test
    void testMapDTOToEntityLayer2() {
        GroupEntity headGroupEntity = GroupMapperDTOToEntity.mapDTOToEntity(mockImageService, head);
        GroupEntity childGroup1Entity = GroupMapperDTOToEntity.mapDTOToEntity(mockImageService, child1);

        /*
            Test 2a: mapping a child in layer 2 (childEntity1) with children
         */
        {
            assertNotNull(childGroup1Entity);
            assertEquals(childGroup1Entity.getName(), "Child1");

            // check children.
            assertNotNull(childGroup1Entity.getChildGroups());
            assertEquals(childGroup1Entity.getChildGroups().size(), 1);

            // check parent group
            assertNotNull(childGroup1Entity.getParentGroup());
            assertEquals(childGroup1Entity.getParentGroup(), headGroupEntity);
        }

        /*
            Test 2b: mapping a child in layer 2 (childEntity2) empty children
         */
        {
            GroupEntity childGroup2 = GroupMapperDTOToEntity.mapDTOToEntity(mockImageService, child2);
            assertNotNull(childGroup2);
            assertEquals(childGroup2.getName(), "Child2");

            // check children.
            // childGroups should be an empty list and not null!
            assertNotNull(childGroup2.getChildGroups());
            assertEquals(childGroup2.getChildGroups().size(), 0);

            // check parent group
            assertNotNull(childGroup2.getParentGroup());
            assertEquals(childGroup2.getParentGroup(), headGroupEntity);
        }
    }

    /**
     * Test DTOToEntity mapper with a group depth of 3 -> test layer 3
     */
    @Test
    void testMapToDTOLayer3() {
        GroupEntity childGroup1Entity = GroupMapperDTOToEntity.mapDTOToEntity(mockImageService, child1);

        /*
            Test 3: mapping a child in layer 3
         */
        {
            GroupEntity childGroup11 = GroupMapperDTOToEntity.mapDTOToEntity(mockImageService, child11);
            assertNotNull(childGroup11);

            // check children
            assertNotNull(childGroup11.getChildGroups());
            assertEquals(childGroup11.getChildGroups().size(), 0);

            // check parents
            assertNotNull(childGroup11.getParentGroup());
            assertEquals(childGroup11.getParentGroup(), childGroup1Entity);
        }
    }
}
