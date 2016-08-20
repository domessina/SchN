package be.beme.schn.vaadin.narrative.presenter;

import be.beme.schn.narrative.component.Diagram;
import be.beme.schn.narrative.component.NarrativeComponent;
import be.beme.schn.persistence.dao.DiagramDao;
import be.beme.schn.persistence.dao.NarrativeComponentDao;
import be.beme.schn.persistence.dao.UserDao;
import be.beme.schn.vaadin.narrative.view.DiagramEditionView;
import be.beme.schn.vaadin.narrative.view.NarrativeView;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Dotista on 20-08-16.
 */
@Component
@UIScope
public class DiagramPresenter implements WrapperPanelPresenter {

    @Autowired
    private DiagramDao diagramdao;

    @Autowired
    private UserDao userDao;

    private DiagramEditionView view;

    @Override
    public Diagram save() {
        Diagram d = view.getDiagram();
        d.setId(diagramdao.create(view.getDiagram()));
        userDao.increaseNumberOfDiagrams(d.getUser_id());
        return d;
    }

    public void update() {
        diagramdao.update(view.getDiagram());
    }

    @Override
    public boolean erase() {
        Diagram d = view.getDiagram();
        diagramdao.delete(d.getId());
        userDao.reduceNumberOfDiagrams(d.getUser_id());
        userDao.setActualDiagram(d.getUser_id(), -1);
        return true;
    }

    @Override
    public void setView(NarrativeView narrativeView) {
        this.view = (DiagramEditionView) narrativeView;
    }

    @Override
    public DiagramDao getDaoService() {
        return diagramdao;
    }
}
