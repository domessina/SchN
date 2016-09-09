package be.beme.schn.vaadin;

import be.beme.schn.Constants;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ErrorEvent;
import com.vaadin.ui.Notification;

/**
 * Created by Dotista on 21-08-16.
 */
public class CustomErrorHandler extends DefaultErrorHandler {

    @Override
    public void error(ErrorEvent event){
        Notification.show(Constants.MSG_SYS_ERR,Constants.MSG_REPORT_SENT, Notification.Type.ERROR_MESSAGE);
        event.getThrowable().printStackTrace();
    }
}
