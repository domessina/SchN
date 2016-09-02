package be.beme.schn.narrative.component;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Dorito on 22-03-16.
 */
@Getter
@Setter
public class Scene extends NarrativeComponent {

    private int chapterId;
    private int diagramId;
    private String picture ;
    private int place;
    private String note;
    private String tag;


}
