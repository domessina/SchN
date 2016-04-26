package be.beme.schn.vaadin.dd;


import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.GridLayout;

/**
 * Created by Dotista on 23-04-16.
 */
public class DDGridLayout extends CustomComponent {
    public final GridLayout layout;
    private final GridLayoutDropHandler dropHandler;

    public DDGridLayout() {
        layout = new GridLayout();
        dropHandler = new GridLayoutDropHandler(layout);

        final DragAndDropWrapper pane = new DragAndDropWrapper(layout);
        setCompositionRoot(pane);
    }

    public void addComponent(final Component component) {
        final CustomDragAndDropWrapper wrapper = new CustomDragAndDropWrapper(component,
                dropHandler);

        layout.addComponent(wrapper);
    }

    public void addDropListener(Listener listener)
    {

       dropHandler.addDropListener(listener);

    }

    public GridLayout getLayout()
    {
        return this.layout;
    }


}