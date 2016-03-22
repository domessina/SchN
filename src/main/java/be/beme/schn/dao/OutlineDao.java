package be.beme.schn.dao;

import be.beme.schn.narrative.components.Outline;

import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
public interface OutlineDao {

    int createOutline(int propertyId, String type, String outlinedValue, String color);     //TODO à spécifier ici et dan sle schéma de la base le format de clouleur

    List<Outline> getAllOutlinesByProperty(int propertyId);

    void delete(int outlineId);
}
