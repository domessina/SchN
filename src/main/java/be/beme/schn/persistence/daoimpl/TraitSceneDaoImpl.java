package be.beme.schn.persistence.daoimpl;

import be.beme.schn.narrative.TraitScene;
import be.beme.schn.narrative.component.Trait;
import be.beme.schn.persistence.AbstractPersistenceService;
import be.beme.schn.persistence.dao.TraitSceneDao;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dorito on 24-03-16.
 */

@Repository
@Transactional

public class TraitSceneDaoImpl extends AbstractPersistenceService implements TraitSceneDao {

    @Override
    public void create(int traitId, int sceneId) {
        jdbcTemplate.update("insert into \"TraitScene\" (trait_id,scene_id) values (?,?)",traitId,sceneId);

    }

    @Override
    public void delete(int traitId, int sceneId) {
        jdbcTemplate.update("delete from \"TraitScene\" where trait_id=? AND scene_id=?",traitId,sceneId);
    }

    @Override
    public List<Trait> getTraitByScene(int sceneId) {
        return jdbcTemplate.query("select * from \"Trait\" inner join \"TraitScene\" on \"Trait\".id=\"TraitScene\".trait_id where \"TraitScene\".scene_id=?",
                new Object[]{sceneId},new TraitMapper());
    }




    private static final class TraitSceneMapper implements RowMapper<TraitScene> {

        public TraitScene mapRow(ResultSet rs, int rowNum) throws SQLException {
            TraitScene traitScene = new TraitScene();
            traitScene.setTraitId(rs.getInt("trait_id"));
            traitScene.setSceneId(rs.getInt("scene_id"));
            traitScene.setDiagramId(rs.getInt("diagram_id"));

            return traitScene;
        }
    }
    private static final class TraitMapper implements RowMapper<Trait> {

        public Trait mapRow(ResultSet rs, int rowNum) throws SQLException {
            Trait trait = new Trait();
            trait.setId(rs.getInt("id"));
            trait.setName(rs.getString("name"));
            trait.setCharacterId(rs.getInt("character_id"));
            return trait;
        }
    }
}
