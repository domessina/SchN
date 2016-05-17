package be.beme.schn.persistence.dao;

import be.beme.schn.narrative.component.Scene;
import be.beme.schn.persistence.Dao;

import java.io.IOException;
import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
public interface SceneDao extends Dao {

    int create(Scene scene);

    void update(Scene scene) ;

    void delete(int sceneId);

    void setPlace(int id, int place);

    List<Scene> getAllScenesByChapter(int chapterId);

    Scene getScene(int id);


}
