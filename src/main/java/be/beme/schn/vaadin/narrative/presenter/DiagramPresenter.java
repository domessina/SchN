package be.beme.schn.vaadin.narrative.presenter;

import be.beme.schn.FileUtil;
import be.beme.schn.narrative.component.Diagram;
import be.beme.schn.persistence.dao.DiagramDao;
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
        FileUtil.createDiagramTree(d.getUserId(),d.getId());
        userDao.increaseNumberOfDiagrams(d.getUserId());
        diagramdao.createDiagramToSynch(d.getUserId(),d.getId());
        diagramdao.setActionDiagramToSynch("CREATE",d.getId());
        diagramdao.setNeedSynch(true,d.getId());
        return d;
    }

    public void update() {
        diagramdao.update(view.getDiagram());
        diagramdao.setNeedSynch(true,view.getDiagram().getId());
    }

    @Override
    public boolean erase() {
        Diagram d = view.getDiagram();
        diagramdao.setDiagramEnabled(d.getId(),false);
        //TODO AVANT D UTILISER PERSONNELLEMENT comme tu useras pas de l'app android, uncomment this line
//        FileUtil.deleteDiagramTree(d.getUserId(),d.getId());
        userDao.decreaseNumberOfDiagrams(d.getUserId());
        userDao.setActualDiagram(d.getUserId(), -1);
        diagramdao.setActionDiagramToSynch("DELETE",d.getId());
        diagramdao.setNeedSynch(true,d.getId());
        return true;
    }

    public void setActionDiagramToSynch(String action, int diagramId){
        diagramdao.setActionDiagramToSynch("UPDATE",diagramId);
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
