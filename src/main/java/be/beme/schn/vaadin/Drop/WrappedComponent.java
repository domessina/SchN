package be.beme.schn.vaadin.Drop;

import com.vaadin.event.dd.DropHandler;
import com.vaadin.ui.Component;
import com.vaadin.ui.DragAndDropWrapper;

/**
 * Created by Dotista on 23-04-16.
 */
public class WrappedComponent extends DragAndDropWrapper {

    private final DropHandler dropHandler;

    public WrappedComponent(final Component content,
                            final DropHandler dropHandler) {
        super(content);
        this.dropHandler = dropHandler;
        setDragStartMode(DragAndDropWrapper.DragStartMode.WRAPPER);
    }

    @Override
    public DropHandler getDropHandler() {
        return dropHandler;
    }

}