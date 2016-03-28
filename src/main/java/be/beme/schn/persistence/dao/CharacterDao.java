package be.beme.schn.persistence.dao;

import be.beme.schn.narrative.object.Character;

import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
public interface CharacterDao {

    int createCharacter(int diagramId, String name, String type);

    void setNote(int characterId, String note);

    void setPicture(int characterId, String pictureURL);

    List<Character> getAllCharactersByDiagram(int diagramId);

    void delete(int chapterId);

}
