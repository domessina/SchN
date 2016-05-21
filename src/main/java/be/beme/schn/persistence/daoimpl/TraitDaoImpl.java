package be.beme.schn.persistence.daoimpl;

import be.beme.schn.narrative.component.NarrativeComponent;
import be.beme.schn.narrative.component.Trait;
import be.beme.schn.persistence.AbstractPersistenceService;
import be.beme.schn.persistence.dao.TraitDao;
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

public class TraitDaoImpl extends AbstractPersistenceService implements TraitDao {


    @Override
    public int create(NarrativeComponent component) {
        Trait trait=(Trait)component;
        jdbcTemplate.update("insert into \"Trait\" (character_id,name) values (?,?)"
                ,trait.getCharacterId(),trait.getName());

        return jdbcTemplate.queryForObject("select max(id) from \"Trait\" where character_id=?",
                new Object[] {trait.getCharacterId()},Integer.class);
    }

    @Override
    public void update(NarrativeComponent component)
    {
        Trait trait=(Trait)component;
        Object[] args = new Object[]{trait.getCharacterId(),trait.getName(),trait.getId()};
        jdbcTemplate.update("UPDATE \"Trait\" SET character_id=?,name=? WHERE id=?",args,new int[]{
                Types.INTEGER,
                Types.VARCHAR,
                Types.INTEGER});
    }

    @Override
    public void delete(int traitId)
    {
        jdbcTemplate.update("delete from public.\"Trait\" where id=? ",traitId);
    }

    @Override
    public Trait getNComponent(int componentId) {
        return jdbcTemplate.queryForObject("select * from \"Trait\" where id=?",new Object[]{componentId},new TraitMapper());
    }

    @Override
    public void setScenes(int traitId, String[] scenesArray) {

        jdbcTemplate.update("update Trait SET scenes_id=? where id=?",
                scenesArray, traitId);
    }


    @Override
    public List<Trait> getTraitsByCharacter(int characterId) {
        return jdbcTemplate.query("select * from \"Trait\" where character_id=?",
                new Object[]{characterId},new TraitMapper());
    }

    @Override
    public void deleteAllTraitsByCharacter(int characterId) {
        jdbcTemplate.update("delete from public.\"Trait\" where character_id=? ",characterId);
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
