package be.beme.schn.narrative.component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by Dorito on 22-03-16.
 */
public class Trait implements NarrativeComponent {

    private int id;
    private String name;
    private int[] scenes_id;
    private int characterId;

    public Trait(){}

    public Trait(String name, int id)
    {
        this.name=name;
        this.id=id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getScenes_id() {
        return scenes_id;
    }

    public void setScenes_id(int[] scenes_id) {
        this.scenes_id = scenes_id;
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    @Override
   public String toString() {
        return this.getName();
    }


}
