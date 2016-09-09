package be.beme.schn.persistence.daoimpl;

import be.beme.schn.narrative.component.NarrativeComponent;
import be.beme.schn.narrative.component.Scene;
import be.beme.schn.persistence.AbstractPersistenceService;
import be.beme.schn.persistence.dao.SceneDao;
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
public class SceneDaoImpl extends AbstractPersistenceService implements SceneDao {


    @Override
    public int create(NarrativeComponent component) {
        Scene s=(Scene)component;
        jdbcTemplate.update("insert into \"Scene\" (chapter_id,tag,place,picture,notes) values (?,?,?,?,?,?)"
                ,s.getChapterId(),s.getTag(),s.getPlace(),s.getPicture(),s.getNote(),s.getDiagramId());

        return jdbcTemplate.queryForObject("select max(id) from \"Scene\" where chapter_id=?",
                new Object[] {s.getChapterId()},Integer.class);
    }

    @Override
    public int update(NarrativeComponent component) {
       Scene s=(Scene)component;
        Object[] args= new Object[]{s.getTag(),s.getPlace(),s.getPicture(),s.getNote(),s.getId()};
        return jdbcTemplate.update("UPDATE public.\"Scene\" SET tag=?,place=?,picture=?, notes=? WHERE id=?",args,new int[]{
                Types.VARCHAR,
                Types.INTEGER,
                Types.VARCHAR,
                Types.VARCHAR,
                Types.INTEGER});//si probleme avec LONGVARCHAR pour TEXT, use VARCHAR
    }

    @Override
    public int delete(int SceneId)
    {
      return jdbcTemplate.update("delete from \"Scene\" where id=? ",SceneId);
    }


    @Override
    public List<Scene> getAllScenesByChapter(int chapterId) {
        return jdbcTemplate.query("select * from \"Scene\" where chapter_id=? order by place asc",
                new Object[]{chapterId},new SceneMapper());
    }

    @Override
    public List<Scene> getAllScenesByDiagram(int diagramid) {
        return jdbcTemplate.query("select * from \"Scene\" where diagram_id=? order by place asc",
                new Object[]{diagramid}, new SceneMapper());
    }

    @Override
    public Scene getNComponent(int id) {
        return jdbcTemplate.queryForObject("select * from \"Scene\" where id=?",
                new Object[]{id},new SceneMapper());
    }

    //method written here instead of chapterDaoImpl because we work on Scene table
    @Override
    public int getNumberOfScenes(int chapterId)
    {
        return jdbcTemplate.queryForObject("select count(*) from \"Scene\" where chapter_id=?",
                new Object[] {chapterId},Integer.class);
    }


    @Override
    public void setPlace(int place, int id)
    {
        jdbcTemplate.update("update \"Scene\" SET place=? where id=?",place, id);
    }



    private static final class SceneMapper implements RowMapper<Scene> {

        public Scene mapRow(ResultSet rs, int rowNum) throws SQLException {
            Scene scene = new Scene();
            scene.setId(rs.getInt("id"));
            scene.setChapterId(rs.getInt("chapter_id"));
            scene.setPicture(rs.getString("picture"));
            scene.setPlace(rs.getInt("place"));
            scene.setTag(rs.getString("tag"));
            scene.setNote(rs.getString("notes"));
            scene.setDiagramId(rs.getInt("diagram_id"));
            return scene;
        }
    }
}
