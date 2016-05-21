package be.beme.schn.narrative.component;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Dorito on 22-03-16.
 */
@Getter
@Setter
public class Rule extends NarrativeComponent {


    private String rule;
    private boolean enabled;
    private int diagramId;

}
