package be.beme.schn.vaadin.narrative.view;

import be.beme.schn.Constants;
import be.beme.schn.narrative.component.*;
import be.beme.schn.narrative.component.Link;
import be.beme.schn.vaadin.narrative.presenter.CharacterScenePresenter;
import be.beme.schn.vaadin.narrative.presenter.NarrativePresenter;
import be.beme.schn.vaadin.narrative.view.NarrativeView;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.*;

import java.io.File;

/**
 * Created by Dotista on 19-04-16.
 */
public class CharacterScene extends CustomComponent implements NarrativeView {                      //TODO characterSceneView?

    private HorizontalLayout rootLayout;
    private CharacterScenePresenter presenter;

    public CharacterScene() {
        setSizeFull();
        setCompositionRoot(buildContent());
    }

    private Layout buildContent() {
        rootLayout = new HorizontalLayout();
        rootLayout.setSizeFull();
        rootLayout.addComponent(buildLeft());
        rootLayout.addComponent(new LinkView(new Link()));
        rootLayout.setSpacing(true);

        return rootLayout;
    }


    private Component buildLeft(){

        Image image = new Image();
        File file = new File(Constants.BASE_DIR+"Users\\1"+"\\Diagrams\\3\\Characters\\Cropped\\f494ac51-d1d6-4bbe-8d4b-66ea12c40f14.jpg");
        Resource resource= new FileResource(file);
        image.setSource(resource);
        image.setStyleName("circular");
//        image.setWidth(100,Unit.PERCENTAGE);
        Panel p= new Panel(image);
        p.setHeight(200,Unit.PIXELS);
        p.setWidth(200,Unit.PIXELS);
//        p.setStyleName("circular");
        return image;
    }


    @Override
    public void setHandler(NarrativePresenter narrativePresenter) {
        this.presenter=(CharacterScenePresenter) narrativePresenter;
    }
}
