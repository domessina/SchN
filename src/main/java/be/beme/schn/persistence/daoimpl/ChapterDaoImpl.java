package be.beme.schn.persistence.daoimpl;

import be.beme.schn.narrative.component.Chapter;
import be.beme.schn.persistence.AbstractPersistenceService;
import be.beme.schn.persistence.dao.ChapterDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
@Repository
@Transactional
public class ChapterDaoImpl extends AbstractPersistenceService implements ChapterDao {



    @Override
    public int create(int diagramId, short phase, String title, short place, String note) {

        jdbcTemplate.update("insert into \"Chapter\" (diagram_id,phase,title, place, notes) values (?,?,?,?,?)"
                ,diagramId, phase, title, place,note);

        return jdbcTemplate.queryForObject("select max(id) from \"Chapter\" where diagram_id=?",
                  new Object[] {diagramId},Integer.class);
    }

    @Override
    public void update(Object[] args) {

        jdbcTemplate.update("UPDATE public.\"Chapter\" SET phase=?,title=?,place=?,notes=? WHERE id=?",args,new int[]{
                Types.SMALLINT,
                Types.VARCHAR,
                Types.SMALLINT,
                Types.VARCHAR,
                Types.INTEGER});
    }

    @Override
    public void setNote(int chapterId, String note) {

        jdbcTemplate.update("update Chapter SET notes=? where id=?",
                note, chapterId);

    }



    @Override
    public Chapter getChapterById(int Id)
    {

        return jdbcTemplate.queryForObject("select * from \"Chapter\" where id=?",
                new Object[]{Id},new ChapterMapper());
    }

    @Override
    public List<Chapter> getAllChaptersByDiagram(int diagramId) {
        return jdbcTemplate.query("select * from \"Chapter\" where diagram_id=?",
                new Object[]{diagramId},new ChapterMapper());
    }

    @Override
    public void setPosition(int pos, int id) {
        jdbcTemplate.update("UPDATE public.\"Chapter\" SET place=? WHERE id=?",pos,id);
    }

    @Override
    public List<Chapter> getAllChaptersByPhase(short phase, int diagramId)
    {
        return jdbcTemplate.query("select * from \"Chapter\" where phase=? and diagram_id=? order by place asc",
                new Object[]{phase,diagramId},new ChapterMapper());
    }

    @Override
    public void delete(int chapterId) {
        jdbcTemplate.update("delete from \"Chapter\" where id=? ",chapterId);
    }




    private static final class ChapterMapper implements RowMapper<Chapter> {

        public Chapter mapRow(ResultSet rs, int rowNum) throws SQLException {
            Chapter chapter = new Chapter();
            chapter.setId(rs.getInt("id"));
            chapter.setPhase(rs.getShort("phase"));
            chapter.setTitle(rs.getString("title"));
            chapter.setNote(rs.getString("notes"));
            chapter.setPosition(rs.getShort("place"));
            chapter.setDiagramId(rs.getInt("diagram_id"));
            return chapter;
        }
    }
}
