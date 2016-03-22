package be.beme.schn.dao;

import be.beme.schn.narrative.components.Property;

import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
public interface PropertyDao {

    int createProperty(int elementId, String name, boolean typeList);

    void setValue(int propertyId, int elementId, String value);

    List<Property> getAllPropertiesByElement(int elementId);

    void delete(int propertyId);

}
