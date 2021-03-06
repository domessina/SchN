package be.beme.schn.vaadin.crud;

/**
 * Created by Dotista on 13-04-16.
 */
public interface CrudNotifier<T> {


   void addCrudListener(CrudListener<T> listener);

    void notifyCreated(T target);

   void notifyUpdated(T target);

    void notifyDeleted(T target);

}
