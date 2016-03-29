package be.beme.schn.narrative.component;

/**
 * Created by Dorito on 22-03-16.
 */
public class Element implements NarrativeComponent {

    private int id;
    private String note;
    private String name;
    private  int diagramId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDiagramId() {
        return diagramId;
    }

    public void setDiagramId(int diagramId) {
        this.diagramId = diagramId;
    }
}
