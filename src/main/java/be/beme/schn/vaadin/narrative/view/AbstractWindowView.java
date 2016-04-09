package be.beme.schn.vaadin.narrative.view;

import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by Dorito on 29-03-16.
 */
public abstract class AbstractWindowView extends Window implements NarrativeView, Button.ClickListener{


    protected VerticalLayout rootLayout;
    protected Button buttonErase;
    protected Button buttonSave;

    public AbstractWindowView(String caption)
    {
        super(caption);
        setResizable(false);
        setModal(true);
        this.rootLayout= new VerticalLayout();
        this.rootLayout.setMargin(true);
        this.rootLayout.setSizeFull();
        this.buttonSave = new Button("Save");
        this.buttonErase = new Button("Erase");
    }


    public final void setWrappedContent(Component component)        //it' final, the daughet class can't override it
    {
        Component wrapped=component;
        this.rootLayout.addComponent(component);

        this.buttonSave.addClickListener(this);
        this.buttonSave.setStyleName(ValoTheme.BUTTON_FRIENDLY);

        this.buttonErase.addClickListener(this);
        this.buttonErase.setStyleName(ValoTheme.BUTTON_DANGER,true);
        this.buttonErase.setStyleName(ValoTheme.BUTTON_TINY,true);

        HorizontalLayout horizontalLayout= new HorizontalLayout();
        horizontalLayout.addComponent(this.buttonErase);
        horizontalLayout.setComponentAlignment(this.buttonErase,Alignment.MIDDLE_CENTER);
        horizontalLayout.addComponent(this.buttonSave);
        horizontalLayout.setComponentAlignment(this.buttonSave,Alignment.MIDDLE_RIGHT);
        horizontalLayout.setWidth(100,Unit.PERCENTAGE);

        this.rootLayout.addComponent(horizontalLayout);
        this.rootLayout.setExpandRatio(wrapped,15);
        this.rootLayout.setExpandRatio(horizontalLayout,1);

        super.setContent(this.rootLayout);
    }

    @Override
    public abstract void buttonClick(Button.ClickEvent event);
}
