package be.beme.schn.vaadin;

import be.beme.schn.Constants;
import be.beme.schn.narrative.component.Trait;
import be.beme.schn.vaadin.narrative.TraitTableCrud;
import com.vaadin.event.Action;
import com.vaadin.ui.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Dotista on 06-04-16.
 */
public abstract class TableCrud<T> extends Table implements Action.Handler, Window.CloseListener, CrudNotifier<Trait>{

    protected ArrayList<CrudListener> listeners;
    protected WindowCrud window;

    public TableCrud(String caption)
    {
        super(caption);
        this.listeners= new ArrayList<>();
        addActionHandler(this);
    }


    public abstract void fillTable(List<T> list) throws NullPointerException;

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
        else if(target!=null)                    //in the case where user clicks on empty rows, do nothing
        {

            if(action==Constants.ACTION_MODIFY)
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
    }

    @Override
    public void notifyCreated(Trait target)
    {
        for(CrudListener listener:listeners)
        {
            listener.created(target);
        }
    }

    @Override
    public void notifyUpdated(Trait target)
    {
        for(CrudListener listener:listeners)
        {
            listener.updated(target);
        }
    }

    @Override
    public void notifyDeleted(Trait target)
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
            notifyCreated((Trait)this.window.getCrudObj());
        }
        else if(this.window.isObjUpdated())
        {
            notifyUpdated((Trait)this.window.getCrudObj());
        }
        else if(this.window.isObjDeleted())
        {
            notifyDeleted((Trait)this.window.getCrudObj());
        }
    }

    @Override
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
