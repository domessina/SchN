package be.beme.schn.narrative.component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Dorito on 22-03-16.
 */
@Getter
@Setter
@AllArgsConstructor
public class Diagram extends NarrativeComponent {


    private int userId;
    private String title;
    private String pictureId;

    public Diagram(int userId){
        this.userId =userId;
    }

    public Diagram(){}
}
