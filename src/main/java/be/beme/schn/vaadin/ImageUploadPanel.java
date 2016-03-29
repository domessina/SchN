package be.beme.schn.vaadin;

import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.ui.*;
import org.vaadin.jcrop.Jcrop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by Dorito on 28-03-16.
 */
public class ImageUploadPanel extends VerticalLayout implements Upload.Receiver {

    private File file;
    private Upload upload;
    private Image image;
    private Panel imagePanel;
    private Jcrop jcrop;

    public ImageUploadPanel()
    {
        //super();              //même si tu ne l'écrit pas il l'appelle
        setSizeFull();
        addComponent(buildImagePanel());
        setExpandRatio(imagePanel,7f);
        addComponent(buildUpload());
        setExpandRatio(upload,3f);
    }


    public OutputStream receiveUpload(String filename, String mimeType)
    {
        FileOutputStream fos = null;
        try {
            file = new File("C:\\Users\\Dorito\\TFEImages\\NarrativeScheme\\Users\\Diagrams\\1\\Characters\\1\\"+ filename);
            fos = new FileOutputStream(file);
        }
        catch (final Exception e)
        {
            new Notification("Could not upload", e.getMessage(), Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
            return null;
        }
        return fos;
    }

    private Upload buildUpload()
    {
        upload= new Upload("",this);
        upload.setButtonCaption("☑");
        upload.addSucceededListener(event -> {

            //changing the imageSource refresh the image in the browser
            this.image.setSource(getFileResource());
        });
        return upload;
    }

    private Panel buildImagePanel()
    {
        imagePanel = new Panel();
        imagePanel.setSizeFull();
        imagePanel.setScrollLeft(1111110);
        File file = new File("C:\\Users\\Dorito\\TFEImages\\NarrativeScheme\\Users\\Diagrams\\"+1+"\\Characters\\"+1+"\\image.jpg");
        Resource resource= new FileResource(file);
        image = new Image(null, resource);
        image.setImmediate(true);
        imagePanel.setContent(image);
        return imagePanel;
    }

    public FileResource getFileResource()
    {
        return new FileResource(file);
    }//voir si écrire que le file refresh aussi
}