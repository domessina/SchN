package be.beme.schn.vaadin.narrative.presenter;

import be.beme.schn.Constants;
import be.beme.schn.narrative.component.NarrativeComponent;
import be.beme.schn.narrative.component.Scene;
import be.beme.schn.persistence.dao.SceneDao;
import be.beme.schn.persistence.daoimpl.SceneDaoImpl;
import be.beme.schn.vaadin.narrative.view.NarrativeView;
import be.beme.schn.vaadin.narrative.view.SceneView;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Dotista on 19-04-16.
 */
@Component
@UIScope
public class ScenePresenter implements WrapperPanelPresenter {

    @Autowired
    private SceneDaoImpl dao;

    private Scene scene;

    private  SceneView view;

    @Override
    public Scene save() {
        try
        {
            this.scene=this.view.getScene();
            int id;
            if(scene.getId()==0)
            {
                id=this.dao.create(
                        scene.getChapterId(),
                        scene.getTag(),
                        scene.getPlace(),
                        scene.getPicture(),
                        scene.getNote());
                this.scene.setId(id);
            }
            else{
                this.dao.update(new Object[]{
                        scene.getTag(),
                        scene.getPlace(),
                        scene.getPicture(),
                        scene.getNote(),
                        scene.getId()});
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return this.scene;
    }

    @Override
    public boolean erase() {

        try
        {

            this.scene=this.view.getScene();
            dao.delete(scene.getId());

            dao.deleteImage(scene.getPicture(), 1, Integer.valueOf(VaadinSession.getCurrent().getAttribute("diagramId").toString()));

        }
        catch (Exception e)                 //doens't matter if it's SQLException or other.
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void setView(NarrativeView narrativeView) {
            this.view=(SceneView)narrativeView;
    }

    @Override
    public SceneDaoImpl getDaoService() {
        return this.dao;
    }


}
