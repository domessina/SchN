package be.beme.schn.persistence.dao;

import be.beme.schn.narrative.component.Character;

import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
public interface CharacterDao  extends NarrativeComponentDao {



    @Override
    Character getNComponent(int id);

    boolean exist(String characterName, int diagramId);

    void setNote(int characterId, String note);

    void setPicture(int characterId, String pictureURL);

    List<Character> getAllCharactersByDiagram(int diagramId);

    void deleteAllByDiagram(int diagramId);



}
