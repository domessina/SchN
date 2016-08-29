package be.beme.schn.persistence.daoimpl.synch;

import be.beme.schn.persistence.AbstractPersistenceService;
import be.beme.schn.persistence.daoimpl.mapper.DiagramMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Dorito on 27-08-16.
 */
@Repository
public class ChapterToSynch extends AbstractPersistenceService  {

    @Autowired
    DiagramMapper mapper;



    public boolean isDiagramToSynch(int diagramIdClient) {
        return jdbcTemplate.queryForObject("select exists (select true from public.\"DiagramToUpdate\" where client_id=?)",new Object[]{diagramIdClient},Boolean.class);
    }


    public String getServerAction(int diagramIdClient) {
        return jdbcTemplate.queryForObject("select (action) from public\"DiagramToUpdate\" where client_id=?",new Object[]{diagramIdClient},String.class);
    }


    public void setLastSelectedAction(String clientAction) {
        jdbcTemplate.update("insert into public.\"DiagramIdClientServer\" (last_selected_action) values (?)",clientAction);

    }
}