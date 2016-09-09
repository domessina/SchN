package be.beme.schn.narrative.component;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Dorito on 09-09-16.
 */
public class CharacterScene {

    @JsonProperty("diagramId")
    public int diagramId;
    @JsonProperty("characterId")
    public int characterId;
    @JsonProperty("sceneId")
    public int sceneId;
}
