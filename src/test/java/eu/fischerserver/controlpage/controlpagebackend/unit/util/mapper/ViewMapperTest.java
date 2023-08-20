package eu.fischerserver.controlpage.controlpagebackend.unit.util.mapper;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.Field;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.Group;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.Image;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.text.StyledText;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.BasicView;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.FullView;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.FieldEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.GroupEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.StyledTextEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.ViewEntity;
import eu.fischerserver.controlpage.controlpagebackend.service.ImageService;
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
            GroupEntity groupEntity = new GroupEntity(0, new ArrayList<>(), null, "TestGroup", null);
            List<FieldEntity> fieldEntities = List.of(
                    new FieldEntity(0, null, null, new StyledTextEntity("Field1"), "", null, 1, 1, 1, 1),
                    new FieldEntity(1, null, null, new StyledTextEntity("Field2"), "", null, 1, 1, 0, 0),
                    new FieldEntity(2, null, null, new StyledTextEntity("Field3"), "", null, 1, 1, 1, 0),
                    new FieldEntity(3, null, null, new StyledTextEntity("Field4"), "", null, 1, 1, 0, 1));
            ViewEntity viewEntity = new ViewEntity(42, "TestView", groupEntity, fieldEntities);

            FullView fullView = conversionService.convert(viewEntity, FullView.class);
            assertNotNull(fullView);
            assertEquals(42, fullView.getId());
            assertEquals("TestView", fullView.getName());
            assertNotNull(fullView.getGroup());
            // get (y) . get (x)
            Assertions.assertEquals("Field2", fullView.getFields().get(0).get(0).getTitle().getText());
            Assertions.assertEquals("Field1", fullView.getFields().get(1).get(1).getTitle().getText());
            Assertions.assertEquals("Field3", fullView.getFields().get(0).get(1).getTitle().getText());
            Assertions.assertEquals("Field4", fullView.getFields().get(1).get(0).getTitle().getText());
        }

        // TODO: still relevant with new mapper?
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
            Group childGroup1 = new Group(0, null, null, "TestGroup1", null);
            Group childGroup2 = new Group(1, null, null, "TestGroup2", null);
            Group headGroup = new Group(2, List.of(childGroup1, childGroup2), null, "HeadGroup", null);
            childGroup1.setParentGroup(headGroup);
            childGroup2.setParentGroup(headGroup);
            FullView fullView = new FullView(0, "TestView", childGroup1, null);

            List<List<Field>> fields = new ArrayList<>();
            fields.add(List.of(new Field(5, fullView, null, new StyledText("T1"), "", null, 1, 1), new Field(3, fullView, null, new StyledText("T2"), "", null, 1, 1)));
            fields.add(List.of(new Field(2, fullView, null, new StyledText("T3"), "", null, 1, 1), new Field(1, fullView, null, new StyledText("T4"), "", null, 1, 1)));
            fullView.setFields(fields);

            ViewEntity viewEntity = conversionService.convert(fullView, ViewEntity.class);

            assertNotNull(viewEntity);
            assertNotNull(viewEntity.getGroup());
            assertEquals("TestGroup1", viewEntity.getGroup().getName());
            assertEquals(viewEntity, viewEntity.getGroup().getViews().get(0));

            List<FieldEntity> fieldEntities = viewEntity.getFields();
            assertNotNull(fieldEntities);
            assertEquals(4, fieldEntities.size());

            FieldEntity testFieldT2 = fieldEntities.get(1);
            assertEquals("T2", testFieldT2.getTitle().getText());
            assertEquals(1, testFieldT2.getXPos());
            assertEquals(0, testFieldT2.getYPos());

            FieldEntity testFieldT3 = fieldEntities.get(2);
            assertEquals("T3", testFieldT3.getTitle().getText());
            assertEquals(0, testFieldT3.getXPos());
            assertEquals(1, testFieldT3.getYPos());
        }

        // TODO: still relevant with new mapper?
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
