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
        jdbcTemplate.update( "insert into public.\"Diagram\" (user_id,title) values (?,?)",d.getUser_id(), d.getTitle());
        jdbcTemplate.update( "update public.\"User\" set nb_diagrams=nb_diagrams+1 where id=?",d.getUser_id());

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
        return jdbcTemplate.queryForObject("select * from public.\"Diagram\" where id = ?",new Object[]{id}, mapper);

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
        return jdbcTemplate.queryForObject("select * from \"Diagram\" where id=?",
                new Object[]{diagramId},mapper);
    }

    @Override
    public List<Diagram> getAllDiagramsByUser(int userId) {
        return jdbcTemplate.query("select * from \"Diagram\" where user_id=?",
                new Object[]{userId},mapper);
    }

    @Override
    public int delete(int diagramId) {
        int userId = getDiagramById(diagramId).getUser_id();
        return jdbcTemplate.update("delete from public.\"Diagram\" where id=? ", diagramId);
    }

    public int getUserId(int diagramId){
        return jdbcTemplate.update("select (user_id) from public.\"Diagram\" where id=?", diagramId);

    }

    @Override
    public String getTitle(int diagramId) {
        return null;
    }


}