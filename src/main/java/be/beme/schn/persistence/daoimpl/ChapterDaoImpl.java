package be.beme.schn.persistence.daoimpl;

import be.beme.schn.narrative.component.Chapter;
import be.beme.schn.persistence.AbstractPersistenceService;
import be.beme.schn.persistence.dao.ChapterDao;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
@Repository
public class ChapterDaoImpl extends AbstractPersistenceService implements ChapterDao {


    @Override
    public int createChapter(int diagramId, String phase, String title, int previousChapterId) {

        jdbcTemplate.update("insert into Chapter (diagram_id,phase,title, previous_chapter_id) values (?,?,?,?)"
                ,diagramId, phase, title, previousChapterId);

        return jdbcTemplate.queryForObject("select last(id) from Chapter where diagram_id=?",
                  new Object[] {diagramId},Integer.class);
    }


    @Override
    public void setNote(int chapterId, String note) {

        jdbcTemplate.update("update Chapter SET notes=? where id=?",
                note, chapterId);

    }

    @Override
    public Chapter getChapterById(int diagramId)
    {
        return jdbcTemplate.queryForObject("select * from Chapter where id=?",
                new Object[]{diagramId},new ChapterMapper());
    }

    @Override
    public List<Chapter> getAllChaptersByDiagram(int diagramId) {
        return jdbcTemplate.query("select * from Chapter where diagram_id=?",
                new Object[]{diagramId},new ChapterMapper());
    }

    @Override
    public void delete(int chapterId) {
        jdbcTemplate.update("delete from Chapter where id=? ",chapterId);
    }


    private static final class ChapterMapper implements RowMapper<Chapter> {

        public Chapter mapRow(ResultSet rs, int rowNum) throws SQLException {
            Chapter chapter = new Chapter();
            chapter.setId(rs.getInt("id"));
            chapter.setPhase(rs.getString("phase"));
            chapter.setTitle(rs.getString("title"));
            chapter.setNote(rs.getString("notes"));
            chapter.setPreviousChapterId(rs.getInt("previous_chapter_id"));
            chapter.setDiagramId(rs.getInt("diagram_id"));
            return chapter;
        }
    }
}
