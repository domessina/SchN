package be.beme.schn.persistence.dao;

import be.beme.schn.narrative.object.Scene;

import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
public interface SceneDao {

    int createScene(int chapterId, int previousSceneId);

    void setPicture(int sceneId, String pictureURL);

    void setNote(int sceneId, String note);

    List<Scene> getAllScenesByChapter(int chapterId);

    void delete(int sceneId);



}
