package be.beme.schn.persistence.dao;

import be.beme.schn.narrative.component.NarrativeComponent;
import be.beme.schn.narrative.component.Scene;
import be.beme.schn.persistence.Dao;

import java.io.IOException;
import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
public interface SceneDao extends NarrativeComponentDao {

    void setPlace(int place, int id);

    int getNumberOfScenes(int chapterId);

    List<Scene> getAllScenesByChapter(int chapterId);

    @Override
    Scene getNComponent(int id);

}
