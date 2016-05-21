package be.beme.schn.persistence.daoimpl;

import be.beme.schn.narrative.component.Character;
import be.beme.schn.narrative.component.NarrativeComponent;
import be.beme.schn.persistence.AbstractPersistenceService;
import be.beme.schn.persistence.dao.CharacterSceneDao;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dotista on 19-04-16.
 */
@Repository
@Transactional

public class CharacterSceneDaoImpl extends AbstractPersistenceService implements CharacterSceneDao{




    @Override
    public NarrativeComponent getNComponent(int componentId) {
        return null;
    }

    @Override
    public int create(NarrativeComponent component) {
        return 0;
    }

    @Override
    public void update(NarrativeComponent component) {

    }

    @Override
    public void delete(int componentId) {

    }


    @Override
    public void addCharacterInScene(int characterId, int sceneId)
    {
        jdbcTemplate.update("insert into public.\"CharacterScene\" (character_id,scene_id) values (?,?)"
                ,characterId,sceneId);
    }

    @Override
    public void removeCharacterFromScene(int characterId, int sceneId)
    {
        jdbcTemplate.update("delete from \"CharacterScene\" where character_id=? and scene_id=?",
                characterId,sceneId);
    }



    @Override
    public List<Character> getAllCharactersByScene(int sceneId)
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
