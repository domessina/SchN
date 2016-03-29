package be.beme.schn.persistence.dao;

import be.beme.schn.narrative.component.Link;

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
