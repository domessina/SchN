package be.beme.schn.vaadin.dd;

import com.vaadin.event.dd.DropHandler;
import com.vaadin.ui.Component;
import com.vaadin.ui.DragAndDropWrapper;

/**
 * Created by D.Messina on 23-04-16.
 */
public class SwapDragAndDropWrapper extends DragAndDropWrapper {

    private final DropHandler dropHandler;

    public SwapDragAndDropWrapper(final Component content,
                                  final DropHandler dropHandler) {
        super(content);
        this.dropHandler = dropHandler;
        setDragStartMode(DragAndDropWrapper.DragStartMode.WRAPPER);

    }

    /**
     * The purpose of this method is to make public the one from supper class
     * DragAndDropWrapper. That way, we can call it in DDSwapGridLayout
     * **/
    public Component getCompositionRoot()
    {
        return super.getCompositionRoot();
    }

    @Override
    public DropHandler getDropHandler() {
        return dropHandler;
    }

}