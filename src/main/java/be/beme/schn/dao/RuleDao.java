package be.beme.schn.dao;

/**
 * Created by Dorito on 21-03-16.
 */
public interface RuleDao {

    int createRule(int diagramId, String rule);

    void setEnabled(boolean enabled);

    boolean isEnabled(int ruleId);


}
