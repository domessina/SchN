package be.beme.schn.narrative.component;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Dorito on 22-03-16.
 */
@Getter
@Setter
public class Character extends NarrativeComponent{

    private String name;
    private String type;
    private String picture;
    private String note;
    private int diagram_id;

    @Override
    public String toString()
    {
        return this.name;
    }
}
