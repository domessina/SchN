package be.beme.schn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by Dorito on 17-03-16.
*/
@Service
public class DiagramServiceImpl implements DiagramService
{
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public int createDiagram(int diagramID, int userID) {
        String title ="lilol";
        //String SQL = "insert into Diagram (id, title) values (?, ?)";
        String SQL= "Select relname as table from pg_stat_user_tables where schemaname ='public'";
       // jdbcTemplate.update( SQL, diagramID, title);
       Object object= jdbcTemplate.queryForRowSet(SQL);
        if (object!=null)
        {
            System.out.println(object);
        }
        return diagramID;
    }

    @Override
    public String setTitle(int diagramID, String title) {
        return null;
    }

    @Override
    public String setNote(int diagramID, String note) {
        return null;
    }

}