package eu.fischerserver.controlpage.controlpagebackend.util;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.text.HorizontalAlignment;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.text.VerticalAlignment;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.*;
import eu.fischerserver.controlpage.controlpagebackend.model.entity.action.ViewActionEntity;
import eu.fischerserver.controlpage.controlpagebackend.model.global.action.RunPolicy;
import eu.fischerserver.controlpage.controlpagebackend.model.global.action.ViewActionType;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.List;

public class DummyEntityUtils {
    /*
        Entity
     */

    /**
     * Group tree:
     * Layer       |               Tree
     * 1)          |   [id:  0] headEntity
     * |       |------------------------------------|
     * 2)          |   [id:  1] childEntity1            [id: 2] childEntity2
     * |       |
     * 3)          |   [id: 11] childEntity11
     *
     * @return The head GroupEntity node of the tree.
     */
    public static GroupEntity getDummyGroupEntityTreeHead() {
        return getDummyGroupEntityTreeHead(null, true);
    }

    public static GroupEntity getDummyGroupEntityTreeHead(Integer idEverywhere, boolean includingViews) {
        final var childEntity11 = new GroupEntity(idEverywhere != null ? idEverywhere : 11, null, null, "Child 11", null);
        final var childEntity1 = new GroupEntity(idEverywhere != null ? idEverywhere : 1, new ArrayList<>(List.of(childEntity11)), null, "Child 1", null);
        // TODO: not required because some DB magic??? - analyse this!
        childEntity11.setParentGroup(childEntity1);

        final var childEntity2 = new GroupEntity(idEverywhere != null ? idEverywhere : 2, null, null, "Child 2", null);

        final var headEntity = new GroupEntity(idEverywhere != null ? idEverywhere : 0, new ArrayList<>(List.of(childEntity1, childEntity2)), null, "HeadGroup", new ArrayList<>());
        // TODO: not required because some DB magic??? - analyse this!
        childEntity1.setParentGroup(headEntity);
        childEntity2.setParentGroup(headEntity);

        if (includingViews)
            headEntity.getViews().add(getDummyViewEntity(headEntity, null));

        return headEntity;
    }

    /**
     * Utility function to get a ViewEntity with some dummy-data.
     *
     * @param idEverywhere - Allow to pre-define an id EVERYWHERE! f.e. db save needs id 0
     * @return The dummy ViewEntity
     */
    public static ViewEntity getDummyViewEntity(Integer idEverywhere) {
        return getDummyViewEntity(null, idEverywhere);
    }

    public static ViewEntity getDummyViewEntity() {
        return getDummyViewEntity(null, null);
    }

    /*
        Dummy View with 4*2 field matrix (transformed):
            F1 F2 F3 F4
            F5 F6 F7 F8
            =>
            F1 F2 F3 F4 F5 F6 F7 F8
    */
    private static ViewEntity getDummyViewEntity(GroupEntity groupEntity, Integer idEverywhere) {
        if (groupEntity == null) {
            groupEntity = getDummyGroupEntityTreeHead(idEverywhere, true);
        }
        final var id = idEverywhere != null ? idEverywhere : getRandomId();
        var viewEntity = ViewEntity.builder()
                .id(id)
                .name("ViewEntity %d".formatted(id))
                .group(groupEntity)
                .fields(new ArrayList<>())
                .build();

        viewEntity.getFields().addAll(List.of(
                getDummyFieldEntity(0, 0, null, viewEntity, idEverywhere),
                getDummyFieldEntity(1, 0, null, viewEntity, idEverywhere),
                getDummyFieldEntity(2, 0, null, viewEntity, idEverywhere),
                getDummyFieldEntity(3, 0, null, viewEntity, idEverywhere),
                getDummyFieldEntity(0, 1, null, viewEntity, idEverywhere),
                getDummyFieldEntity(1, 1, null, viewEntity, idEverywhere),
                getDummyFieldEntity(2, 1, null, viewEntity, idEverywhere),
                getDummyFieldEntity(3, 1, null, viewEntity, idEverywhere)
        ));

        return viewEntity;
    }

    public static FieldEntity getDummyFieldEntity() {
        return getDummyFieldEntity(-1, -1, null, null, null);
    }

    public static FieldEntity getDummyFieldEntity(Integer predefinedId) {
        return getDummyFieldEntity(-1, -1, predefinedId, null, null);
    }

    private static FieldEntity getDummyFieldEntity(int xPos, int yPos, Integer predefinedId, ViewEntity viewEntity, Integer idEverywhere) {
        var id = getRandomId();

        if (idEverywhere != null)
            id = idEverywhere;

        if (predefinedId != null)
            id = predefinedId;

        return FieldEntity.builder()
                .id(id)
                .view(viewEntity)
                .action(getDummyViewActionEntity(idEverywhere))
                .title(getDummyStyledTextEntity(idEverywhere))
                .description("A Very Nice FieldEntity description of Field %d".formatted(id))
                .background(getDummyImageEntity(idEverywhere))
                .rowspan(4)
                .colspan(2)
                .xPos(xPos)
                .yPos(yPos)
                .build();
    }


    public static ViewActionEntity getDummyViewActionEntity() {
        return getDummyViewActionEntity(null);
    }

    private static ViewActionEntity getDummyViewActionEntity(Integer idEverywhere) {
        return ViewActionEntity.viewActionEntityBuilder()
                .id(idEverywhere != null ? idEverywhere : getRandomId())
                .runPolicy(RunPolicy.ASYNC)
                .viewActionType(ViewActionType.SWITCH_TO)
                .viewId(getRandomId())
                .build();
    }

    // TODO: other actions

    public static StyledTextEntity getDummyStyledTextEntity() {
        return getDummyStyledTextEntity(null);
    }

    private static StyledTextEntity getDummyStyledTextEntity(Integer idEverywhere) {
        final var id = idEverywhere != null ? idEverywhere : getRandomId();
        return StyledTextEntity.builder()
                .id(id)
                .text("Some Text %d".formatted(id))
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER)
                .color("#424242")
                .build();
    }

    public static ImageEntity getDummyImageEntity() {
        return getDummyImageEntity(null);
    }

    private static ImageEntity getDummyImageEntity(Integer idEverywhere) {
        final var id = idEverywhere != null ? idEverywhere : getRandomId();
        return ImageEntity.builder()
                .id(id)
                .name("A Very Nice Image %d".formatted(id))
                .type("image/png")
                .imageData(new byte[]{21, 42, 69})
                .build();
    }

    /*
        Other
     */

    private static int getRandomId() {
        return (int) (Math.random() * Integer.MAX_VALUE);
    }
}
