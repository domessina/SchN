package be.beme.schn.vaadin.narrative;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by Dotista on 11-04-16.
 */
public class ChapterPVLayout extends VerticalLayout {

    public void enableAllChildren(boolean enabled)
    {
        for(Component c: components)
        {
            c.setEnabled(enabled);
        }
    }

    public void removeChapter(int chapterId)
    {
        removeComponent(getComponentByChapter(chapterId));
    }





    public Component getComponentByChapter(int chapterId)
    {
        for(int i=0;i<getComponentCount();i++)
        {
            if(getComponent(i).getId().equals(String.valueOf(chapterId)))
            {
                return getComponent(i);
            }
        }
        return null;
    }
}
