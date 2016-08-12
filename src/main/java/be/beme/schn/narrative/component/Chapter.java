package be.beme.schn.narrative.component;

import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Getter;

import java.util.List;

/**
 * Created by Dorito on 22-03-16.
 */
@Getter
@lombok.Setter
public class Chapter extends NarrativeComponent {

    private short phase;
    private String title;
    private short position;
    private int diagramId;
    private String note;


}
