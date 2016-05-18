package be.beme.schn.narrative.component;

/**
 * Created by Dorito on 22-03-16.
 */
public class Outline extends NarrativeComponent {

    private int propertyId;
    private String type;
    private String value;
    private String color;

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
