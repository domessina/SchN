package be.beme.schn.vaadin.narrative;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by Dotista on 11-04-16.
 */
public class ChapterPVLayout extends VerticalLayout {

    private int i=0;

    public void enableAllChildren(boolean enabled)
    {
        for(Component c: components)
        {
            c.setEnabled(enabled);
        }
    }

    /**
     * return index in layout of deleted chapter component
     */
    public int removeChapter(int chapterId)
    {
        removeComponent(getComponentByChapter(chapterId));
        return i;
    }




    public Component getComponentByChapter(int chapterId)
    {
        i=0;
        for(i=0;i<getComponentCount();i++)
        {
            if(getComponent(i).getId().equals(String.valueOf(chapterId)))
            {
                return getComponent(i);
            }
        }
        return null;
    }
}
