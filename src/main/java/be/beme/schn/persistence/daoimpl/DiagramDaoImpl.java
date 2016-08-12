package be.beme.schn.persistence.daoimpl;

/**
 * Created by Dorito on 21-03-16.
 */

import be.beme.schn.narrative.component.Diagram;
import be.beme.schn.narrative.component.NarrativeComponent;
import be.beme.schn.persistence.AbstractPersistenceService;
import be.beme.schn.persistence.dao.DiagramDao;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * Created by Dorito on 17-03-16.
 */
@Repository
@Transactional
public class DiagramDaoImpl extends AbstractPersistenceService implements DiagramDao
{


    @Override
    public int create(NarrativeComponent component) {            //le diagram_id est unique dans toute la DB
        Diagram d=(Diagram)component;
        jdbcTemplate.update( "insert into public.\"Diagram\" (user_id,title) values (?,?)",d.getUser_id(), d.getTitle());
        jdbcTemplate.update( "update public.\"User\" set nb_diagrams=nb_diagrams+1 where id=? (?)",d.getUser_id());

        return jdbcTemplate.queryForObject("select id from public.\"Diagram\" order by id desc limit 1",Integer.class);
    }

    @Override
    public int update(NarrativeComponent component) {
        Diagram d=(Diagram)component;
        Object[] args= new Object[]{d.getTitle(),d.getId()};
        return jdbcTemplate.update("UPDATE public.\"Diagram\" SET title=? WHERE id=?",args,new int[]{
                Types.VARCHAR,
                Types.INTEGER});
    }


    @Override
    public String setTitle(int diagramId, String title) {

        jdbcTemplate.update( "UPDATE Diagram SET title=? WHERE id=? values (?,?)",
                title, diagramId);
        return title;                                                           //TODO je retorune le titre ainsi , ou je dois aller lire dans la db le titre et retourner celiu là pour savoir si bonne valeur écrite dans db?
    }

    @Override
    public Diagram getNComponent(int id) {
        return jdbcTemplate.queryForObject("select * from public.\"Diagram\" where id = ?",new Object[]{id}, new DiagramMapper());

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
        return jdbcTemplate.query("select * from \"Diagram\" where user_id=?",
                new Object[]{userId},new DiagramMapper());
    }

    @Override
    public int delete(int diagramId)
    {
       return jdbcTemplate.update("delete from Diagram where id=? ",diagramId);
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