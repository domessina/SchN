package be.beme.schn.daoimpl;

import be.beme.schn.dao.RuleDao;
import be.beme.schn.narrative.components.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Dorito on 22-03-16.
 */
@Repository
public class RuleDaoImpl implements RuleDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public int createRule(int diagramId, String rule, boolean enabled) {
        jdbcTemplate.update("insert into Rule (diagram_id,rule,enabled) values (?,?,?)"
                ,diagramId, rule, enabled);

        return jdbcTemplate.queryForObject("select last(id) from Rule where diagram_id=?",
                new Object[] {diagramId},Integer.class);
    }

    @Override
    public void setEnabled(int ruleId, boolean enabled) {
        jdbcTemplate.update("update Outline SET enabled=? where id=?",
               enabled,ruleId);
    }

    @Override
    public boolean isEnabled(int ruleId) {
        return jdbcTemplate.queryForObject("select enabled from Rule where id=?",
                new Object[] {ruleId},Boolean.class);
    }

    @Override
    public List<Rule> getAllRulesByDiagram(int diagramId) {
        return jdbcTemplate.query("select * from Rule where diagram_id=?",
                new Object[]{diagramId},new RuleMapper());
    }

    @Override
    public void delete(int RuleId)
    {
        jdbcTemplate.update("delete from Rule where id=? ",RuleId);
    }

    private static final class RuleMapper implements RowMapper<Rule> {

        public Rule mapRow(ResultSet rs, int rowNum) throws SQLException {
            Rule chapter = new Rule();
            chapter.setId(rs.getInt("id"));
            chapter.setRule(rs.getString("rule"));
            chapter.setEnabled(rs.getBoolean("enabled"));
            chapter.setDiagramId(rs.getInt("diagram_id"));
            return chapter;
        }
    }
}
