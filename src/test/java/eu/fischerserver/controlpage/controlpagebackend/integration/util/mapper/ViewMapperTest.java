package eu.fischerserver.controlpage.controlpagebackend.integration.util.mapper;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.BasicView;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.FullView;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.FieldEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.GroupEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.ViewEntity;
import eu.fischerserver.controlpage.controlpagebackend.util.DummyDomainUtils;
import eu.fischerserver.controlpage.controlpagebackend.util.DummyEntityUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ViewMapperTest {
    @Autowired
    ConversionService conversionService;

    /**
     * Test: mapping a {@link FieldEntity} to a {@link BasicView}
     */
    @Test
    void testMappingEntityToBasicDTO() {
        /*
            Test 1: some basic mapping test
         */
        {
            GroupEntity groupEntity = new GroupEntity(0, new ArrayList<>(), null, "TestGroup", null);
            ViewEntity viewEntity = new ViewEntity(42, "TestView", groupEntity, null);

            BasicView basicView = conversionService.convert(viewEntity, BasicView.class);
            assertNotNull(basicView);
            assertEquals(42, basicView.getId());
            assertEquals("TestView", basicView.getName());
            assertNotNull(basicView.getGroup());
        }
    }

    /**
     * Test: mapping a {@link FieldEntity} to a {@link FullView}
     */
    @Test
    void testMappingEntityToFullDTO() {
        /*
            Test 1: mapping ViewEntity with Fields set
         */
        {
            ViewEntity viewEntity = DummyEntityUtils.getDummyViewEntity();

            FullView fullView = conversionService.convert(viewEntity, FullView.class);
            assertNotNull(fullView);
            assertEquals(viewEntity.getId(), fullView.getId());
            assertEquals(viewEntity.getName(), fullView.getName());
            assertNotNull(fullView.getGroup());
            assertNotNull(fullView.getFields());
            // get (y) . get (x)
            Assertions.assertEquals(viewEntity.getFields().get(0).getTitle().getText(), fullView.getFields().get(0).get(0).title().text());
            Assertions.assertEquals(viewEntity.getFields().get(1).getTitle().getText(), fullView.getFields().get(0).get(1).title().text());
            Assertions.assertEquals(viewEntity.getFields().get(2).getTitle().getText(), fullView.getFields().get(0).get(2).title().text());
            Assertions.assertEquals(viewEntity.getFields().get(3).getTitle().getText(), fullView.getFields().get(0).get(3).title().text());
            Assertions.assertEquals(viewEntity.getFields().get(4).getTitle().getText(), fullView.getFields().get(1).get(0).title().text());
            Assertions.assertEquals(viewEntity.getFields().get(5).getTitle().getText(), fullView.getFields().get(1).get(1).title().text());
            Assertions.assertEquals(viewEntity.getFields().get(6).getTitle().getText(), fullView.getFields().get(1).get(2).title().text());
            Assertions.assertEquals(viewEntity.getFields().get(7).getTitle().getText(), fullView.getFields().get(1).get(3).title().text());
        }

        /*
            Test 2: mapping ViewEntity with fields null (in fact should not be null when fetched from database - should be empty list, but just in case...) creates NullPointerException?
         */
        {
            assertDoesNotThrow(() -> {
                conversionService.convert(new ViewEntity(42, "V2", null, null), FullView.class);
            });
        }
    }

    /**
     * Test: mapping a {@link FullView} (includes testing of {@link BasicView}) to a {@link ViewEntity}
     */
    @Test
    void testMappingDTOToEntity() {
        /*
            Test 1: mapping View with fields set
         */
        {
            FullView fullView = DummyDomainUtils.getDummyFullView();

            ViewEntity viewEntity = conversionService.convert(fullView, ViewEntity.class);

            assertNotNull(viewEntity);
            assertNotNull(viewEntity.getGroup());
            assertEquals(fullView.getGroup().name(), viewEntity.getGroup().getName());
            // TODO: why would this be necessary to be set actually?
//            assertEquals(viewEntity, viewEntity.getGroup().getViews().get(0));

            List<FieldEntity> fieldEntities = viewEntity.getFields();
            assertNotNull(fieldEntities);
            assertEquals(8, fieldEntities.size());

            assertEquals(fullView.getFields().get(0).get(0).title().text(), fieldEntities.get(0).getTitle().getText());
            assertEquals(0, fieldEntities.get(0).getYPos());
            assertEquals(0, fieldEntities.get(0).getXPos());

            assertEquals(fullView.getFields().get(0).get(1).title().text(), fieldEntities.get(1).getTitle().getText());
            assertEquals(0, fieldEntities.get(1).getYPos());
            assertEquals(1, fieldEntities.get(1).getXPos());

            assertEquals(fullView.getFields().get(0).get(2).title().text(), fieldEntities.get(2).getTitle().getText());
            assertEquals(0, fieldEntities.get(2).getYPos());
            assertEquals(2, fieldEntities.get(2).getXPos());

            assertEquals(fullView.getFields().get(0).get(3).title().text(), fieldEntities.get(3).getTitle().getText());
            assertEquals(0, fieldEntities.get(3).getYPos());
            assertEquals(3, fieldEntities.get(3).getXPos());

            assertEquals(fullView.getFields().get(1).get(0).title().text(), fieldEntities.get(4).getTitle().getText());
            assertEquals(1, fieldEntities.get(4).getYPos());
            assertEquals(0, fieldEntities.get(4).getXPos());

            assertEquals(fullView.getFields().get(1).get(1).title().text(), fieldEntities.get(5).getTitle().getText());
            assertEquals(1, fieldEntities.get(5).getYPos());
            assertEquals(1, fieldEntities.get(5).getXPos());

            assertEquals(fullView.getFields().get(1).get(2).title().text(), fieldEntities.get(6).getTitle().getText());
            assertEquals(1, fieldEntities.get(6).getYPos());
            assertEquals(2, fieldEntities.get(6).getXPos());

            assertEquals(fullView.getFields().get(1).get(3).title().text(), fieldEntities.get(7).getTitle().getText());
            assertEquals(1, fieldEntities.get(7).getYPos());
            assertEquals(3, fieldEntities.get(7).getXPos());
        }

        /*
            Test 2: mapping View with fields null (in fact should not be null when fetched from database - should be empty list, but just in case...) creates NullPointerException?
         */
        {
            assertDoesNotThrow(() -> {
                conversionService.convert(new FullView(42, "V2", null, null), ViewEntity.class);
            });
        }
    }
}
