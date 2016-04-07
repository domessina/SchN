package be.beme.schn.persistence.dao;

import be.beme.schn.narrative.component.Trait;

import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
public interface TraitDao {

    int create(int characterId, String name);

    void update(Object[] args);

    void setScenes(int traitId, String[] scenesArray);

    List<Trait> getAllTraitsByCharacter(int characterId);

    void deleteAllTraitsByCharacter(int characterId);

    void delete(int traitId);


}
