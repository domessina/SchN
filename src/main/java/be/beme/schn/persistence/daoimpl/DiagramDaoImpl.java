package be.beme.schn.persistence.daoimpl;

/**
 * Created by Dorito on 21-03-16.
 */

import be.beme.schn.narrative.component.Diagram;
import be.beme.schn.persistence.AbstractPersistenceService;
import be.beme.schn.persistence.dao.DiagramDao;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dorito on 17-03-16.
 */
@Repository
@Transactional

public class DiagramDaoImpl extends AbstractPersistenceService implements DiagramDao
{

    @Override
    public int createDiagram(int userId, String title) {            //le diagram_id est unique dans toute la DB

        jdbcTemplate.update( "insert into public.\"Diagram\" (user_id,title) values (?,?)",userId, title);
        jdbcTemplate.update( "update public.\"User\" set nb_diagrams=nb_diagrams+1 where id=? (?)",userId);

        return jdbcTemplate.queryForObject("select id from public.\"Diagram\" order by id desc limit 1",Integer.class);
    }

    @Override
    public String setTitle(int diagramId, String title) {

        jdbcTemplate.update( "UPDATE Diagram SET title=? WHERE id=? values (?,?)",
                title, diagramId);
        return title;                                                           //TODO je retorune le titre ainsi , ou je dois aller lire dans la db le titre et retourner celiu là pour savoir si bonne valeur écrite dans db?
    }




    @Override
    public Integer[] getAllDiagramsIdByUser(int userId)
    {
      /* jdbcTemplate.queryForList();
        jdbcTemplate.queryForObject("select id from Diagram where user_id=?",
                new Object[] {ruleId},Boolean.class);*/

        return null;
    }

    @Override
    public Diagram getDiagramById(int diagramId)
    {
        return jdbcTemplate.queryForObject("select * from Diagram where id=?",
                new Object[]{diagramId},new DiagramMapper());
    }

    @Override
    public List<Diagram> getAllDiagramsByUser(int userId) {
        return jdbcTemplate.query("select * from Diagram where user_id=?",
                new Object[]{userId},new DiagramMapper());
    }

    @Override
    public void deleteDiagram(int diagramId)
    {
        jdbcTemplate.update("delete from Diagram where id=? ",diagramId);
    }

    @Override
    public String getTitle(int diagramId) {
        return null;
    }

    private static final class DiagramMapper implements RowMapper<Diagram> {

        public Diagram mapRow(ResultSet rs, int rowNum) throws SQLException {
            Diagram diagram = new Diagram();
            diagram.setId(rs.getInt("id"));
            diagram.setUser_id(rs.getInt("user_id"));
            diagram.setTitle(rs.getString("title"));
            return diagram;
        }
    }






}