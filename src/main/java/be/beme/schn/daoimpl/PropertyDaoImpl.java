package be.beme.schn.daoimpl;

import be.beme.schn.dao.PropertyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by Dorito on 21-03-16.
 */
public class PropertyDaoImpl implements PropertyDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int createProperty(int elementId, String name, boolean typeList) {
        jdbcTemplate.update("insert into Property (element_id,name,type_list) values (?,?,?)"
                ,elementId,name,typeList);

        return jdbcTemplate.queryForObject("select last(id) from Property where element_id=? and name=?",
                new Object[] {elementId,name },Integer.class);
    }

    @Override
    public String setValue(int propertyId, String value) {
        jdbcTemplate.update("update Property SET value=? where id=?",
                value, propertyId);

        return value;
    }


}
