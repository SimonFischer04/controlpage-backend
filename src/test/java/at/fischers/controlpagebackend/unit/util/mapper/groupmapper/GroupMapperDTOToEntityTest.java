package at.fischers.controlpagebackend.unit.util.mapper.groupmapper;

import at.fischers.controlpagebackend.dto.Group;
import at.fischers.controlpagebackend.entity.GroupEntity;
import at.fischers.controlpagebackend.util.mapper.groupmapper.GroupMapperDTOToEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GroupMapperDTOToEntityTest {
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
     * Test DTOToEntity mapper with a group depth of 3 -> test layer 1
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
        GroupEntity headGroupEntity = GroupMapperDTOToEntity.mapDTOToEntity(head);
        assertNotNull(headGroupEntity);
        assertEquals(headGroupEntity.getName(), "HeadGroup");

        assertNotNull(headGroupEntity.getChildGroups());
        assertEquals(headGroupEntity.getChildGroups().size(), 2);
        //-----------------------------------------------------------------
    }

    /**
     * Test DTOToEntity mapper with a group depth of 3 -> test layer 2
     */
    @Test
    void testMapToDTOLayer2() {
        GroupEntity headGroupEntity = GroupMapperDTOToEntity.mapDTOToEntity(head);
        GroupEntity childGroup1Entity = GroupMapperDTOToEntity.mapDTOToEntity(child1);

        /*
            Test 2a: mapping a child in layer 2 (childEntity1) with children
         */
        assertNotNull(childGroup1Entity);
        assertEquals(childGroup1Entity.getName(), "Child1");

        // check children.
        assertNotNull(childGroup1Entity.getChildGroups());
        assertEquals(childGroup1Entity.getChildGroups().size(), 1);

        // check parent group
        assertNotNull(childGroup1Entity.getParentGroup());
        assertEquals(childGroup1Entity.getParentGroup(), headGroupEntity);

        /*
            Test 2b: mapping a child in layer 2 (childEntity2) empty children
         */
        GroupEntity childGroup2 = GroupMapperDTOToEntity.mapDTOToEntity(child2);
        assertNotNull(childGroup2);
        assertEquals(childGroup2.getName(), "Child2");

        // check children.
        // childGroups should be an empty list and not null!
        assertNotNull(childGroup2.getChildGroups());
        assertEquals(childGroup2.getChildGroups().size(), 0);

        // check parent group
        assertNotNull(childGroup2.getParentGroup());
        assertEquals(childGroup2.getParentGroup(), headGroupEntity);
        //------------------------------------------------------------------------------
    }

    /**
     * Test DTOToEntity mapper with a group depth of 3 -> test layer 3
     */
    @Test
    void testMapToDTOLayer3() {
        GroupEntity childGroup1Entity = GroupMapperDTOToEntity.mapDTOToEntity(child1);

        /*
            Test 3: mapping a child in layer 3
         */
        GroupEntity childGroup11 = GroupMapperDTOToEntity.mapDTOToEntity(child11);
        assertNotNull(childGroup11);

        // check children
        assertNotNull(childGroup11.getChildGroups());
        assertEquals(childGroup11.getChildGroups().size(), 0);

        // check parents
        assertNotNull(childGroup11.getParentGroup());
        assertEquals(childGroup11.getParentGroup(), childGroup1Entity);
    }
}
