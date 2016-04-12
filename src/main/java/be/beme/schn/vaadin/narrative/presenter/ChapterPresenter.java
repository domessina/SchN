package be.beme.schn.vaadin.narrative.presenter;

import be.beme.schn.narrative.component.Chapter;
import be.beme.schn.narrative.component.NarrativeComponent;
import be.beme.schn.persistence.daoimpl.ChapterDaoImpl;
import be.beme.schn.vaadin.narrative.view.ChapterView;
import be.beme.schn.vaadin.narrative.view.NarrativeView;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Dotista on 08-04-16.
 */

@Component
@UIScope
public class ChapterPresenter implements WindowPresenter {

    private ChapterView view;
    private Chapter chapter;

    @Autowired
    ChapterDaoImpl chapterService;

    @Override
    public NarrativeComponent save() {
        try
        {
             this.chapter=this.view.getChapter();
            int id;
            if(chapter.getId()==0)
            {
                id=this.chapterService.create(
                        chapter.getDiagramId(),
                        chapter.getPhase(),
                        chapter.getTitle(),
                        chapter.getPreviousChapterId(),
                        chapter.getNote());
                this.chapter.setId(id);
            }
            else{
/*
                this.chapterService.update(new Object[]{
                        chapter.getName(),
                        chapter.getType(),
                        chapter.getNote(),
                        chapter.getPicture(),
                        chapter.getId()});*/
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return this.chapter;

    }

    @Override
    public boolean erase() {

        this.chapter=view.getChapter();
        try {
            chapterService.delete(chapter.getId());
        }
        catch(Exception e)
        {

        }


        return false;
    }

    @Override
    public void setView(NarrativeView narrativeView) {
        this.view=(ChapterView)narrativeView;
    }
}
