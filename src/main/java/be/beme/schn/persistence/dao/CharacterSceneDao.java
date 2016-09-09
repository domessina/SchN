package be.beme.schn.persistence.dao;

import be.beme.schn.narrative.component.Character;
import be.beme.schn.narrative.component.CharacterScene;

import java.util.List;

/**
 * Created by Dotista on 21-04-16.
 */
public interface CharacterSceneDao  extends NarrativeComponentDao{

    List<Character> getAllCharactersByScene(int sceneId);

    void addCharacterInScene(int diagramId, int characterId, int sceneId);

    void removeCharacterFromScene(int charaterId, int sceneId);

    List<CharacterScene> getAllCharacterSceneByDiagram(int diagramId);
}
