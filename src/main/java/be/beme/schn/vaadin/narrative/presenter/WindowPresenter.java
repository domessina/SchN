package be.beme.schn.vaadin.narrative.presenter;

/**
 * Created by Dorito on 29-03-16.
 */
public interface WindowPresenter extends NarrativePresenter {

    void saveInDB();

    //boolean saveInDB();   renvoyer si echec ou réussite?

    void eraseFromDB();



}
