package be.beme.schn.persistence.daoimpl;

import be.beme.schn.persistence.AbstractPersistenceService;
import be.beme.schn.persistence.dao.UserDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Dorito on 25-03-16.
 */
@Repository
public class UserDaoImpl extends AbstractPersistenceService implements UserDao {


    @Override
    public String getPseudo(int userId) {
        return jdbcTemplate.queryForObject("select pseudo from public.\"User\" where id=?",
                new Object[] {userId},String.class);
    }

    @Override
    public int getNbDiagrams(int userId) {
        return 0;
    }

    @Override
    public int getActualDiagram(int userId) {
        return jdbcTemplate.queryForObject("select actual_diagram_id from public.\"User\" where id=?",
                new Object[] {userId},Integer.class);
    }

    @Override
    public void setActualDiagram(int userId, int diagramId) {
        jdbcTemplate.update("update \"User\" SET actual_diagram_id=? where id=?",diagramId, userId);

    }

    @Override
    public void decreaseNumberOfDiagrams(int userId){
        jdbcTemplate.update("update public.\"User\" set nb_diagrams=nb_diagrams-1 where id=?", userId);
    }

    @Override
    public void increaseNumberOfDiagrams(int userId){
        jdbcTemplate.update("update public.\"User\" set nb_diagrams=nb_diagrams+1 where id=?", userId);
    }
}
