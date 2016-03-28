package be.beme.schn.persistence.dao;

import be.beme.schn.narrative.object.TraitScene;

import java.util.List;

/**
 * Created by Dorito on 24-03-16.
 */
public interface TraitSceneDao {

    void createTraitScene(int traitId, int sceneId, int diagramId);

    void deleteTraitScene(int traitId, int sceneId, int diagramId);

    List<TraitScene> getAllTraitSceneByDiagram(int diagramId);
}
