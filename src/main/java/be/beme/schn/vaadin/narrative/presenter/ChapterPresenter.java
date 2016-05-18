package be.beme.schn.vaadin.narrative.presenter;

import be.beme.schn.narrative.component.Chapter;
import be.beme.schn.persistence.dao.ChapterDao;
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
public class ChapterPresenter implements WrapperPanelPresenter {

    private ChapterView view;
    private Chapter chapter;

    @Autowired
    ChapterDao chapterService;

    @Override
    public Chapter save() {
        try
        {
            //j'ai remarqué par contre , que c'était une bonne idée de prendre de la vue l'objet chapter. Car j'ai changé une méthode dan le dao et je n'ai eu à changer que le preetner , pa la vue :p
             this.chapter=this.view.getChapter();
            int id;
            if(chapter.getId()==0)
            {
                id=this.chapterService.create(this.chapter);
                this.chapter.setId(id);
            }
            else{
                this.chapterService.update(this.chapter);
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
            e.printStackTrace();
            return false;
        }


        return true;
    }


    @Override
    public void setView(NarrativeView narrativeView) {
        this.view=(ChapterView)narrativeView;
    }

    @Override
    public ChapterDao getDaoService() {
        return this.chapterService;
    }

    public void changePosition(int offset, int id)
    {
        int crrntPos=chapterService.getNComponent(id).getPosition();
        crrntPos+=offset;
        chapterService.setPosition(crrntPos,id);
    }
}
