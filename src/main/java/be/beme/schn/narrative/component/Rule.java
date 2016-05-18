package be.beme.schn.narrative.component;

/**
 * Created by Dorito on 22-03-16.
 */
public class Rule extends NarrativeComponent {


    private String rule;
    private boolean enabled;
    private int diagramId;

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getDiagramId() {
        return diagramId;
    }

    public void setDiagramId(int diagramId) {
        this.diagramId = diagramId;
    }
}
