package be.beme.schn.vaadin.narrative;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

/**
 * Created by Dotista on 11-04-16.
 */
public interface NWrapper extends Component {

    public Button getButtonSet();

    public Button getButtonErase();

    public Button getButtonSave();

    public void setWrappedContent(Component component);




}
