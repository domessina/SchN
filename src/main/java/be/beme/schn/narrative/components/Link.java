package be.beme.schn.narrative.components;

/**
 * Created by Dorito on 22-03-16.
 */
public class Link  {

    private int id;
    private String name;
    private int elementId_1;
    private int elementId_2;
    private boolean rel;
    private int scene_Id;

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

    public int getElementId_1() {
        return elementId_1;
    }

    public void setElementId_1(int elementId_1) {
        this.elementId_1 = elementId_1;
    }

    public int getElementId_2() {
        return elementId_2;
    }

    public void setElementId_2(int elementId_2) {
        this.elementId_2 = elementId_2;
    }

    public boolean isRel() {
        return rel;
    }

    public void setRel(boolean rel) {
        this.rel = rel;
    }

    public int getScene_Id() {
        return scene_Id;
    }

    public void setScene_Id(int scene_Id) {
        this.scene_Id = scene_Id;
    }
}
