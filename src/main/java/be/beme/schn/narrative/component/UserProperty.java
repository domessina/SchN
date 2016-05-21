package be.beme.schn.narrative.component;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Dorito on 22-03-16.
 */
@Getter
@Setter
public class UserProperty extends NarrativeComponent{

    private String name;
    private boolean typeList;
    private String value;
    private int element_id;

}
