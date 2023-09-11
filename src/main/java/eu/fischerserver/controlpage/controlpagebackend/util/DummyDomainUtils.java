package eu.fischerserver.controlpage.controlpagebackend.util;

import eu.fischerserver.controlpage.controlpagebackend.model.domain.Field;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.Group;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.Image;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.action.ViewAction;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.text.HorizontalAlignment;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.text.StyledText;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.text.VerticalAlignment;
import eu.fischerserver.controlpage.controlpagebackend.model.domain.view.FullView;
import eu.fischerserver.controlpage.controlpagebackend.model.global.action.RunPolicy;
import eu.fischerserver.controlpage.controlpagebackend.model.global.action.ViewActionType;

import java.util.ArrayList;
import java.util.List;

public class DummyDomainUtils {

    /*
        Domain
     */

    /**
     * Group tree:
     * Layer       |               Tree
     * 1)          |   [id:  0] headGroup
     * |       |----------------------------------|
     * 2)          |   [id:  1] childGroup1            [id: 2] childGroup2
     * |       |
     * 3)          |   [id: 11] childGroup11
     *
     * @return The head Group node of the tree.
     */
    public static Group getDummyGroupTreeHead() {
        var head = Group.builder()
                .id(0)
                .name("HeadGroup")
                .childGroups(new ArrayList<>())
                .views(new ArrayList<>())
                .build();
        head.views().add(getDummyFullView(head));

        var child1 = Group.builder()
                .id(1)
                .name("Child 1")
                .parentGroup(head)
                .childGroups(new ArrayList<>())
                .build();
        head.childGroups().add(child1);

        var child11 = Group.builder()
                .id(11)
                .name("Child 11")
                .parentGroup(child1)
                .build();
        child1.childGroups().add(child11);

        var child2 = Group.builder()
                .id(2)
                .name("Child 2")
                .parentGroup(head)
                .childGroups(new ArrayList<>())
                .build();
        head.childGroups().add(child2);

        return head;
    }

    public static FullView getDummyFullView() {
        return getDummyFullView(null);
    }

    /*
        Dummy View with 4*2 field matrix:
            F1 F2 F3 F4
            F5 F6 F7 F8
     */
    private static FullView getDummyFullView(Group group) {
        if (group == null) {
            group = getDummyGroupTreeHead();
        }
        final var id = getRandomId();
        var fullView = FullView.fullBuilder()
                .id(id)
                .name("View %d".formatted(id))
                .group(group)
                .fields(new ArrayList<>(

                ))
                .build();

        fullView.getFields().addAll(List.of(
                new ArrayList<>(List.of(
                        getDummyField(null, fullView),
                        getDummyField(null, fullView),
                        getDummyField(null, fullView),
                        getDummyField(null, fullView)
                )),
                new ArrayList<>(List.of(
                        getDummyField(null, fullView),
                        getDummyField(null, fullView),
                        getDummyField(null, fullView),
                        getDummyField(null, fullView)
                ))
        ));

        return fullView;
    }

    public static Field getDummyField() {
        return getDummyField(null, null);
    }

    public static Field getDummyField(Integer predefinedId) {
        return getDummyField(predefinedId, null);
    }

    private static Field getDummyField(Integer predefinedId, FullView view) {
        final var id = predefinedId != null ? predefinedId : getRandomId();
        var builder = Field.builder()
                .id(id)
                .action(getDummyViewAction())
                .title(getDummyStyledText())
                .description("A Very Nice Field description of Field %d".formatted(id))
                .background(getDummyImage())
                .rowspan(4)
                .colspan(2);

        if (view != null) {
            builder.view(view);
        }

        return builder.build();
    }

    public static ViewAction getDummyViewAction() {
        return ViewAction.viewActionBuilder()
                .id(getRandomId())
                .runPolicy(RunPolicy.ASYNC)
                .viewActionType(ViewActionType.SWITCH_TO)
                .viewId(getRandomId())
                .build();
    }

    // TODO: other actions

    public static StyledText getDummyStyledText() {
        final var id = getRandomId();
        return StyledText.builder()
                .id(id)
                .text("Some Text %d".formatted(id))
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER)
                .color("#424242")
                .build();
    }

    public static Image getDummyImage() {
        final var id = getRandomId();
        return Image.builder()
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
