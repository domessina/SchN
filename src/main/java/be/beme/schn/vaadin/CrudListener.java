package be.beme.schn.vaadin;

/**
 * Created by Dotista on 06-04-16.
 */
public interface CrudListener {

    void created(Object o);
    void updated(Object o);
    void deleted(Object o);

}
