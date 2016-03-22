package be.beme.schn.daoimpl;

/**
 * Created by Dorito on 21-03-16.
 */

import be.beme.schn.dao.DiagramDao;
import be.beme.schn.narrative.components.Diagram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dorito on 17-03-16.
 */
@Repository
public class DiagramDaoImpl implements DiagramDao
{
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public int createDiagram(int userId, String title) {            //le diagram_id est unique dans toute la DB

        //prendre l'id du dernier diagram créé par cet user et on l'incrémente
        int diagramId=jdbcTemplate.queryForObject("select max(id) from Diagram where user_id=?",
                new Object[] {userId },Integer.class);

        //je suppose que l'id va être auta incrémenté sans que je lenvoie dans la requete
        jdbcTemplate.update( "insert into Diagram (id, user_id, title) values (?,?)", diagramId+1,userId, title);

        return jdbcTemplate.queryForObject("select last(id) from Diagram where user_id=?",
                new Object[] {userId },Integer.class);
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