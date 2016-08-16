package be.beme.schn.vaadin.crud;

/**
 * Created by Dotista on 06-04-16.
 */
public interface CrudListener<T> {

    void created(T o);
    void updated(T o);
    void deleted(T o);

}
