package be.beme.schn.vaadin.narrative.view;

import be.beme.schn.narrative.component.*;
import be.beme.schn.narrative.component.Character;
import be.beme.schn.vaadin.CrudListener;
import be.beme.schn.vaadin.CrudNotifier;
import be.beme.schn.vaadin.narrative.NWrapperPanel;
import be.beme.schn.vaadin.narrative.presenter.NarrativePresenter;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Runo;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;

/**
 * Created by Dotista on 18-04-16.
 */
public final class SceneViewExtended extends CustomComponent implements NarrativeView, Button.ClickListener{

    private Scene scene;
    private ArrayList<CrudListener> listeners;
    private SceneView scVLeft;

    private VerticalLayout charLayout;

    public SceneViewExtended(Scene scene)
    {
        this.scene=scene;
        listeners= new ArrayList<>();
        setSizeFull();
        setCompositionRoot(buildContent());
    }


    private Component buildContent()
    {
              HorizontalSplitPanel horizontalSplitPanel= new HorizontalSplitPanel(buildCmpntLeft(),buildCmpntRight());
        horizontalSplitPanel.setMaxSplitPosition(45,Unit.PERCENTAGE);
        horizontalSplitPanel.setMinSplitPosition(25,Unit.PERCENTAGE);
        horizontalSplitPanel.setSizeFull();




        return horizontalSplitPanel;
    }

    private Component buildCmpntLeft()
    {
        scVLeft =new SceneView(this.scene);
        NWrapperPanel wrapper= new NWrapperPanel(scVLeft);
        wrapper.setSizeFull();

        scVLeft.wrap(wrapper);
        return wrapper;

    }




    private Component buildCmpntRight()
    {
        charLayout = new VerticalLayout();
        charLayout.setMargin(true);
        charLayout.setSpacing(true);



        VerticalLayout ver=new VerticalLayout();
        Button btn= new Button("test");
        btn.addClickListener(this);
        ver.addComponent(btn);
        ver.addComponent(charLayout);
        ver.setComponentAlignment(btn,Alignment.TOP_LEFT);

        Panel rootRight= new Panel();
        rootRight.setSizeFull();
        rootRight.setContent(ver);
        rootRight.setStyleName("background-grey");

        return rootRight;
    }

    public void addCharacterSceneView(CharacterSceneView charScV)
    {
        charLayout.addComponent(charScV);
    }

    private Layout buildCharList()
    {
        return null;
    }




    @Override
    public void setHandler(NarrativePresenter narrativePresenter) {
            scVLeft.setHandler(narrativePresenter);
    }


    public void addCrudListener(CrudListener listener) {
        scVLeft.addCrudListener(listener);
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        String caption=event.getButton().getCaption();
        if(caption.equals("Add characterScene"))
        {
            CharacterSceneView chScV = new CharacterSceneView(new Character(),scene.getId());
//            chScV.setHandler(characterScenePresenter);
//            chScV.loadTraits();
            addCharacterSceneView(chScV);
        }
    }
}

