package at.fischers.controlpagebackend.unit.util;

import at.fischers.controlpagebackend.dto.Group;
import at.fischers.controlpagebackend.entity.GroupEntity;
import at.fischers.controlpagebackend.util.GroupMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GroupMapperTest {
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
        Group headGroup = GroupMapper.mapEntityToDTO(headEntity);
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
        Group headGroup = GroupMapper.mapEntityToDTO(headEntity);
        Group childGroup1 = GroupMapper.mapEntityToDTO(childEntity1);

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
        Group childGroup2 = GroupMapper.mapEntityToDTO(childEntity2);
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
        Group childGroup1 = GroupMapper.mapEntityToDTO(childEntity1);

        /*
            Test 3: mapping a child in layer 3
         */
        Group childGroup11 = GroupMapper.mapEntityToDTO(childEntity11);
        assertNotNull(childGroup11);

        // check children
        assertNotNull(childGroup11.getChildGroups());
        assertEquals(childGroup11.getChildGroups().size(), 0);

        // check parents
        assertNotNull(childGroup11.getParentGroup());
        assertEquals(childGroup11.getParentGroup(), childGroup1);
    }
}
