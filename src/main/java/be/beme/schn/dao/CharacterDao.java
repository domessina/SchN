package be.beme.schn.dao;

import java.io.ByteArrayInputStream;

/**
 * Created by Dorito on 21-03-16.
 */
public interface CharacterDao {

    int createCharacter(int diagramId, String name, String type);

    String setNote(String note);

    int setPicture(ByteArrayInputStream pictureInputStream);


}
