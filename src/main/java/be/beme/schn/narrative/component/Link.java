package be.beme.schn.narrative.component;

/**
 * Created by Dorito on 22-03-16.
 */

import lombok.Getter;
import lombok.Setter;

/**This creates a relationship form characterA to characterB. But also a trait challenged*/
@Getter
@Setter
public class Link extends NarrativeComponent  {                  //TODO renommer Link , lire pq dans la javadoc juste au dessus. Apopelle ça relation. Mais relation ET traitchallenged utilisent linkView

    private String name;
    private int fromCharacterId;
    private int toCharacterId;
    private boolean rel;
    private int scene_Id;


}
