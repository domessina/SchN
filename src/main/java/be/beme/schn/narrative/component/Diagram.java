package be.beme.schn.narrative.component;

/**
 * Created by Dorito on 22-03-16.
 */
public class Diagram extends NarrativeComponent {


    private int user_id;
    private String title;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
