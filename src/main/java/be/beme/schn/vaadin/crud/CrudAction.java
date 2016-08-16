package be.beme.schn.vaadin.crud;

/**
 * Created by Dotista on 07-04-16.
 */
public interface CrudAction<T>  {

    boolean create(T t);
    boolean update(T t);
    boolean delete(T t);
}
