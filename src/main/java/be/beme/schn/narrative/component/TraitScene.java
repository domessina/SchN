package be.beme.schn.narrative.component;

/**
 * Created by Dorito on 24-03-16.
 */
public class TraitScene implements NarrativeComponent{

    private int traitId;
    private int sceneId;
    private int diagramId;

    public int getTraitId() {
        return traitId;
    }

    public void setTraitId(int traitId) {
        this.traitId = traitId;
    }

    public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    public int getDiagramId() {
        return diagramId;
    }

    public void setDiagramId(int diagramId) {
        this.diagramId = diagramId;
    }
}
