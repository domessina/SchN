package be.beme.schn.vaadin.narrative.presenter;

import be.beme.schn.vaadin.narrative.view.LinkView;
import be.beme.schn.vaadin.narrative.view.NarrativeView;

/**
 * Created by Dotista on 19-04-16.
 */
public class LinkPresenter implements NarrativePresenter {

    private LinkView view;

    @Override
    public void setView(NarrativeView narrativeView) {
        this.view=(LinkView)narrativeView;
    }
}
