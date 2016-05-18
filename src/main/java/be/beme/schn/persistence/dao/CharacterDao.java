package be.beme.schn.persistence.dao;

import be.beme.schn.narrative.component.Chapter;
import be.beme.schn.narrative.component.Character;
import be.beme.schn.narrative.component.NarrativeComponent;
import be.beme.schn.persistence.Dao;

import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
public interface CharacterDao  extends NarrativeComponentDao {



    @Override
    Character getNComponent(int id);

    boolean exist(String characterName, int diagramId);

    List<Character> getAllCharactersByScene(int sceneId);

    void setNote(int characterId, String note);

    void setPicture(int characterId, String pictureURL);

    List<Character> getAllCharactersByDiagram(int diagramId);

    void addCharacterInScene(int characterId, int sceneId);

    void removeCharacterFromScene(int charaterId, int sceneId);


}
