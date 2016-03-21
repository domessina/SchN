package be.beme.schn.daoimpl;

/**
 * Created by Dorito on 21-03-16.
 */

import be.beme.schn.dao.DiagramDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by Dorito on 17-03-16.
 */
@Service
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
    public String setNote(int diagramId, String note) {

        jdbcTemplate.update( "UPDATE Diagram SET notes=? WHERE id=? values (?,?)",
                note, diagramId);
        return note;
    }



}