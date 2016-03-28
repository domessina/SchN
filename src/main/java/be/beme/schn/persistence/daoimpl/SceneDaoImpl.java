package be.beme.schn.persistence.daoimpl;

import be.beme.schn.narrative.object.Scene;
import be.beme.schn.persistence.AbstractPersistenceService;
import be.beme.schn.persistence.dao.SceneDao;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dorito on 22-03-16.
 */
@Repository
public class SceneDaoImpl extends AbstractPersistenceService implements SceneDao {


    @Override
    public int createScene(int chapterId, int previousSceneId) {
        jdbcTemplate.update("insert into Scene (chapter_id,previous_scene_id) values (?,?)"
                ,chapterId,previousSceneId);

        return jdbcTemplate.queryForObject("select last(id) from Scene where chapter_id=?",
                new Object[] {chapterId},Integer.class);
    }

    @Override
    public void setPicture(int sceneId, String pictureURL) {


    }

    @Override
    public void setNote(int sceneId, String note) {

        jdbcTemplate.update("update Scene SET notes=? where id=?",
                note, sceneId);
    }

    @Override
    public List<Scene> getAllScenesByChapter(int chapterId) {
        return jdbcTemplate.query("select * from Scene where chapter_id=?",
                new Object[]{chapterId},new SceneMapper());
    }

    @Override
    public void delete(int SceneId)
    {
        jdbcTemplate.update("delete from Scene where id=? ",SceneId);
    }

    private static final class SceneMapper implements RowMapper<Scene> {

        public Scene mapRow(ResultSet rs, int rowNum) throws SQLException {
            Scene scene = new Scene();
            scene.setId(rs.getInt("id"));
            scene.setChapterId(rs.getInt("chapter_id"));
            scene.setPicture(rs.getString("picture_url"));
            scene.setPrevious_scene_id(rs.getInt("previous_scene_id"));
            scene.setNote(rs.getString("notes"));
            return scene;
        }
    }
}
