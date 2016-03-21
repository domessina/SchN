package be.beme.schn.dao;

import java.io.ByteArrayInputStream;

/**
 * Created by Dorito on 21-03-16.
 */
public interface SceneDao {

    int createScene(int chapterId);

    int setPicture(ByteArrayInputStream pictureInputStream);


}
