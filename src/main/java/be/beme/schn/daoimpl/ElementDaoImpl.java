package be.beme.schn.daoimpl;

import be.beme.schn.dao.ElementDao;
import be.beme.schn.narrative.components.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dorito on 22-03-16.
 */
@Repository
public class ElementDaoImpl implements ElementDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public int createElement(int diagramId, String name) {
        jdbcTemplate.update("insert into Element (diagram_id,name) values (?,?)"
                ,diagramId, name);

        return jdbcTemplate.queryForObject("select last(id) from Element where diagram_id=?",
                new Object[] {diagramId},Integer.class);
    }

    @Override
    public void setNote(int ElementId, String note) {

        jdbcTemplate.update("update Element SET notes=? where id=?",
                note, ElementId);
    }

    @Override
    public List<Element> getAllElementsByDiagram(int diagramId) {
        return jdbcTemplate.query("select * from Element where diagram_id=?",
                new Object[]{diagramId},new ElementMapper());
    }

    @Override
    public void delete(int elementId)
    {
        jdbcTemplate.update("delete from Element where id=? ",elementId);
    }

    private static final class ElementMapper implements RowMapper<Element> {

        public Element mapRow(ResultSet rs, int rowNum) throws SQLException {
            Element element = new Element();
            element.setId(rs.getInt("id"));
            element.setNote(rs.getString("notes"));
            element.setName(rs.getString("name"));
            element.setDiagramId(rs.getInt("diagram_id"));
            return element;
        }
    }

}
