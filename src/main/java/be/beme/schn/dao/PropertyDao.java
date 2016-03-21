package be.beme.schn.dao;

/**
 * Created by Dorito on 21-03-16.
 */
public interface PropertyDao {

    int createProperty(int elementId, String name, boolean typeList);

    String setValue(int propertyId, String value);

}
