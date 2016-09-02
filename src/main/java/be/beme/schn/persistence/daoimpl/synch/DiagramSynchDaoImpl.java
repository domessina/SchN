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
    public boolean isDiagramToSynch(int diagramId) {
        return jdbcTemplate.queryForObject("select exists (select * from public.\"DiagramToSynch\" where diagram_id=? )",new Object[]{diagramId},Boolean.class);
    }




    @Override
    public String getServerAction(int diagramId) {
        return jdbcTemplate.queryForObject("select action from public\"DiagramToSynch\" where diagram_id=?",new Object[]{diagramId},String.class);
    }

    @Override
    public void setLastSelectedAction(String clientAction, int diagramId) {
        jdbcTemplate.update("Update public.\"DiagramToSynch\" set last_selected_action=? where diagram_id=?",clientAction,diagramId);

    }


    public String getLastSelectedAction(int diagramId) {
      return  jdbcTemplate.queryForObject("select last_selected_action from public.\"DiagramToSynch\" where diagram_id=?",new Object[]{diagramId},String.class);

    }

    public boolean isComponentToSynch(String tableType,String columnType, int componentId){
        return jdbcTemplate.queryForObject("select exists (select * from public.\""+tableType+"ToSynch\" where"+columnType+"_id=?)",new Object[]{componentId},Boolean.class);
    }

    public int getDiagramId(String type, int componentId){

            return jdbcTemplate.queryForObject("select diagram_id from public.\""+type+"\" where id=?",new Object[]{componentId},Integer.class);

    }





}
