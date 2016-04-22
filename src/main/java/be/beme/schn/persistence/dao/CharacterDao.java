package be.beme.schn.persistence.dao;

import be.beme.schn.narrative.component.Character;
import be.beme.schn.persistence.Dao;

import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
public interface CharacterDao  extends Dao {

    int create(int diagramId, String name, String type, String note, String picture_url) ;

    boolean exist(String characterName, int diagramId);

    List<Character> getAllCharactersByScene(int sceneId);

    void setNote(int characterId, String note);

    void setPicture(int characterId, String pictureURL);

    List<Character> getAllCharactersByDiagram(int diagramId);

    Character getCharacterById(int id);

    void delete(int chapterId);

    void update( Object[] args); //J'aimerais remplacer par update(int characterId, Object[ args);

}
