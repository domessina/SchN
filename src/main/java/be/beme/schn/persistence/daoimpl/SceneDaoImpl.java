package be.beme.schn.persistence.daoimpl;

import be.beme.schn.narrative.component.Scene;
import be.beme.schn.persistence.AbstractPersistenceService;
import be.beme.schn.persistence.dao.SceneDao;
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
public class SceneDaoImpl extends AbstractPersistenceService implements SceneDao {


    @Override
    public int create(int chapterId, int previousSceneId) {
        jdbcTemplate.update("insert into Scene (chapter_id,previous_scene_id) values (?,?)"
                ,chapterId,previousSceneId);

        return jdbcTemplate.queryForObject("select last(id) from Scene where chapter_id=?",
                new Object[] {chapterId},Integer.class);
    }

    @Override
    public void update(Object[] args) {
        jdbcTemplate.update("UPDATE public.\"Scene\" SET tag=?,place=?,picture=?, note=? WHERE id=?",args,new int[]{
                Types.VARCHAR,
                Types.INTEGER,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.INTEGER});//si probleme avec LONGVARCHAR pour TEXT, use VARCHAR
    }

    public void delete(int SceneId)
    {
        jdbcTemplate.update("delete from Scene where id=? ",SceneId);
    }


    @Override
    public List<Scene> getAllScenesByChapter(int chapterId) {
        return jdbcTemplate.query("select * from Scene where chapter_id=?",
                new Object[]{chapterId},new SceneMapper());
    }



    private static final class SceneMapper implements RowMapper<Scene> {

        public Scene mapRow(ResultSet rs, int rowNum) throws SQLException {
            Scene scene = new Scene();
            scene.setId(rs.getInt("id"));
            scene.setChapterId(rs.getInt("chapter_id"));
            scene.setPicture(rs.getString("picture"));
            scene.setPlace(rs.getInt("place"));
            scene.setNote(rs.getString("notes"));
            return scene;
        }
    }
}
