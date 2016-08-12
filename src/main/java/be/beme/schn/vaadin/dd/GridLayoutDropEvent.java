package be.beme.schn.vaadin.dd;

import com.vaadin.ui.Component;

/**
 * Created by Dotista on 23-04-16.
 */
public class GridLayoutDropEvent extends Component.Event {

    private int[] coordinates;



    /**
     * Constructs a new event with the specified source component.
     *
     * @param source the GridLayout
     * @param coordinates .
     */
    public GridLayoutDropEvent(Component source,int[] coordinates) {
        super(source);
        this.coordinates=coordinates;
    }

    public int getXdest() {return coordinates[0];}

    public int getYdest() {return coordinates[1];}

    /**
     * @return index in the GridLayout where the component is dropped
     */
    public int getIndexdest() {return coordinates[2];}

    public int getXsrc() {return coordinates[3];}

    public int getYsrc() {return coordinates[4];}

    public int getIndexsrc() {return coordinates[5];
    }
}
