package be.beme.schn.vaadin;

/**
 * Created by Dotista on 13-04-16.
 */
public interface CrudNotifier {


   void addCrudListener(CrudListener listener);

    void notifyCreated(Object target);

   void notifyUpdated(Object target);

    void notifyDeleted(Object target);

}
