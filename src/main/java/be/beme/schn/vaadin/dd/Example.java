package be.beme.schn.vaadin.dd;


import com.vaadin.server.Sizeable;
import com.vaadin.ui.Component;

/**
 * Created by Dorito on 28-08-16.
 */
public class Example  implements Component.Listener{

    SwapDDGridLayout ddGLayout;
    public Example(){
        ddGLayout = new SwapDDGridLayout();
        //Example class must implement Component.Listener
        ddGLayout.addDropListener(this);
        ddGLayout.getGridLayout().setRows(5);
        ddGLayout.getGridLayout().setColumns(5);
        ddGLayout.getGridLayout().setHeight(100, Sizeable.Unit.PERCENTAGE);
        ddGLayout.getGridLayout().setWidth(100, Sizeable.Unit.PERCENTAGE);

        //fill the GridLayout with items
        for(int i=0;i<25;i++){
//            ddGLayout.addComponent(new XyzComponent());
        }

    }

    @Override
    public void componentEvent(Component.Event event) {
        if(event instanceof SwapGridLayoutDropEvent)
        {
            //the items are already switched in the layout
            SwapGridLayoutDropEvent e =(SwapGridLayoutDropEvent) event;

            //hox to get an item in the gridLayout
            ddGLayout.getUnwrappedComponent(e.getXdest(),e.getYdest());

            //how to access to the gridLayout
            ddGLayout.getGridLayout().getComponentCount();

            //information given by the event
            e.getIndexdest();
            e.getIndexsrc();
            e.getXdest();
            e.getYdest();
            e.getXsrc();
            e.getYsrc();

        }

    }
}
