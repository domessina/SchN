package be.beme.schn.vaadin.Drop;

import com.vaadin.event.dd.DropHandler;
import com.vaadin.ui.*;

/**
 * Created by Dotista on 23-04-16.
 */
public class SortableLayout extends CustomComponent {
    private final AbstractOrderedLayout layout;
    private final DropHandler dropHandler;

    public SortableLayout() {
        layout = new HorizontalLayout();
        dropHandler = new ReorderLayoutDropHandler(layout);

        final DragAndDropWrapper pane = new DragAndDropWrapper(layout);
        setCompositionRoot(pane);
    }

    public void addComponent(final Component component) {
        final WrappedComponent wrapper = new WrappedComponent(component,
                dropHandler);
        wrapper.setSizeUndefined();
        component.setHeight("100%");
        wrapper.setHeight("100%");
        layout.addComponent(wrapper);
    }
}