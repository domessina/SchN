package be.beme.schn.vaadin.dd;

import com.vaadin.event.dd.DropHandler;
import com.vaadin.ui.Component;
import com.vaadin.ui.DragAndDropWrapper;

/**
 * Created by Dotista on 23-04-16.
 */
public class CustomDragAndDropWrapper extends DragAndDropWrapper {

    private final DropHandler dropHandler;

    public CustomDragAndDropWrapper(final Component content,
                                    final DropHandler dropHandler) {
        super(content);
        this.dropHandler = dropHandler;
        setDragStartMode(DragAndDropWrapper.DragStartMode.WRAPPER);

    }

    public Component getCompositionRoot()
    {
        return super.getCompositionRoot();
    }

    @Override
    public DropHandler getDropHandler() {
        return dropHandler;
    }

}