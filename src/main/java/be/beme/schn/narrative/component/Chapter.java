package be.beme.schn.narrative.component;

import java.util.List;

/**
 * Created by Dorito on 22-03-16.
 */
public class Chapter implements NarrativeComponent {


    private int id;
    private String phase;
    private String title;
    private int previousChapterId;
    private int diagramId;
    private String note;
    private List<Scene> scenes;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public int getId() {
        return id;
    }
    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPreviousChapterId() {
        return previousChapterId;
    }

    public void setPreviousChapterId(int previousChapterId) {
        this.previousChapterId = previousChapterId;
    }

    public int getDiagramId() {
        return diagramId;
    }

    public void setDiagramId(int diagramId) {
        this.diagramId = diagramId;
    }

    public List<Scene> getScenes() {
        return scenes;
    }

    public void setScenes(List<Scene> scenes) {
        this.scenes = scenes;
    }
}
