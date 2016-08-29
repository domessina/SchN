package be.beme.schn.persistence.daoimpl;

/**
 * Created by Dorito on 21-03-16.
 */

import be.beme.schn.narrative.component.Diagram;
import be.beme.schn.narrative.component.NarrativeComponent;
import be.beme.schn.persistence.AbstractPersistenceService;
import be.beme.schn.persistence.dao.DiagramDao;
import be.beme.schn.persistence.daoimpl.mapper.DiagramMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.List;

/**
 * Created by Dorito on 17-03-16.
 */
@Repository
@Transactional
public class DiagramDaoImpl extends AbstractPersistenceService implements DiagramDao
{

    @Autowired
    DiagramMapper mapper;

    @Override
    public int create(NarrativeComponent component) {            //le diagram_id est unique dans toute la DB
        Diagram d=(Diagram)component;
        jdbcTemplate.update( "insert into public.\"Diagram\" (user_id,title,picture_id) values (?,?,?)",d.getUser_id(), d.getTitle(),d.getPictureId());

        return jdbcTemplate.queryForObject("select id from public.\"Diagram\" order by id desc limit 1",Integer.class);
    }

    @Override
    public int update(NarrativeComponent component) {
        Diagram d=(Diagram)component;
        Object[] args= new Object[]{d.getTitle(),d.getPictureId(),d.getId()};
        return jdbcTemplate.update("UPDATE public.\"Diagram\" SET title=?,picture_id=? WHERE id=?",args,new int[]{
                Types.VARCHAR,
                Types.VARCHAR,
                Types.INTEGER});
    }

    @Override
    public int delete(int diagramId) {
        int userId = getDiagramById(diagramId).getUser_id();
        return jdbcTemplate.update("delete from public.\"Diagram\" where id=?", diagramId);
    }


    @Override
    public Diagram getNComponent(int id) {
        return jdbcTemplate.queryForObject("select * from public.\"Diagram\" where id = ?",new Object[]{id}, mapper);

    }

    @Override
    public Diagram getDiagramByClientId(int diagramIdClient) {
        return jdbcTemplate.queryForObject("select * from \"Diagram\" where client_id=?",
                new Object[]{diagramIdClient},mapper);
    }

    @Override
    public Diagram getDiagramById(int diagramId)
    {
        return jdbcTemplate.queryForObject("select * from \"Diagram\" where id=?",
                new Object[]{diagramId},mapper);
    }

    @Override
    public int getIdFromClientId(int clientId) {
        return jdbcTemplate.queryForObject("select id from \"Diagram\" where client_id=?",
                new Object[]{clientId},Integer.class);
    }

    @Override
    public void setDiagramEnabled(int diagramId, boolean isEnabled) {
        jdbcTemplate.update("UPDATE public.\"Diagram\" SET enabled=? WHERE id=?",new Object[]{isEnabled},new int[]{
                Types.BOOLEAN});
    }

    @Override
    public List<Diagram> getAllDiagramsByUser(int userId) {
        return jdbcTemplate.query("select * from \"Diagram\" where user_id=? and enabled=true",
                new Object[]{userId},mapper);
    }


}