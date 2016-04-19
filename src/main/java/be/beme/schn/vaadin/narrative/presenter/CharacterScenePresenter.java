package be.beme.schn.vaadin.narrative.presenter;

import be.beme.schn.vaadin.narrative.view.CharacterScene;
import be.beme.schn.vaadin.narrative.view.NarrativeView;

/**
 * Created by Dotista on 19-04-16.
 */
public class CharacterScenePresenter implements NarrativePresenter {

    private CharacterScene view;
    @Override
    public void setView(NarrativeView narrativeView) {
        this.view=(CharacterScene)narrativeView;
    }
}
