package be.beme.schn.vaadin;

import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by Dorito on 28-03-16.
 */
public class ImageReceiver implements Upload.Receiver{

    public File file;

    public OutputStream receiveUpload(String filename, String mimeType)
    {
        FileOutputStream fos = null;
        try {
            file = new File("C:\\Users\\Dorito\\TFEImages\\NarrativeScheme\\Users\\Diagrams\\1\\Characters\\1\\"+ filename);
            fos = new FileOutputStream(file);
        }
        catch (final Exception e)
        {
            new Notification("Could not upload<br/>", e.getMessage(), Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
            return null;
        }
        return fos;
    }


    public FileResource getFileResource()
    {
        return new FileResource(file);
    }
}