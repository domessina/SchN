package be.beme.schn.persistence.daoimpl.synch;

import be.beme.schn.narrative.component.Diagram;
import be.beme.schn.persistence.AbstractPersistenceService;
import be.beme.schn.persistence.dao.synch.DiagramSynchDao;
import be.beme.schn.persistence.daoimpl.mapper.DiagramMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Dorito on 18-08-16.
 */
@Repository
public class DiagramSynchDaoImpl extends AbstractPersistenceService implements DiagramSynchDao {

    @Autowired
    DiagramMapper mapper;

    @Override
    public void  setIdClientServer(int diagramIdClient, int diagramIdServer) {
        jdbcTemplate.update("insert into public.\"DiagramIdClientServer\" (client_id,server_id) values (?,?)",diagramIdClient,diagramIdServer);
    }

    @Override
    public List<Diagram> newFromServer(int userId) {

        return jdbcTemplate.query("select * from public.\"DiagramToUpdate\" where user_id = ?",new Object[]{userId},mapper);

    }

    @Override
    public boolean isDiagramToSynch(int diagramIdClient) {
        return jdbcTemplate.queryForObject("select exists (select true from public.\"DiagramToUpdate\" where client_id=?)",new Object[]{diagramIdClient},Boolean.class);
    }



    @Override
    public String getServerAction(int diagramIdClient) {
        return jdbcTemplate.queryForObject("select (action) from public\"DiagramToUpdate\" where client_id=?",new Object[]{diagramIdClient},String.class);
    }

    @Override
    public void setLastSelectedAction(String clientAction, int diagramIdClient) {
        jdbcTemplate.update("Update public.\"DiagramIdClientServer\" set last_selected_action=? where client_id=?",clientAction,diagramIdClient);

    }


    public String getLastSelectedAction(int diagramIdClient) {
        jdbcTemplate.queryForObject("select last_selected_action from public.\"DiagramIdClientServer\"  where client_id=?",new Object[]{diagramIdClient},String.class);

    }

    public boolean isComponentToSynch(String type, int componentIdClient){
        return jdbcTemplate.queryForObject("select exists  from public.\""+type+"ToUpdate\" where client_id=?)",new Object[]{componentIdClient},Boolean.class);
    }

    public int getDiagramId(String type, int componentIdClient){

            return jdbcTemplate.queryForObject("select diagram_id from public.\""+type+"\" where client_id=?",new Object[]{componentIdClient},Integer.class);

    }





}
