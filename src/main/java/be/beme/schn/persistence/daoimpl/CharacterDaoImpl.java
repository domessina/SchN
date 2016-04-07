package be.beme.schn.persistence.daoimpl;

import be.beme.schn.narrative.component.Character;
import be.beme.schn.persistence.AbstractPersistenceService;
import be.beme.schn.persistence.dao.CharacterDao;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * Created by Dorito on 22-03-16.
 */
@Repository
public class CharacterDaoImpl extends AbstractPersistenceService implements CharacterDao{




    @Override
    public int create(int diagramId, String name, String type, String note, String picture_url) {
        jdbcTemplate.update("insert into public.\"Character\" (diagram_id,name,type,notes,picture_url) values (?,?,?,?,?)"
                ,diagramId, name, type,note, picture_url);
        return jdbcTemplate.queryForObject("select max(id) from public.\"Character\" where diagram_id=?",
                new Object[] {diagramId},Integer.class);

    }

    /**
     * @param args  Must be ordered like the query, the characterId is <b>the last</b>
     *              element in the array
     */
    @Override
    public void update(Object[] args) {
        jdbcTemplate.update("UPDATE public.\"Character\" SET name=?,type=?,notes=?,picture_url=? WHERE id=?",args,new int[]{
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.INTEGER});//si probleme avec LONGVARCHAR, use VARCHAR
    }

    @Override
    public void setNote(int characterId, String note) {

        jdbcTemplate.update("update Character SET notes=? where id=?",
                note, characterId);
    }

    @Override
    public Character getCharacterById(int id)
    {
        return jdbcTemplate.queryForObject("select * from public.\"Character\" where id = ?",new Object[]{id}, new CharacterMapper());
    }

    @Override
    public void setPicture(int characterId,String pictureURL) {

    }

    @Override
    public List<Character> getAllCharactersByDiagram(int diagramId) {
        return jdbcTemplate.query("select * from Character where diagram_id=?",
                new Object[]{diagramId},new CharacterMapper());
    }

    @Override
    public void delete(int characterId)
    {
        jdbcTemplate.update("delete from public.\"Character\" where id=? ",characterId);
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