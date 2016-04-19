package be.beme.schn.persistence.dao;

import be.beme.schn.narrative.component.Scene;

import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
public interface SceneDao {

    int create(int chapterId, int previousSceneId);

    void update(Object[] args) ;

    void delete(int sceneId);

    List<Scene> getAllScenesByChapter(int chapterId);


}
