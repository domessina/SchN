package be.beme.schn.vaadin.narrative.view;

import be.beme.schn.narrative.component.Chapter;
import be.beme.schn.vaadin.ImagePanel;
import be.beme.schn.vaadin.narrative.presenter.ChapterPresenter;
import be.beme.schn.vaadin.narrative.presenter.NarrativePresenter;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.*;

import java.io.File;

/**
 * Created by Dotista on 08-04-16.
 */
public class ChapterWindow extends AbstractWindowView implements NarrativeView {

    private ChapterPresenter presenter;
    private Chapter chapter;
    private Panel imagePanel;

    public ChapterWindow(Chapter chapter)
    {
        super("Chapter");
        this.chapter=chapter;
        setHeight(99, Unit.PERCENTAGE);
        setWidth(30, Unit.EM);
        setWrappedContent(buildContent());
    }

    private Layout buildContent()
    {
        VerticalLayout verticalLayout= new VerticalLayout();
     //   verticalLayout.addComponent(buildImage());



        return new VerticalLayout();
    }


    private Panel buildImage()
    {
        ImagePanel ip = new ImagePanel();


       return null;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {

        if(event.getButton().equals(this.buttonSave))
        {
            presenter.save();
        }
        else if(event.getButton().equals(this.buttonErase))
        {
            presenter.erase();
        }
    }

    @Override
    public void setHandler(NarrativePresenter narrativePresenter) {
        this.presenter=(ChapterPresenter)narrativePresenter;
    }

    public Chapter getChapter() {
        return chapter;
    }
}
