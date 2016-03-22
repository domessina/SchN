package be.beme.schn.dao;

import be.beme.schn.narrative.components.Scene;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
public interface SceneDao {

    int createScene(int chapterId, int previousSceneId);

    int setPicture(int sceneId, ByteArrayInputStream pictureInputStream);

    void setNote(int sceneId, String note);

    List<Scene> getAllScenesByChapter(int chapterId);

    void delete(int sceneId);



}
