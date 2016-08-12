package be.beme.schn.vaadin;

import com.vaadin.ui.*;


/**
 * Created by Dotista on 06-04-16.
 */
public abstract class WindowCrud<T> extends Window implements Button.ClickListener{

    protected FormLayout formLayout;
    protected Button OKBtn;




    public WindowCrud(String caption, FormLayout formLayout)
    {
        super(caption);
        this.formLayout= formLayout;
        setModal(true);
        setResizable(false);
        setDraggable(true);
        setContent(buildContent());
    }

    private Layout buildContent()
    {
        OKBtn= new Button("OK");
        OKBtn.addClickListener(this);
        formLayout.setMargin(true);
        formLayout.addComponent(OKBtn);
        formLayout.setComponentAlignment(OKBtn,Alignment.MIDDLE_CENTER);

        try{
            ((TextField)formLayout.getComponent(0)).selectAll();
        }
        catch(ClassCastException e)
        {
            e.printStackTrace();
        }


        return formLayout;
    }

    public abstract boolean isObjCreated();
    public abstract boolean isObjUpdated();
    public abstract boolean isObjDeleted();
    public abstract T getCrudObj();
    @Override
    public abstract void buttonClick(Button.ClickEvent event);
    protected abstract void setItem(Object itemId, boolean delete);

}
