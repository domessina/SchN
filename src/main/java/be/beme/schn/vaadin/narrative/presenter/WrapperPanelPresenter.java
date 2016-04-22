package be.beme.schn.vaadin.narrative.presenter;

import be.beme.schn.narrative.component.NarrativeComponent;

/**
 * Created by Dorito on 29-03-16.
 */
public interface WrapperPanelPresenter extends NarrativePresenter {

    /**
     *
     * @return the last NarrativeComponent saved with new informations given
     *                 by the database. Or null if exception during data access
     */
    NarrativeComponent save();

    /**
     *
     * @return true if successful erasure.
     */
    boolean erase();



}
