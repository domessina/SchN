package be.beme.schn.narrative.component;

import java.util.List;

/**
 * Created by Dorito on 22-03-16.
 */
public class Chapter implements NarrativeComponent {


    private int id;
    private short phase;
    private String title;
    private short position;
    private int diagramId;
    private String note;

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

    public short getPhase() {
        return phase;
    }

    public void setPhase(short phase) {
        this.phase = phase;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public short getPosition() {
        return position;
    }

    public void setPosition(short previousChapterId) {
        this.position = previousChapterId;
    }

    public int getDiagramId() {
        return diagramId;
    }

    public void setDiagramId(int diagramId) {
        this.diagramId = diagramId;
    }

}
