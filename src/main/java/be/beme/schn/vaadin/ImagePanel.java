package be.beme.schn.vaadin;

import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;

import java.io.File;

/**
 * Created by Dotista on 09-04-16.
 */
public class ImagePanel extends Panel {

    private Image image;

    public ImagePanel()
    {
        setSizeFull();
        setScrollLeft(1111110);
        image = new Image();
        image.setImmediate(true);
        setContent(image);
    }

    public void setImageSource(Resource resource)
    {
        image.setSource(resource);
    }
}
