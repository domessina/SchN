package be.beme.schn.narrative.component;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Dorito on 22-03-16.
 */
@Getter
@Setter
public class Outline extends NarrativeComponent {

    private int propertyId;
    private String type;
    private String value;
    private String color;
}
