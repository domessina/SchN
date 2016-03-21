package be.beme.schn.dao;

/**
 * Created by Dorito on 21-03-16.
 */
public interface LinkDao {

    int createLink(int elementId_1, int elementId_2, int sceneId );

    boolean setRelationShip(boolean relshp);    //TODO null=neutre


}
