package be.beme.schn.vaadin;

import com.google.gwt.thirdparty.guava.common.io.Files;
import com.vaadin.server.FileResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.ui.*;
import org.apache.tomcat.util.http.fileupload.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by Dorito on 28-03-16.
 */
public class ImageUploadPanel extends CustomComponent implements Upload.Receiver{

    private File file;
    private File origImage;
    private String path;
    private Upload upload;
    private Image image;
    private String fileName;
    private File tmpdir;
    private HorizontalLayout btnLayout;
    private boolean imageEmpty;

    public ImageUploadPanel(String path, String fileName)
    {
        //super();              //même si tu ne l'écrit pas il l'appelle
        this.path = path;
        this.fileName=fileName;

        VerticalLayout layout= new VerticalLayout();
        setSizeFull();
        layout.setSizeFull();
        layout.addComponent(buildImagePanel());
        layout.setExpandRatio(image,7f);
        layout.addComponent(buildBtnLayout());
        layout.setExpandRatio(btnLayout,3f);
        setCompositionRoot(layout);
    }


    @Override
    public OutputStream receiveUpload(String filename, String mimeType)             //C'est chiant car Vaadin ne donne pas accès au fichier qui est upload. Il le rajoute au fos que tu retournes après
    {
        if(!(mimeType.contains("jpeg")||mimeType.contains("png")))
        {
            new Notification("The accepted formats are JPEG and PNG", Notification.Type.HUMANIZED_MESSAGE).show(Page.getCurrent());  //TODO fair le redimentionnement si>850px voir rapport
            return null;
        }

        if(tmpdir==null)
        {
            tmpdir =Files.createTempDir();
        }
        FileOutputStream fos = null;
        UUID imageId= UUID.randomUUID();
        this.fileName=imageId.toString()+filename.substring(filename.lastIndexOf("."));
      //Si on nomme toutes les images upliadées pareil comme "image.gpg" , alros malgré quel 'on raffraichisse la page le browser garde toujours l'ancienne. Il faut un nom différent puis reload


        try {

            file= new File(tmpdir.getPath(),this.fileName);
            fos = new FileOutputStream(file);
        }
        catch (final Exception e)
        {
            new Notification("Could not upload", e.getMessage(), Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
            return null;
        }

        return fos;
    }

    private Layout buildBtnLayout()
    {
        btnLayout = new HorizontalLayout();
        Button erase = new Button(FontAwesome.ERASER);
        erase.addClickListener(event -> {
            this.file=null;
                image.setSource(null);
            this.fileName=null;
            this.imageEmpty=true;
        });
        btnLayout.addComponent(buildUpload());
        btnLayout.addComponent(erase);
        btnLayout.setWidth(100,Unit.PERCENTAGE);
        btnLayout.setComponentAlignment(upload,Alignment.BOTTOM_LEFT);
        btnLayout.setComponentAlignment(erase,Alignment.TOP_RIGHT);
        return btnLayout;
    }

    private Upload buildUpload()
    {
        upload= new Upload("",this);
        upload.setImmediate(true);
        upload.setButtonCaption("Browse picture...");
        upload.addSucceededListener(event -> {

            //changing the imageSource refresh the image in the browser
           this.image.setSource(new FileResource(this.file));
        });
        return upload;
    }

    private Image buildImagePanel()
    {
        image = new Image();
        image.setImmediate(true);
        image.setWidth(100,Unit.PERCENTAGE);

        if((this.fileName!=null)&&(this.path !=null))  //it's null when we instantiate a new empty character. It's says "only load the image if the fileName and path are not null"
        {
            file = new File(this.path +this.fileName);
            origImage=file;
            Resource resource= new FileResource(file);
            image.setSource(resource);
            imageEmpty=false;
        }

        return image;
    }

    public void commit()
    {
        try {
            if(!file.getPath().equals(this.path+this.fileName))
                Files.move(file,new File(this.path+this.fileName));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch(NullPointerException e)
        {
            System.out.println("No image to save");

        }
        if(isImageEmpty())
        {
            deleteOrigImage();
        }

    }

    public void deleteTmpDir()
    {
        if(this.tmpdir!=null)
        {
            try {
                FileUtils.deleteDirectory(this.tmpdir);
                this.tmpdir=null;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void deleteImage()
    {
        try{
            this.file.delete();
        }
        catch(NullPointerException e)
        {
            System.out.println("There is no file image to delete");
        }
    }

    public String getFileName(){
        return this.fileName;
    }

    private void deleteOrigImage()
    {
        try{
            this.origImage.delete();
        }
        catch(NullPointerException e)
        {
            System.out.println("Failed to delete image---Image doesn't exist");
        }
    }

    public void setPath(String path){
        this.path=path;
    }

    public boolean isImageEmpty() {
        return imageEmpty;
    }
}