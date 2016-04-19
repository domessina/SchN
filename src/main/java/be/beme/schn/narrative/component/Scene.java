package be.beme.schn.narrative.component;

/**
 * Created by Dorito on 22-03-16.
 */
public class Scene implements NarrativeComponent {


    private int id;
    private int chapterId;
    private String picture ;
    private int place;
    private String note;
    private String tag;

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


    public int getPlace() {
        return place;
    }

    public void setPlace(int previous_scene_id) {
        this.place = previous_scene_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
