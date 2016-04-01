package be.beme.schn.vaadin;


import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Dorito on 12-03-16.
 * This class is reusable in others Vaadin applications
 */
public final class VaadinUtils {

    private VaadinUtils()
    {

    }

    public static Component findComponentById(HasComponents root, String id) {

        for(Component child : root) {
            if(id.equals(child.getId())) {

                return child;
            }
            else if(child instanceof HasComponents) {

                Component ret= findComponentById((HasComponents) child, id);
                if(ret!=null)
                    return ret;
            }
        }
        // none was found
        return null;
    }

    public static Image getInternalImageResource(String imageName)
    {

        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

        FileResource imgres = new FileResource(new File(basepath + "/WEB-INF/images/"+imageName));
        Image image= new Image(null, imgres);

        return image;
    }

    public static Window findWindowById(String id)
    {
        ArrayList<Window> arrayList=new ArrayList<>(UI.getCurrent().getWindows());

        for(Window window: arrayList)
        {
            if(window.getId().equals(id))
            {
                return window;
            }
        }

        return null;

    }
}
