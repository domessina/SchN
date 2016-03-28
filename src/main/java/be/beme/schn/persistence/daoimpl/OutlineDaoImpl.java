package be.beme.schn.persistence.daoimpl;

import be.beme.schn.narrative.object.Outline;
import be.beme.schn.persistence.AbstractPersistenceService;
import be.beme.schn.persistence.dao.OutlineDao;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dorito on 22-03-16.
 */
@Repository
public class OutlineDaoImpl  extends AbstractPersistenceService implements OutlineDao {



    @Override
    public int createOutline(int propertyId, String type, String outlinedValue, String color) {
        jdbcTemplate.update("insert into Outline (property_id,type,value,color) values (?,?,?,?)"
                ,propertyId,type,outlinedValue,color);

        return jdbcTemplate.queryForObject("select last(id) from Outline where property_id=? AND type=? AND value=?",
                new Object[] {propertyId,type,outlinedValue},Integer.class);
    }

    @Override
    public List<Outline> getAllOutlinesByProperty(int propertyId) {
        return jdbcTemplate.query("select * from Outline where property_id=?",
                new Object[]{propertyId},new OutlineMapper());
    }

    @Override
    public void delete(int OutlineId)
    {
        jdbcTemplate.update("delete from Outline where id=? ",OutlineId);
    }

    private static final class OutlineMapper implements RowMapper<Outline> {

        public Outline mapRow(ResultSet rs, int rowNum) throws SQLException {
            Outline outline = new Outline();
            outline.setId(rs.getInt("id"));
            outline.setPropertyId(rs.getInt("property_id"));
            outline.setType(rs.getString("type"));
            outline.setValue(rs.getString("value"));
            outline.setColor(rs.getString("color"));
            return outline;
        }
    }
}
