package be.beme.schn.narrative.object;

/**
 * Created by Dorito on 22-03-16.
 */
public class UserProperty {

    private int id;
    private String name;
    private boolean typeList;
    private String value;
    private int element_id;

    public UserProperty()
    {

    }
    public UserProperty(String name, String value)
    {
        this.name=name;
        this.value=value;
    }

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

    public boolean isTypeList() {
        return typeList;
    }

    public void setTypeList(boolean typeList) {
        this.typeList = typeList;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getElement_id() {
        return element_id;
    }

    public void setElement_id(int element_id) {
        this.element_id = element_id;
    }
}
