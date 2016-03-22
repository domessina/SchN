package be.beme.schn.dao;

import be.beme.schn.narrative.components.Character;
import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
public interface CharacterDao {

    int createCharacter(int diagramId, String name, String type);

    void setNote(int characterId, String note);

    void setPicture(ByteArrayInputStream pictureInputStream);

    List<Character> getAllCharactersByDiagram(int diagramId);

    void delete(int chapterId);

}
