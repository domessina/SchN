package be.beme.schn.vaadin;

import be.beme.schn.Constants;
import com.vaadin.event.Action;
import com.vaadin.ui.*;
import java.util.ArrayList;


/**
 * Created by Dotista on 06-04-16.
 */
public class TableCrud extends Table implements Action.Handler, Window.CloseListener{

    protected ArrayList<CrudListener> listeners;
    protected WindowCrud window;

    public TableCrud(String caption)
    {
        super(caption);
        this.listeners= new ArrayList<>();
        addActionHandler(this);
    }


    @Override
    public Action[] getActions(Object target, Object sender) {
        return new Action[]{Constants.ACTION_ADD,Constants.ACTION_MODIFY,Constants.ACTION_DELETE};
    }

    @Override
    public void handleAction(Action action, Object sender, Object target) {

        if(action==Constants.ACTION_ADD)
        {
            UI.getCurrent().addWindow(this.window);

        }
        else if(action==Constants.ACTION_MODIFY)
        {
            this.window.setItem(target,false);
            UI.getCurrent().addWindow(this.window);

        }
        else if(action==Constants.ACTION_DELETE)
        {
            this.window.setItem(target,true);
            UI.getCurrent().addWindow(this.window);

        }

    }


    protected void notifyCreated(Object target)
    {
        for(CrudListener listener:listeners)
        {
            listener.created(target);
        }
    }

    protected void notifyUpdated(Object target)
    {
        for(CrudListener listener:listeners)
        {
            listener.updated(target);
        }
    }

    protected void notifyDeleted(Object target)
    {
        for(CrudListener listener:listeners)
        {
            listener.deleted(target);
        }
    }


    @Override
    public void windowClose(Window.CloseEvent e) {
        if(this.window.isObjCreated())
        {
            notifyCreated(this.window.getCrudObj());
        }
        else if(this.window.isObjUpdated())
        {
            notifyUpdated(this.window.getCrudObj());
        }
        else if(this.window.isObjDeleted())
        {
            notifyDeleted(this.window.getCrudObj());
        }
    }


    public void addCrudListener(CrudListener listener)
    {
        this.listeners.add(listener);
    }


    protected void setSubmissionWindow(WindowCrud window)
    {
        this.window=window;
        this.window.addCloseListener(this);
    }


}
