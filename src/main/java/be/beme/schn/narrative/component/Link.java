package be.beme.schn.narrative.component;

/**
 * Created by Dorito on 22-03-16.
 */

/**This creates a relationship form characterA to characterB. But also a trait challenged*/
public class Link implements NarrativeComponent  {                  //TODO renommer Link , lire pq dans la javadoc juste au dessus. Apopelle ça relation. Mais relation ET traitchallenged utilisent linkView

    private int id;
    private String name;
    private int fromCharacterId;
    private int toCharacterId;
    private boolean rel;
    private int scene_Id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    /**The name of element2 */
    public void setName(String name) {
        this.name = name;
    }

    public int getFromCharacterId() {
        return fromCharacterId;
    }

    public void setFromCharacterId(int fromCharacterId) {
        this.fromCharacterId = fromCharacterId;
    }

    public int getToCharacterId() {
        return toCharacterId;
    }

    public void setToCharacterId(int toCharacterId) {
        this.toCharacterId = toCharacterId;
    }

    public boolean isRel() {
        return rel;
    }

    public void setRel(boolean rel) {
        this.rel = rel;
    }

    public int getScene_Id() {
        return scene_Id;
    }

    public void setScene_Id(int scene_Id) {
        this.scene_Id = scene_Id;
    }
}
