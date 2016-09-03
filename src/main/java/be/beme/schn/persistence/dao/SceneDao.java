package be.beme.schn.persistence.dao;

import be.beme.schn.narrative.component.Scene;

import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
public interface SceneDao extends NarrativeComponentDao {

    void setPlace(int place, int id);

    int getNumberOfScenes(int chapterId);

    List<Scene> getAllScenesByChapter(int chapterId);

    List<Scene> getAllScenesByDiagram(int diagramid);

    @Override
    Scene getNComponent(int id);

}
