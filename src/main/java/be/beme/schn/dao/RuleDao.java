package be.beme.schn.dao;


import be.beme.schn.narrative.components.Rule;

import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
public interface RuleDao {

    int createRule(int diagramId, String rule, boolean enabled);

    void setEnabled(int ruleId, boolean enabled);

    boolean isEnabled(int ruleId);

    List<Rule> getAllRulesByDiagram(int diagramId);

    void delete(int ruleId);


}
