package be.beme.schn.persistence.dao;

import be.beme.schn.narrative.component.Trait;
import be.beme.schn.persistence.Dao;

import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
public interface TraitDao  extends Dao {

    int create(int characterId, String name);

    void update(Object[] args);

    void setScenes(int traitId, String[] scenesArray);

    List<Trait> getTraitsByCharacter(int characterId);

    void deleteAllTraitsByCharacter(int characterId);

    void delete(int traitId);


}
