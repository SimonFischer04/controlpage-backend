package eu.fischerserver.controlpage.controlpagebackend.integration.util.mapper.groupmapper;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.Group;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.BasicView;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.FullView;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.GroupEntity;
import eu.fischerserver.controlpage.controlpagebackend.util.DummyDomainUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GroupMapperDTOToEntityTest {
    @Autowired
    private ConversionService conversionService;

    /*
        Note: i know this is kind of code duplication to the GroupMapperDTOToEntityTest, but building a "framework" (superclass,...)
        would create more overhead / is over-engineered
     */

    private static Group child11, child1, child2, head;

    @BeforeAll
    static void init() {
        /*
            init for Group -> GroupEntity
         */
        head = DummyDomainUtils.getDummyGroupTreeHead();
        child1 = head.childGroups().get(0);
        child11 = child1.childGroups().get(0);
        child2 = head.childGroups().get(1);
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
            group.views().addAll(List.of(v1, v2, v3));

            GroupEntity groupEntity = conversionService.convert(group, GroupEntity.class);
            assertNotNull(groupEntity);
            assertNotNull(groupEntity.getViews());
            assertEquals(3, groupEntity.getViews().size());
        }
    }

    /**
     * Test DTOToEntity mapper with a group depth of 3 -> test layer 1
     */
    @Test
    void testMapDTOToEntityLayer1() {
        /*
            Test 1: mapping head group Entity
         */
        {
            GroupEntity headGroupEntity = conversionService.convert(head, GroupEntity.class);
            assertNotNull(headGroupEntity);
            assertEquals(headGroupEntity.getName(), "HeadGroup");

            assertNotNull(headGroupEntity.getChildGroups());
            assertEquals(2, headGroupEntity.getChildGroups().size());
        }
    }

    /**
     * Test DTOToEntity mapper with a group depth of 3 -> test layer 2
     */
    @Test
    void testMapDTOToEntityLayer2() {
        GroupEntity headGroupEntity = conversionService.convert(head, GroupEntity.class);
        GroupEntity childGroup1Entity = conversionService.convert(child1, GroupEntity.class);

        /*
            Test 2a: mapping a child in layer 2 (childEntity1) with children
         */
        {
            assertNotNull(childGroup1Entity);
            assertEquals(child1.name(), childGroup1Entity.getName());

            // check children.
            assertNotNull(childGroup1Entity.getChildGroups());
            assertEquals(1, childGroup1Entity.getChildGroups().size());

            // check parent group
            assertNotNull(childGroup1Entity.getParentGroup());
            assertEquals(headGroupEntity, childGroup1Entity.getParentGroup());
        }

        /*
            Test 2b: mapping a child in layer 2 (childEntity2) empty children
         */
        {
            GroupEntity childGroup2 = conversionService.convert(child2, GroupEntity.class);
            assertNotNull(childGroup2);
            assertEquals(child2.name(), childGroup2.getName());

            // check children.
            assertNotNull(childGroup2.getChildGroups());
            assertEquals(0, childGroup2.getChildGroups().size());

            // check parent group
            assertNotNull(childGroup2.getParentGroup());
            assertEquals(headGroupEntity, childGroup2.getParentGroup());
        }
    }

    /**
     * Test DTOToEntity mapper with a group depth of 3 -> test layer 3
     */
    @Test
    void testMapToDTOLayer3() {
        GroupEntity childGroup1Entity = conversionService.convert(child1, GroupEntity.class);

        /*
            Test 3: mapping a child in layer 3
         */
        {
            GroupEntity childGroup11 = conversionService.convert(child11, GroupEntity.class);
            assertNotNull(childGroup11);

            // check parents
            assertNotNull(childGroup11.getParentGroup());
            assertEquals(childGroup1Entity, childGroup11.getParentGroup());
        }
    }
}
