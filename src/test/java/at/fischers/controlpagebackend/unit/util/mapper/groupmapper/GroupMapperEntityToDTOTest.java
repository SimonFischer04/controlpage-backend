package at.fischers.controlpagebackend.unit.util.mapper.groupmapper;

import at.fischers.controlpagebackend.dto.Group;
import at.fischers.controlpagebackend.entity.GroupEntity;
import at.fischers.controlpagebackend.entity.ViewEntity;
import at.fischers.controlpagebackend.util.mapper.groupmapper.GroupMapper;
import at.fischers.controlpagebackend.util.mapper.groupmapper.GroupMapperEntityToDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GroupMapperEntityToDTOTest {
    /*
        Note: i know this is kind of code duplication to the GroupMapperDTOToEntityTest, but building a "framework" (superclass,...)
        would create more overhead / is over-engineered
     */

    private static GroupEntity childEntity11, childEntity1, childEntity2, headEntity;

    @BeforeAll
    static void init() {
        /*
            init for GroupEntity -> Group
         */
        childEntity11 = new GroupEntity(11, null, null, "Child11", null);
        childEntity1 = new GroupEntity(1, List.of(childEntity11), null, "Child1", null);
        childEntity11.setParentGroup(childEntity1);

        childEntity2 = new GroupEntity(2, null, null, "Child2", null);

        headEntity = new GroupEntity(0, List.of(childEntity1, childEntity2), null, "HeadGroup", null);
        childEntity1.setParentGroup(headEntity);
        childEntity2.setParentGroup(headEntity);
    }

    /**
     * Other tests
     */
    @Test
    void testMapEntityToDTO() {
        /*
            Test 1: test mapping of 3 Views in Group
         */
        {
            GroupEntity groupEntity = new GroupEntity(1, Collections.emptyList(), null, "GroupName", new ArrayList<>());
            ViewEntity v1 = new ViewEntity(1, "View1", groupEntity, Collections.emptyList());
            ViewEntity v2 = new ViewEntity(2, "View2", groupEntity, Collections.emptyList());
            ViewEntity v3 = new ViewEntity(3, "View3", groupEntity, Collections.emptyList());
            groupEntity.getViews().addAll(List.of(v1, v2, v3));

            Group group = GroupMapper.mapEntityToDTO(groupEntity);
            assertNotNull(group);
            assertNotNull(group.getViews());
            assertEquals(group.getViews().size(), 3);
        }
    }

    /**
     * Test EntityToDTO mapper with a group depth of 3 -> test layer 1
     */
    @Test
    void testMapEntityToDTOLayer1() {
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
        Group headGroup = GroupMapperEntityToDTO.mapEntityToDTO(headEntity);
        assertNotNull(headGroup);
        assertEquals(headGroup.getName(), "HeadGroup");

        assertNotNull(headGroup.getChildGroups());
        assertEquals(headGroup.getChildGroups().size(), 2);
        //-----------------------------------------------------------------
    }

    /**
     * Test EntityToDTO mapper with a group depth of 3 -> test layer 2
     */
    @Test
    void testMapToDTOLayer2() {
        Group headGroup = GroupMapperEntityToDTO.mapEntityToDTO(headEntity);
        Group childGroup1 = GroupMapperEntityToDTO.mapEntityToDTO(childEntity1);

        /*
            Test 2a: mapping a child in layer 2 (childEntity1) with children
         */
        assertNotNull(childGroup1);
        assertEquals(childGroup1.getName(), "Child1");

        // check children.
        assertNotNull(childGroup1.getChildGroups());
        assertEquals(childGroup1.getChildGroups().size(), 1);

        // check parent group
        assertNotNull(childGroup1.getParentGroup());
        assertEquals(childGroup1.getParentGroup(), headGroup);

        /*
            Test 2b: mapping a child in layer 2 (childEntity2) empty children
         */
        Group childGroup2 = GroupMapperEntityToDTO.mapEntityToDTO(childEntity2);
        assertNotNull(childGroup2);
        assertEquals(childGroup2.getName(), "Child2");

        // check children.
        // childGroups should be an empty list and not null!
        assertNotNull(childGroup2.getChildGroups());
        assertEquals(childGroup2.getChildGroups().size(), 0);

        // check parent group
        assertNotNull(childGroup2.getParentGroup());
        assertEquals(childGroup2.getParentGroup(), headGroup);
        //------------------------------------------------------------------------------
    }

    /**
     * Test EntityToDTO mapper with a group depth of 3 -> test layer 3
     */
    @Test
    void testMapToDTOLayer3() {
        Group childGroup1 = GroupMapperEntityToDTO.mapEntityToDTO(childEntity1);

        /*
            Test 3: mapping a child in layer 3
         */
        Group childGroup11 = GroupMapperEntityToDTO.mapEntityToDTO(childEntity11);
        assertNotNull(childGroup11);

        // check children
        assertNotNull(childGroup11.getChildGroups());
        assertEquals(childGroup11.getChildGroups().size(), 0);

        // check parents
        assertNotNull(childGroup11.getParentGroup());
        assertEquals(childGroup11.getParentGroup(), childGroup1);
    }
}
