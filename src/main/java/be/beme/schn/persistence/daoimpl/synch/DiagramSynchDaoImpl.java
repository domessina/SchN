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
}
