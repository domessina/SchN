package be.beme.schn.vaadin.dd;

import com.vaadin.event.dd.DropHandler;
import com.vaadin.ui.*;

/**
 * Created by Dotista on 23-04-16.
 */
public class DDGridLayout extends CustomComponent {
    public final GridLayout layout;
    private final DropHandler dropHandler;

    public DDGridLayout() {
        layout = new GridLayout();
        dropHandler = new GridLayoutDropHandler(layout);

        final DragAndDropWrapper pane = new DragAndDropWrapper(layout);
        setCompositionRoot(pane);
    }

    public void addComponent(final Component component) {
        final WrappedComponent wrapper = new WrappedComponent(component,
                dropHandler);

        layout.addComponent(wrapper);
    }
}