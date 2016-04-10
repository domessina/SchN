package be.beme.schn.vaadin.narrative.view;


import be.beme.schn.vaadin.narrative.presenter.NarrativePresenter;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by Dotista on 10-04-16.
 */
public abstract class AbstractPanelView extends Panel implements NarrativeView, Button.ClickListener {

    protected VerticalLayout rootLayout;
    protected Button buttonErase;
    protected Button buttonSave;


    public AbstractPanelView(String caption)
    {
        super(caption);
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
        horizontalLayout.setComponentAlignment(this.buttonErase, Alignment.MIDDLE_CENTER);
        horizontalLayout.addComponent(this.buttonSave);
        horizontalLayout.setComponentAlignment(this.buttonSave,Alignment.MIDDLE_RIGHT);
        horizontalLayout.setWidth(100,Unit.PERCENTAGE);

        this.rootLayout.addComponent(horizontalLayout);
        this.rootLayout.setExpandRatio(wrapped,15);
        this.rootLayout.setExpandRatio(horizontalLayout,1);

        super.setContent(this.rootLayout);
    }

    @Override
    public void setHandler(NarrativePresenter narrativePresenter) {

    }
}
