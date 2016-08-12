package be.beme.schn.vaadin.narrative;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

/**
 * Created by Dotista on 12-04-16.
 */
public interface NWrapped extends Button.ClickListener, Component {  //ecrire extends Component oblige à ce que le NWrapped soit un component vaadin ;)

    void wrap(NWrapper wrapper);
    NWrapper getWrapper();
}
