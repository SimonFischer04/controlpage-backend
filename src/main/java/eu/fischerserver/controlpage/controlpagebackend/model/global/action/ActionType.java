package eu.fischerserver.controlpage.controlpagebackend.model.global.action;

public record ActionType() {
    public static final String UNDEFINED = "UNDEFINED";
    public static final String REST = "REST";
    public static final String VIEW = "VIEW";
    public static final String DESKTOP_AUTOMATION = "DESKTOP_AUTOMATION";
}
