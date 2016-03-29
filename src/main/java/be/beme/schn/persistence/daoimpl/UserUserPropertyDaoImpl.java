package be.beme.schn.persistence.daoimpl;

import be.beme.schn.narrative.component.UserProperty;
import be.beme.schn.persistence.AbstractPersistenceService;
import be.beme.schn.persistence.dao.UserPropertyDao;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
@Repository
public class UserUserPropertyDaoImpl extends AbstractPersistenceService implements UserPropertyDao {


    @Override
    public int createProperty(int elementId, String name, boolean typeList) {
        jdbcTemplate.update("insert into Property (element_id,name,type_list) values (?,?,?)"
                ,elementId,name,typeList);

        return jdbcTemplate.queryForObject("select last(id) from Property where element_id=? and name=?",
                new Object[] {elementId,name },Integer.class);
    }

    @Override
    public void setValue(int propertyId, int element_id, String value) {
        jdbcTemplate.update("update Property SET value=? where id=?",
                value, propertyId);

        jdbcTemplate.update("update Property SET element_id=? where id=?",
                element_id, propertyId);
    }


    @Override
    public List<UserProperty> getAllPropertiesByElement(int elementId) {
        return jdbcTemplate.query("select * from Property where element_id=?",
                new Object[]{elementId},new PropertyMapper());
    }

    @Override
    public void delete(int propertyId)
    {
        jdbcTemplate.update("delete from Property where id=? ",propertyId);
    }

    private static final class PropertyMapper implements RowMapper<UserProperty> {

        public UserProperty mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserProperty userProperty = new UserProperty();
            userProperty.setId(rs.getInt("id"));
            userProperty.setElement_id(rs.getInt("element_id"));
            userProperty.setName(rs.getString("name"));
            userProperty.setTypeList(rs.getBoolean("type_list"));
            userProperty.setValue(rs.getString("value"));
            return userProperty;
        }
    }
}
