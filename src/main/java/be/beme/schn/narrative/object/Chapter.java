package be.beme.schn.narrative.object;

/**
 * Created by Dorito on 22-03-16.
 */
public class Chapter  {


    private int id;
    private String phase;
    private String title;
    private int previousChapterId;
    private int diagramId;
    private String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

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

}
