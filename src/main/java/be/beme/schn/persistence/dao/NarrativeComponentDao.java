package be.beme.schn.persistence.dao;

import be.beme.schn.narrative.component.NarrativeComponent;

/**
 * Created by Dotista on 18-05-16.
 */
public interface NarrativeComponentDao {

    NarrativeComponent getNComponent(int componentId);

    int create(NarrativeComponent component);

    void update(NarrativeComponent component) ;

    void delete(int componentId);
}
