package be.beme.schn.dao;

/**
 * Created by Dorito on 21-03-16.
 */
public interface TraitDao {

    int createTrait(int characterId, String name);

    String setScenes(String[] scenesArray);


}
