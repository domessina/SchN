package be.beme.schn.vaadin.dd;

import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.DropTarget;
import com.vaadin.event.dd.TargetDetails;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.event.dd.acceptcriteria.Not;
import com.vaadin.event.dd.acceptcriteria.SourceIsTarget;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;

import java.util.ArrayList;

/**
 * Created by Dotista on 23-04-16.
 */
public class GridLayoutDropHandler implements DropHandler {

    private final GridLayout layout;
    private int xdest;
    private int ydest;
    private int indexdest;
    private ArrayList<Component.Listener> listeners;

    public GridLayoutDropHandler(final GridLayout layout) {
        this.layout = layout;
        listeners=new ArrayList<>();
    }

    @Override
    public AcceptCriterion getAcceptCriterion() {
        return new Not(SourceIsTarget.get());
    }

    @Override
    public void drop(final DragAndDropEvent dropEvent) {
        final Transferable transferable = dropEvent.getTransferable();
        final Component sourceComponent = transferable.getSourceComponent();

        if (sourceComponent instanceof WrappedComponent)
        {
            final TargetDetails dropTargetData = dropEvent.getTargetDetails();
            final DropTarget target = dropTargetData.getTarget();
            findComponent(target);
            layout.replaceComponent(layout.getComponent(xdest,ydest),sourceComponent);
        }

        fireDropEvent();
    }

    private void findComponent(DropTarget target)
    {
        indexdest=0;
        for(int y=0;y<layout.getRows();y++)
        {
            for(int x=0;x<layout.getColumns();x++)
            {

                if(layout.getComponent(x,y)==target)
                {
                    xdest=x;
                    ydest=y;
                    return;
                }
                indexdest++;
            }
        }
    }

    public void addDropListener(Component.Listener listener)
    {
        listeners.add(listener);
    }

    public void fireDropEvent()
    {
        for(Component.Listener l: listeners)
        {
            l.componentEvent(new GridLayoutDropEvent(this.layout,xdest,ydest,indexdest));
        }
    }
}