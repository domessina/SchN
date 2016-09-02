package be.beme.schn.narrative.component;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Dorito on 22-03-16.
 */
@Getter
@Setter
public class Trait extends NarrativeComponent {

    private String name;
    private int characterId;
    private int diagramId;

    public Trait(){}

    public Trait(String name, int id)
    {
        this.name=name;
        this.id=id;
    }

    @Override
   public String toString() {
        return this.getName();
    }


}
