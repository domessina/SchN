package be.beme.schn.persistence.dao;

import be.beme.schn.narrative.component.Link;
import be.beme.schn.persistence.Dao;

import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
public interface LinkDao  extends Dao {

    int createLink(int elementId_1, int elementId_2, int sceneId );

    boolean setRelationShip(int linkId, boolean relshp);    //null=neutre

    void setName(int linkId, String name );

    List<Link> getLinksBy( int fromCharacterId,int sceneId);

    void delete(int linkId);


}
