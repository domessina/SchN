package be.beme.schn;

import com.vaadin.event.Action;

/**
 * Created by Dorito on 01-04-16.
 */
public final class Constants {

    private Constants() {

    }

    //System
    //    public static final String BASE_DIR="C:\\Users\\Dorito\\TFEFiles\\NarrativeScheme\\";
    public static final String BASE_DIR = "D:\\Téléchargements\\TFEFiles\\NarrativeScheme\\";

    //UI
    public static final Action ACTION_ADD = new Action("Add");
    public static final Action ACTION_DELETE = new Action("Delete");
    public static final Action ACTION_MODIFY = new Action("Modify");
    public static final int TEXTAREA_ROWS_SCENE=35;
    public static final int TEXTAREA_ROWS_CHARACTER=20;
    public static final int TEXTAREA_ROWS_CHAPTER=20;

    //Cookies
    public static final String CK_USER_ID = "userId";
    public static final String CK_DIAGRAM_ID = "diagramId";

    //Social Platform
    public static final String SOCIAL_HTTP_PARAM_USER_ID = "USER_ID";
    public static final boolean SOCIAL_COMPATIBILITY = false;
    public static final String SOCIAL_URL = "www.google.be";

    //USER MSG
    public static final String MSG_DELETE_DIAGRAM = "This will delete all sub-contents of this diagram";
    public static final String MSG_SAVE_CONTENT_BEFORE = "Make sure to save any modifications before to leave";
    public static final String MSG_REQUIRED_FIELD_INFO ="Required fields not filled";
    public static final String MSG_REPORT_SENT = "A report was sent to the developers";
    public static final String MSG_SYS_ERR = "System error";


}
