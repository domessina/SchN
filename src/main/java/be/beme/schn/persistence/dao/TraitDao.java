package be.beme.schn.persistence.dao;

import be.beme.schn.narrative.component.Character;
import be.beme.schn.narrative.component.Trait;
import be.beme.schn.persistence.Dao;

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


}
