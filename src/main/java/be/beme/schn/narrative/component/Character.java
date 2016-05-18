package be.beme.schn.narrative.component;

import java.util.List;

/**
 * Created by Dorito on 22-03-16.
 */
public class Character extends NarrativeComponent{

    private String name;
    private String type;
    private String picture;
    private String note;
    private List<UserProperty> userProperties;
    private int diagram_id;

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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
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

    public List<UserProperty> getUserProperties() {
        return userProperties;
    }

    public void setUserProperties(List<UserProperty> userProperties) {
        this.userProperties = userProperties;
    }


    @Override
    public String toString()
    {
        return this.name;
    }
}
