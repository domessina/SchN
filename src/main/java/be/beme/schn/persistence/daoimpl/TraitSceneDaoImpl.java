package be.beme.schn.persistence.daoimpl;

import be.beme.schn.narrative.component.TraitScene;
import be.beme.schn.persistence.AbstractPersistenceService;
import be.beme.schn.persistence.dao.TraitSceneDao;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dorito on 24-03-16.
 */
public class TraitSceneDaoImpl extends AbstractPersistenceService implements TraitSceneDao {

    @Override
    public void createTraitScene(int traitId, int sceneId, int diagramId) {
        jdbcTemplate.update("insert into TraitScene (trait_id,scene_id,diagram_id) values (?,?,?)",traitId,sceneId,diagramId);

    }

    @Override
    public void deleteTraitScene(int traitId, int sceneId, int diagramId) {
        jdbcTemplate.update("delete from TraitScene where trait_id=? AND scene_id=? AND diagram_id=?",traitId,sceneId,diagramId);
    }

    @Override
    public List<TraitScene> getAllTraitSceneByDiagram(int diagramId) {
        return jdbcTemplate.query("select * from TraitScene where diagram_id=?",
                new Object[]{diagramId},new TraitSceneMapper());
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
}
