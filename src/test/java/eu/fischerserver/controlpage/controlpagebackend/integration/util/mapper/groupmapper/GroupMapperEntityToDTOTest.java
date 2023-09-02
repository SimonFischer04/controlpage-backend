package eu.fischerserver.controlpage.controlpagebackend.integration.util.mapper.groupmapper;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.Group;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.GroupEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.ViewEntity;
import eu.fischerserver.controlpage.controlpagebackend.util.DummyEntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GroupMapperEntityToDTOTest {
    @Autowired
    private ConversionService conversionService;

    /*
        Note: i know this is kind of code duplication to the GroupMapperDTOToEntityTest, but building a "framework" (superclass,...)
        would create more overhead / is over-engineered
     */

    private GroupEntity childEntity11, childEntity1, childEntity2, headEntity;

    @BeforeEach
    void init() {
        /*
            init for GroupEntity -> Group
         */
        headEntity = DummyEntityUtils.getDummyGroupEntityTreeHead();
        childEntity1 = headEntity.getChildGroups().get(0);
        childEntity11 = childEntity1.getChildGroups().get(0);
        childEntity2 = headEntity.getChildGroups().get(1);
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
            ViewEntity v1 = new ViewEntity(1, "View1", null, Collections.emptyList());
            ViewEntity v2 = new ViewEntity(2, "View2", null, Collections.emptyList());
            ViewEntity v3 = new ViewEntity(3, "View3", null, Collections.emptyList());
            // TODO: not required because some DB magic??? - analyse this!
            GroupEntity groupEntity = new GroupEntity(1, null, null, "GroupName", List.of(v1, v2, v3));

            Group group = conversionService.convert(groupEntity, Group.class);
            assertNotNull(group);
            assertNotNull(group.views());
            assertEquals(3, group.views().size());
        }
    }

    /**
     * Test EntityToDTO mapper with a group depth of 3 -> test layer 1
     */
    @Test
    void testMapEntityToDTOLayer1() {
        /*

         */


        /*
            Test 1: mapping head group Entity
         */
        Group headGroup = conversionService.convert(headEntity, Group.class);
        assertNotNull(headGroup);
        assertEquals(headGroup.name(), "HeadGroup");

        assertNotNull(headGroup.childGroups());
        assertEquals(2, headGroup.childGroups().size());
        //-----------------------------------------------------------------
    }

    /**
     * Test EntityToDTO mapper with a group depth of 3 -> test layer 2
     */
    @Test
    void testMapToDTOLayer2() {
        Group headGroup = conversionService.convert(headEntity, Group.class);
        Group childGroup1 = conversionService.convert(childEntity1, Group.class);

        /*
            Test 2a: mapping a child in layer 2 (childEntity1) with children
         */
        assertNotNull(childGroup1);
        assertEquals(childEntity1.getName(), childGroup1.name());

        // check children.
        assertNotNull(childGroup1.childGroups());
        assertEquals(1, childGroup1.childGroups().size());

        // check parent group
        assertNotNull(childGroup1.parentGroup());
        assertEquals(headGroup, childGroup1.parentGroup());

        /*
            Test 2b: mapping a child in layer 2 (childEntity2) empty children
         */
        Group childGroup2 = conversionService.convert(childEntity2, Group.class);
        assertNotNull(childGroup2);
        assertEquals(childEntity2.getName(), childGroup2.name());

        // check children.
        assertNull(childGroup2.childGroups());

        // check parent group
        assertNotNull(childGroup2.parentGroup());
        assertEquals(headGroup, childGroup2.parentGroup());
        //------------------------------------------------------------------------------
    }

    /**
     * Test EntityToDTO mapper with a group depth of 3 -> test layer 3
     */
    @Test
    void testMapToDTOLayer3() {
        Group childGroup1 = conversionService.convert(childEntity1, Group.class);

        /*
            Test 3: mapping a child in layer 3
         */
        Group childGroup11 = conversionService.convert(childEntity11, Group.class);
        assertNotNull(childGroup11);

        // check parents
        assertNotNull(childGroup11.parentGroup());
        assertEquals(childGroup1, childGroup11.parentGroup());
    }
}
