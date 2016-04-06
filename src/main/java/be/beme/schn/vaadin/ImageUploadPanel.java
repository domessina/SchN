package be.beme.schn.vaadin;

import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.ui.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by Dorito on 28-03-16.
 */
public class ImageUploadPanel extends VerticalLayout implements Upload.Receiver{

    private File file;
    private String path;
    private Upload upload;
    private Image image;
    private Panel imagePanel;
    private String fileName;

    public ImageUploadPanel(String path, String fileName)
    {
        //super();              //même si tu ne l'écrit pas il l'appelle
        this.path = path;
        this.fileName=fileName;
        setSizeFull();
        addComponent(buildImagePanel());
        setExpandRatio(imagePanel,7f);
        addComponent(buildUpload());
        setExpandRatio(upload,3f);

    }



    public OutputStream receiveUpload(String filename, String mimeType)
    {
        if(!mimeType.contains("image"))
        {
            new Notification("The mimetype must be an image", Notification.Type.HUMANIZED_MESSAGE).show(Page.getCurrent());
            return null;
        }

        FileOutputStream fos = null;
        UUID imageId= UUID.randomUUID();
        this.fileName=imageId.toString()+filename.substring(filename.lastIndexOf("."));
      //Si on nomme toutes les images upliadées pareil comme "image.gpg" , alros malgré quel 'on raffraichisse la page le browser garde toujours l'ancienne. Il faut un nom différent puis reload


        try {
            file = new File(this.path +this.fileName);
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
        upload.setImmediate(true);
        upload.setButtonCaption("Browse...");
        upload.addSucceededListener(event -> {

            //changing the imageSource refresh the image in the browser
            this.image.setSource(new FileResource(this.file));
        });
        return upload;
    }

    private Panel buildImagePanel()
    {
        imagePanel = new Panel();
        imagePanel.setSizeFull();
        imagePanel.setScrollLeft(1111110);
        image = new Image();

        if((this.fileName!=null)&&(this.path !=null))  //it's null when we instantiate a new empty character. It's says "only load the image if the fileName and path are not null"
        {
            file = new File(this.path +this.fileName);
            Resource resource= new FileResource(file);
            image = new Image(null,resource);
        }

        image.setImmediate(true);
        imagePanel.setContent(image);
        return imagePanel;
    }

    public String getFileName(){
        return this.fileName;
    }


}