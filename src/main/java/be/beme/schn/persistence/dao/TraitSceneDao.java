package be.beme.schn.persistence.dao;

import be.beme.schn.narrative.TraitScene;
import be.beme.schn.narrative.component.Trait;
import be.beme.schn.persistence.Dao;

import java.util.List;

/**
 * Created by Dorito on 24-03-16.
 */
public interface TraitSceneDao  extends Dao {

    void create(int traitId, int sceneId);

    void delete(int traitId, int sceneId);

    List<Trait> getTraitByScene(int sceneId);
}
