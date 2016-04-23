package be.beme.schn.vaadin.dd;

import com.vaadin.ui.Component;

/**
 * Created by Dotista on 23-04-16.
 */
public class GridLayoutDropEvent extends Component.Event {

    private int column;
    private int row;
    private int index;



    /**
     * Constructs a new event with the specified source component.
     *
     * @param source the source component of the event
     */
    public GridLayoutDropEvent(Component source, int column, int row, int index) {
        super(source);
        this.column=column;
        this.row=row;
        this.index=index;
    }


    public int getX() {
        return column;
    }

    public int getIndex() {
        return index;
    }

    public int getY() {
        return row;
    }
}
