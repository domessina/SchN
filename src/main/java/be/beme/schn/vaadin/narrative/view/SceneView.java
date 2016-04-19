package be.beme.schn.vaadin.narrative.view;

import be.beme.schn.Constants;
import be.beme.schn.narrative.component.Scene;
import be.beme.schn.vaadin.CrudListener;
import be.beme.schn.vaadin.CrudNotifier;
import be.beme.schn.vaadin.ImageUploadPanel;
import be.beme.schn.vaadin.narrative.NWrapped;
import be.beme.schn.vaadin.narrative.NWrapper;
import be.beme.schn.vaadin.narrative.presenter.NarrativePresenter;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;

import java.util.ArrayList;

/**
 * Created by Dotista on 18-04-16.
 */
public class SceneView extends CustomComponent implements NarrativeView, NWrapped, CrudNotifier<Scene> {

    private Scene scene;
    private ArrayList<CrudListener> listeners;
    private ImageUploadPanel imageUploadPanel;

    public SceneView(Scene scene)
    {
        this.scene=scene;
        listeners= new ArrayList<>();
        setSizeFull();
        setCompositionRoot(buildContent());
    }


    private Component buildContent()
    {
              HorizontalSplitPanel horizontalSplitPanel= new HorizontalSplitPanel(buildVLLeft(),buildVRight());
        horizontalSplitPanel.setSizeFull();




        return horizontalSplitPanel;
    }

    private VerticalLayout buildVLLeft()
    {
        VerticalLayout verticalLayout= new VerticalLayout();
        imageUploadPanel= new ImageUploadPanel(Constants.BASE_DIR+"Users\\1"+"\\Diagrams\\"+ VaadinSession.getCurrent().getAttribute("diagramId")+"\\Scenes\\" ,this.scene.getPicture());
        verticalLayout.addComponent(imageUploadPanel);
        verticalLayout.setSizeFull();
        return verticalLayout;
    }

    private VerticalLayout buildVRight()
    {
        return new VerticalLayout();
    }


    @Override
    public void notifyCreated(Scene target) {

    }

    @Override
    public void notifyUpdated(Scene target) {

    }

    @Override
    public void notifyDeleted(Scene target) {

    }

    @Override
    public void addCrudListener(CrudListener listener) {

    }

    @Override
    public void setHandler(NarrativePresenter narrativePresenter) {

    }

    @Override
    public NWrapper getWrapper() {
        return null;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {

    }
}
