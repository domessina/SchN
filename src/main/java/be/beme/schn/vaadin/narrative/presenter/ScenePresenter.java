package be.beme.schn.vaadin.narrative.presenter;

import be.beme.schn.Constants;
import be.beme.schn.narrative.component.Scene;
import be.beme.schn.persistence.dao.SceneDao;
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
    private SceneDao sceneService;

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
                id=this.sceneService.create(scene);
                this.scene.setId(id);
            }
            else{
                this.sceneService.update(scene);
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
            sceneService.delete(scene.getId());

            if(scene.getPicture()!=null)
            {
                VaadinSession session= VaadinSession.getCurrent();
                String target= Constants.BASE_DIR+"Users\\"+session.getAttribute("userId")+"\\Diagrams\\"+session.getAttribute("diagramId").toString()+"\\Scenes\\"+scene.getPicture();
                Files.delete(Paths.get(target));
            }


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
    public SceneDao getDaoService() {
        return this.sceneService;
    }

    public int getPlace(int id)
    {
        return sceneService.getScene(id).getPlace();
    }

    public void switchPosition(int id1,int id2)
    {
        int pos1 =sceneService.getScene(id1).getPlace();
        int pos2 =sceneService.getScene(id2).getPlace();
        sceneService.setPlace(pos2,id1);
        sceneService.setPlace(pos1,id2);
    }

    public void changePosition(int offset, int id)
    {
        int crrntPos=sceneService.getScene(id).getPlace();
        crrntPos+=offset;
        sceneService.setPlace(crrntPos,id);
    }

}
