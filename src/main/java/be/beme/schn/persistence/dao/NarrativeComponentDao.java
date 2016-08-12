package be.beme.schn.persistence.dao;

import be.beme.schn.narrative.component.NarrativeComponent;

/**
 * Created by Dotista on 18-05-16.
 */
public interface NarrativeComponentDao {

    NarrativeComponent getNComponent(int componentId);

    /**
     * return the id of the new component
     * */
    int create(NarrativeComponent component);

    /**
     * returns the number of rows affected
     * **/
    int update(NarrativeComponent component) ;

    int delete(int componentId);
}
