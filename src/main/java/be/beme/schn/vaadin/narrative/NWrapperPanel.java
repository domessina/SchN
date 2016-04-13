package be.beme.schn.vaadin.narrative;


import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by Dotista on 10-04-16.
 */
public class NWrapperPanel extends Panel implements NWrapper {

    protected VerticalLayout rootLayout;
    protected HorizontalLayout horizontalLayout;
    protected Button buttonErase;
    protected Button buttonSave;
    protected Button buttonSet;

    public NWrapperPanel()
    {
        this("");
    }

    public NWrapperPanel(String caption)
    {
        super(caption);
        this.rootLayout= new VerticalLayout();
        this.horizontalLayout=new HorizontalLayout();
        this.rootLayout.setMargin(true);
        this.buttonSave = new Button("Save");
        this.buttonErase = new Button("Erase");
        this.buttonSet= new Button(FontAwesome.GEAR);
    }

    public NWrapperPanel(Component component)
    {
        this("");
        setWrappedContent(component);
    }

    public void setWrappedContent(Component component)        //it' final, the daughet class can't override it
    {
        Component wrapped=component;
        this.rootLayout.addComponent(component);

        this.buttonSave.setStyleName(ValoTheme.BUTTON_FRIENDLY);

        this.buttonErase.setStyleName(ValoTheme.BUTTON_DANGER,true);
        this.buttonErase.setStyleName(ValoTheme.BUTTON_TINY,true);


        horizontalLayout= new HorizontalLayout();
        horizontalLayout.addComponent(this.buttonErase);
        horizontalLayout.setComponentAlignment(this.buttonErase, Alignment.MIDDLE_LEFT);
        horizontalLayout.addComponent(this.buttonSet);
        horizontalLayout.setComponentAlignment(this.buttonSet,Alignment.MIDDLE_CENTER);
        horizontalLayout.addComponent(this.buttonSave);
        horizontalLayout.setComponentAlignment(this.buttonSave,Alignment.MIDDLE_RIGHT);
        horizontalLayout.setWidth(100,Unit.PERCENTAGE);

        this.rootLayout.addComponent(horizontalLayout);
        this.rootLayout.setExpandRatio(wrapped,15);
        this.rootLayout.setExpandRatio(horizontalLayout,1);

        super.setContent(this.rootLayout);
    }



    @Override
    public void setSizeFull()
    {
        super.setSizeFull();
        this.rootLayout.setHeight(100,Unit.PERCENTAGE);
        this.rootLayout.setWidth(100,Unit.PERCENTAGE);
    }

    public void close()
    {
        if(this.getParent() instanceof Window)
        {
            ((Window) getParent()).close();
        }
        else if(this.getParent() instanceof Layout)
        {
            ((Layout) getParent()).removeComponent(this);
        }
    }



    public Layout getRootLayout()
    {
        return this.rootLayout;
    }

    public HorizontalLayout getHorizontalLayout() {
        return horizontalLayout;
    }

    public Button getButtonSet() {
        return buttonSet;
    }

    public Button getButtonErase() {
        return buttonErase;
    }

    public Button getButtonSave() {
        return buttonSave;
    }
}
