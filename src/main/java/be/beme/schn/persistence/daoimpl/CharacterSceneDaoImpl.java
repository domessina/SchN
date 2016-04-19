package be.beme.schn.persistence.daoimpl;

import be.beme.schn.narrative.component.Character;
import be.beme.schn.persistence.AbstractPersistenceService;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dotista on 19-04-16.
 */
public class CharacterSceneDaoImpl extends AbstractPersistenceService {


    List<Character> getAllCharactersByScene(int sceneId)
    {
        return jdbcTemplate.query("select * from \"Character\" inner join \"CharacterScene\" on \"Character\".id=\"CharacterScene\".character_id where \"CharacterScene\".scene_id=?",
                new Object[]{sceneId},new CharacterMapper());
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
