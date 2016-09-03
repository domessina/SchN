package be.beme.schn.persistence.dao;

import be.beme.schn.narrative.component.Trait;

import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
public interface TraitDao  extends NarrativeComponentDao {


    @Override
    Trait getNComponent(int id);

    void setScenes(int traitId, String[] scenesArray);

    List<Trait> getTraitsByCharacter(int characterId);

    void deleteAllTraitsByCharacter(int characterId);

    List<Trait> getAllTraitsByDiagram(int diagramId);


}
