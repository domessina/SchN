package be.beme.schn.dao;

import be.beme.schn.narrative.components.Link;

import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
public interface LinkDao {

    int createLink(int elementId_1, int elementId_2, int sceneId );

    boolean setRelationShip(int linkId, boolean relshp);    //null=neutre

    void setName(int linkId, String name );

    List<Link> getAllLinksByScene(int sceneId);

    void delete(int linkId);


}
