package be.beme.schn.vaadin.narrative.view;

import be.beme.schn.vaadin.narrative.presenter.NarrativePresenter;

/**
 * Created by Dorito on 29-03-16.
 *  <p></p>
 *  <b><p>I NarrativePresenter=>NarrativeView</p>
 *  <p>I WindowPresenter=>AbstractWindowView</p>
 *  <p>C CharacterWindowPresenter=>CharacterView</p></b>
 *
 * */
public interface NarrativeView  {  //TODO créer une interface AbstractWindowView qui hérite de celle-ci. Elle regroupera toutes les méthodes qu'on verra se répéter entre chaque Window, comme characterWindow ou diagramwindow avec les buttons erase et save en commun



    void setHandler(NarrativePresenter narrativePresenter);
}
