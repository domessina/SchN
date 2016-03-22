package be.beme.schn.daoimpl;

import be.beme.schn.dao.LinkDao;
import be.beme.schn.narrative.components.Link;
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
public class LinkDaoImpl implements LinkDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public int createLink(int elementId_1, int elementId_2, int sceneId) {
        jdbcTemplate.update("insert into Link (element_id_1,element_id_2,scene_id) values (?,?,?)"
                ,elementId_1,elementId_2,sceneId);

        return jdbcTemplate.queryForObject("select last(id) from Link where scene_id=?",
                new Object[] {sceneId},Integer.class);
    }

    @Override
    public boolean setRelationShip(int linkId, boolean relshp) {
        jdbcTemplate.update("update Link SET rel=? where id=?",
                relshp, linkId);
        return relshp;              //TODO toujorus obligé de renvoyé un truc ou un void c'est bon ici?
}

    @Override
    public void setName(int linkId, String name) {
        jdbcTemplate.update("update Link SET name=? where id=?",
                name, linkId);
    }

    @Override
    public List<Link> getAllLinksByScene(int sceneId) {
        return jdbcTemplate.query("select * from Diagram where user_id=?",
                new Object[]{sceneId},new LinkMapper());
    }

    @Override
    public void delete(int LinkId)
    {
        jdbcTemplate.update("delete from Link where id=? ",LinkId);
    }

    private static final class LinkMapper implements RowMapper<Link> {

        public Link mapRow(ResultSet rs, int rowNum) throws SQLException {
            Link link = new Link();
            link.setId(rs.getInt("id"));
            link.setName(rs.getString("name"));
            link.setElementId_1(rs.getInt("element_id_1"));
            link.setElementId_2(rs.getInt("element_id_2"));
            link.setRel(rs.getBoolean("rel"));
            link.setScene_Id(rs.getInt("scene_id"));
            return link;
        }
    }
}
