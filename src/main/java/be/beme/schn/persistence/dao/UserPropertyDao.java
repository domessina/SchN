package be.beme.schn.persistence.dao;

import be.beme.schn.narrative.component.UserProperty;
import be.beme.schn.persistence.Dao;

import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
public interface UserPropertyDao  extends Dao {

    int createProperty(int elementId, String name, boolean typeList);

    void setValue(int propertyId, int elementId, String value);

    List<UserProperty> getAllPropertiesByElement(int elementId);

    void delete(int propertyId);

}
