package be.beme.schn.persistence.dao;

import be.beme.schn.narrative.component.Character;
import be.beme.schn.persistence.Dao;

import java.util.List;

/**
 * Created by Dotista on 21-04-16.
 */
public interface CharacterSceneDao  extends Dao {

    List<Character> getAllCharactersByScene(int sceneId);

}
