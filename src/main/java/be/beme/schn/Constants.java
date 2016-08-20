package be.beme.schn;

import com.vaadin.event.Action;

/**
 * Created by Dorito on 01-04-16.
 */
public final class Constants {

    private Constants() {

    }

    //    public static final String BASE_DIR="C:\\Users\\Dorito\\TFEFiles\\NarrativeScheme\\";
    public static final String BASE_DIR = "D:\\Téléchargements\\TFEFiles\\NarrativeScheme\\";
    public static final String REPORT_SENT = "A report was sent to the developers";
    public static final String SYS_ERR = "System error";
    public static final Action ACTION_ADD = new Action("Add");
    public static final Action ACTION_DELETE = new Action("Delete");
    public static final Action ACTION_MODIFY = new Action("Modify");

    //Cookies
    public static final String CK_USER_ID = "userId";
    public static final String CK_DIAGRAM_ID = "diagramId";

    //Social Platform
    public static final String HTTP_PARAM_USER_ID = "USER_ID";
    public static final boolean WORKING_WITH_SOCIAL = false;
    public static final String SOCIAL_URL = "www.google.be";

    //Windows
    public static final String WINDOW_DELETE_DIAGRAM_MSG = "This will delete all sub-contents of this diagram";
    public static final String WINDOW_SAVE_CONTENT_BEFORE = "Make sure to save any modifications before to leave";

}
