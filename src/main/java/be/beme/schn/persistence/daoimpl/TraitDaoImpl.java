package be.beme.schn.persistence.daoimpl;

import be.beme.schn.narrative.component.Trait;
import be.beme.schn.persistence.AbstractPersistenceService;
import be.beme.schn.persistence.dao.TraitDao;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dorito on 22-03-16.
 */
@Repository
public class TraitDaoImpl extends AbstractPersistenceService implements TraitDao {


    @Override
    public int createTrait(int characterId, String name) {
        jdbcTemplate.update("insert into Trait (character_id) values (?)"
                ,characterId);

        return jdbcTemplate.queryForObject("select last(id) from Trait where character_id=?",
                new Object[] {characterId},Integer.class);
    }

    @Override
    public void setScenes(int traitId, String[] scenesArray) {

        jdbcTemplate.update("update Trait SET scenes_id=? where id=?",
                scenesArray, traitId);
    }


    @Override
    public List<Trait> getAllTraitsByCharacter(int characterId) {
        return jdbcTemplate.query("select * from Trait where character_id=?",
                new Object[]{characterId},new TraitMapper());
    }

    @Override
    public void delete(int traitId)
    {
        jdbcTemplate.update("delete from Trait where id=? ",traitId);
    }

    private static final class TraitMapper implements RowMapper<Trait> {

        public Trait mapRow(ResultSet rs, int rowNum) throws SQLException {
            Trait trait = new Trait();
            trait.setId(rs.getInt("id"));
            trait.setName(rs.getString("name"));
           // trait.setScenes_id(rs.getArray("scenes_id"));           //TODO pas trouver comment prendre tabelau de varchar
            trait.setCharacterId(rs.getInt("character_id"));
            return trait;
        }
    }
}
