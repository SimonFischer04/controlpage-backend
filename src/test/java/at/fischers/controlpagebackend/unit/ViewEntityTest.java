package at.fischers.controlpagebackend.unit;

import at.fischers.controlpagebackend.dto.Group;
import at.fischers.controlpagebackend.dto.view.BasicView;
import at.fischers.controlpagebackend.dto.view.FullView;
import at.fischers.controlpagebackend.entity.ViewEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ViewEntityTest {
    @Test
    void testMappingBasicView() {
        Group childGroup1 = new Group(0, null, null, "TestGroup1", null);
        Group childGroup2 = new Group(0, null, null, "TestGroup2", null);
        Group headGroup = new Group(0, List.of(childGroup1, childGroup2), null, "HeadGroup", null);
        childGroup1.setParentGroup(headGroup);
        childGroup2.setParentGroup(headGroup);

        ViewEntity view = new ViewEntity(new BasicView(0, "TestView", childGroup1));

        assertNotNull(view);
        assertNotNull(view.getGroup());
        assertEquals(view.getGroup().getName(), "TestGroup1");
        assertEquals(view.getGroup().getView(), view);
    }

    void testMappingFullView() {

    }
}
