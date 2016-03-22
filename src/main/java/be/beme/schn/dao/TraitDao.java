package be.beme.schn.dao;

import be.beme.schn.narrative.components.Trait;

import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
public interface TraitDao {

    int createTrait(int characterId, String name);

    void setScenes(int traitId, String[] scenesArray);

    List<Trait> getAllTraitsByCharacter(int characterId);

    void delete(int traitId);


}
