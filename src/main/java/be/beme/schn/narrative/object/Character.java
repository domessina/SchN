package be.beme.schn.narrative.object;

import java.util.List;

/**
 * Created by Dorito on 22-03-16.
 */
public class Character {

    private int id;
    private String name;
    private String type;
    private String picture;
    private String note;
    private List<UserProperty> userPropertyList;
    private List<Trait> traitList;
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

    public List<UserProperty> getUserPropertyList() {
        return userPropertyList;
    }

    public void setUserPropertyList(List<UserProperty> userPropertyList) {
        this.userPropertyList = userPropertyList;
    }

    public List<Trait> getTraitList() {
        return traitList;
    }

    public void setTraitList(List<Trait> traitList) {
        this.traitList = traitList;
    }
}
