package be.beme.schn.narrative.object;

/**
 * Created by Dorito on 22-03-16.
 */
public class Scene {


    private int id;
    private int chapterId;
    private String picture ;
    private int previous_scene_id;
    private String note;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }


    public int getPrevious_scene_id() {
        return previous_scene_id;
    }

    public void setPrevious_scene_id(int previous_scene_id) {
        this.previous_scene_id = previous_scene_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


}
