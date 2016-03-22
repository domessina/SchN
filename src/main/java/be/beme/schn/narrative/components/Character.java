package be.beme.schn.narrative.components;

/**
 * Created by Dorito on 22-03-16.
 */
public class Character {

    private int id;
    private String name;
    private String type;
    private byte[] picture;
    private String note;
    private int diagram_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getDiagram_id() {
        return diagram_id;
    }

    public void setDiagram_id(int diagram_id) {
        this.diagram_id = diagram_id;
    }

}
