package be.beme.schn.vaadin.narrative.presenter;

import be.beme.schn.persistence.Dao;
import be.beme.schn.vaadin.narrative.view.NarrativeView;

/**
 * Created by Dorito on 29-03-16.
 */
public interface NarrativePresenter  {                                      //TODO créer une nouvelle interface qui hérite de NarrativePresenter et possède la méthode getDaoService

    void setView(NarrativeView narrativeView);

    Dao getDaoService();
}
