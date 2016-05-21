package be.beme.schn.persistence.daoimpl;

import be.beme.schn.narrative.component.Character;
import be.beme.schn.narrative.component.NarrativeComponent;
import be.beme.schn.persistence.AbstractPersistenceService;
import be.beme.schn.persistence.dao.CharacterDao;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * Created by Dorito on 22-03-16.
 */
@Repository
@Transactional

public class CharacterDaoImpl extends AbstractPersistenceService implements CharacterDao{




    @Override
    public int create(NarrativeComponent component) {
        Character c=(Character)component;
        jdbcTemplate.update("insert into public.\"Character\" (diagram_id,name,type,notes,picture_url) values (?,?,?,?,?)",
                c.getDiagram_id(),c.getName(),c.getType(),c.getNote(),c.getPicture());

        return jdbcTemplate.queryForObject("select max(id) from public.\"Character\" where diagram_id=?",
                new Object[] {c.getDiagram_id()},Integer.class);

    }

/**
     * @param args  Must be ordered like the query, the characterId is <b>the last</b>
     *              element in the array
     */

    @Override
    public void update(NarrativeComponent component) {
        Character c=(Character)component;
        Object[] args= new Object[]{c.getName(),c.getType(),c.getNote(),c.getPicture(),c.getId()};
        jdbcTemplate.update("UPDATE public.\"Character\" SET name=?,type=?,notes=?,picture_url=? WHERE id=?",args,new int[]{
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.INTEGER});//si probleme avec LONGVARCHAR pour TEXT, use VARCHAR
    }

      @Override
    public void delete(int componentId)
    {
        jdbcTemplate.update("delete from public.\"Character\" where id=? ",componentId);
    }

    @Override
    public Character getNComponent(int id)
    {
        return jdbcTemplate.queryForObject("select * from public.\"Character\" where id = ?",new Object[]{id}, new CharacterMapper());
    }

    @Override
    public void setNote(int characterId, String note) {

        jdbcTemplate.update("update Character SET notes=? where id=?",
                note, characterId);
    }

    public boolean exist(String characterName, int diagramId)            //unique constraint on this columns
    {
        List<Character> list=jdbcTemplate.query("select * from \"Character\" where diagram_id=? and name=?", new Object[]{diagramId,characterName},new CharacterMapper());

        if(list.isEmpty())
        {
            return false;
        }
        return true;
    }




    @Override
    public void setPicture(int characterId,String pictureURL) {

    }

    @Override
    public List<Character> getAllCharactersByDiagram(int diagramId) {
        return jdbcTemplate.query("select * from \"Character\" where diagram_id=?",
                new Object[]{diagramId},new CharacterMapper());
    }





    private static final class CharacterMapper implements RowMapper<Character> {

        public Character mapRow(ResultSet rs, int rowNum) throws SQLException {
            Character character = new Character();
            character.setId(rs.getInt("id"));
            character.setName(rs.getString("name"));
            character.setType(rs.getString("type"));
            character.setNote(rs.getString("notes"));
            character.setPicture(rs.getString("picture_url"));
            character.setDiagram_id(rs.getInt("diagram_id"));
            return character;
        }
    }
}