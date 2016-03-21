package be.beme.schn.daoimpl;

import be.beme.schn.dao.ChapterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by Dorito on 21-03-16.
 */
public class ChapterDaoImpl implements ChapterDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int createChapter(int diagramId, String phase, String title, int previousChapterId) {

        jdbcTemplate.update("insert into Chapter (diagram_id,phase,title, previous_chapter_id) values (?,?,?,?)"
                ,diagramId, phase, title, previousChapterId);

        return jdbcTemplate.queryForObject("select last(id) from Chapter where diagram_id=?",
                  new Object[] {diagramId},Integer.class);
    }


    @Override
    public String setNote(int chapterId, String note) {

        jdbcTemplate.update("update Chapter SET notes=? where id=?",
                note, chapterId);

        return note;
    }
}
