package be.beme.schn.narrative.component;

/**
 * Created by Dotista on 01-07-16.
 */
public enum NarrativeComponentType {

    NC_Diagram("diagram"),
    NC_Chapter("chapter"),
    NC_Scene("scene"),
    NC_Character("character"),
    NC_Trait("trait");

    private String type;

    NarrativeComponentType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

    public String getType(){
        return type;
    }
}
