package be.beme.schn.vaadin.narrative;

import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

/**
 * Created by Dotista on 11-04-16.
 */
public class ChapterPHLayout extends HorizontalLayout {

    public void enableAllChilds(boolean enabled)
    {
        for(Component c: components)
        {
            c.setEnabled(enabled);
        }
    }
}
