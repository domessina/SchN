package be.beme.schn.vaadin.narrative.view;

import be.beme.schn.narrative.component.Link;
import be.beme.schn.vaadin.narrative.presenter.LinkPresenter;
import be.beme.schn.vaadin.narrative.presenter.NarrativePresenter;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by Dotista on 19-04-16.
 */
public class LinkView extends HorizontalLayout implements NarrativeView, Button.ClickListener {

    private Link link;
    private Button good;
    private Button bad;
    private LinkPresenter presenter;


                                                    //c'est Relation et pas link qu'il aura fallu l'appeler.
    public LinkView(Link link)
    {
        this.link=link;
        initContent();
        setSizeFull();
        addComponents(new Label(this.link.getName()),good, bad);
    }

    private void initContent()
    {


        good = new Button(FontAwesome.PLUS);
        good.addClickListener(this);
        good.setStyleName(ValoTheme.BUTTON_BORDERLESS,true);
        good.setStyleName(ValoTheme.BUTTON_TINY,true);

        bad = new Button(FontAwesome.MINUS);
        bad.addClickListener(this);
        bad.setStyleName(ValoTheme.BUTTON_BORDERLESS,true);
        bad.setStyleName(ValoTheme.BUTTON_TINY,true);

        applyUIRel();


    }

    public boolean applyUIRel()
    {
        if(link.isRel())
        {
            good.setStyleName("green-shadow",true);
            bad.setStyleName("red-shadow",false);
        }
        else
        {
            bad.setStyleName("red-shadow",true);
            good.setStyleName("green-shadow",false);
        }

        return link.isRel();
    }

    @Override
    public void setHandler(NarrativePresenter narrativePresenter) {
        this.presenter=(LinkPresenter)narrativePresenter;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        link.setRel(!link.isRel());
        applyUIRel();
    }

    public Link getLink()
    {
        return this.link;
    }
}
