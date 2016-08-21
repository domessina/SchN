package be.beme.schn.vaadin.narrative.view;

import be.beme.schn.Constants;
import be.beme.schn.narrative.component.Character;
import be.beme.schn.narrative.component.Scene;
import be.beme.schn.vaadin.crud.CrudListener;
import be.beme.schn.vaadin.narrative.NWrapperPanel;
import be.beme.schn.vaadin.narrative.presenter.CharacterScenePresenter;
import be.beme.schn.vaadin.narrative.presenter.NarrativePresenter;
import be.beme.schn.vaadin.narrative.presenter.ScenePresenter;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Dotista on 18-04-16.
 */
public final class SceneViewExtended extends CustomComponent implements NarrativeView, PopupView.PopupVisibilityListener, CrudListener<Scene>{

    private Scene scene;
    private ArrayList<CrudListener> listeners;
    private SceneView scVLeft;
    private ScenePresenter presenter;
    private VerticalLayout charLayout;
    private CharacterScenePresenter chScenePresenter;
    private PopupView popUp;
    private List<Character> characterList;
    private List<Character> characterListToDelete;
    private TwinColSelect select;
    private boolean firstTime;
    private HashSet<Character> preselected;


    public SceneViewExtended(Scene scene, List<Character> characterList)
    {
        this.scene=scene;
        this.characterList=characterList;
        this.firstTime=true;
        listeners= new ArrayList<>();
        preselected=new HashSet<>();
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
        scVLeft.addCrudListener(this);
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
        ver.addComponent(builPopUpStuff());
        ver.addComponent(charLayout);
        ver.setComponentAlignment(popUp,Alignment.TOP_LEFT);

        Panel rootRight= new Panel();
        rootRight.setSizeFull();
        rootRight.setContent(ver);
        rootRight.setStyleName("background-grey");

        return rootRight;
    }

    private PopupView builPopUpStuff()
    {
        select = new TwinColSelect();
        select.setLeftColumnCaption("Characters");
        select.setRows(10);
        select.setMultiSelect(true);                                                                              //in this case getValue returns a Set
        popUp= new PopupView("Relationship", select);
        popUp.setHideOnMouseOut(false);
        popUp.addPopupVisibilityListener(this);
        return popUp;
    }

    public void preSelectCharacters(List<CharacterSceneView> chSvL)
    {
//        preselected=new HashSet<>();
        HashSet<Character> characterList=new HashSet<>(this.characterList);

        for( Character c: characterList)
        {
            select.addItem(c);

            for(CharacterSceneView chSv:chSvL)
            {
                charLayout.addComponent(chSv);
                if(c.getId()==chSv.getCharacter().getId())
                {
                    preselected.add(c);
                }
            }

        }
        select.setValue(preselected);                   //TODO faut dire que avec vaadin c'est bien mais des fois pour le widget ils sont pauvres en méthode spubliques et tu dois te caser le cul our deviner plein de truc sur leur Etat et les variabes qu'ils contiennet...des event pauvres en informations...

    }

    public void removeCharacterSceneViews()
    {
        charLayout.removeAllComponents();
    }

    private Layout buildCharList()
    {
        return null;
    }







    public void addCrudListener(CrudListener listener) {
        scVLeft.addCrudListener(listener);
    }



    @Override
    public void popupVisibilityChange(PopupView.PopupVisibilityEvent event) {
        if(!event.isPopupVisible())
        {
            removeCharacterSceneViews();
            characterListToDelete=new ArrayList<>(characterList);
            buildChScViews(new ArrayList<>((Set)select.getValue()));
            deleteCharactersFromScene();

        }

    }

    public void buildChScViews(List<Character> cList)
    {
        ArrayList<CharacterSceneView> chSvL=new ArrayList<>();
        for(Character c:cList)
        {
            CharacterSceneView chScV = new CharacterSceneView(c,scene.getId());
            chScV.setHandler(chScenePresenter);
            chScV.loadTraits();
            charLayout.addComponent(chScV);
            chSvL.add(chScV);
        }
        if(firstTime)
        {
            preSelectCharacters(chSvL);
            firstTime=false;
        }
    }

    private void deleteCharactersFromScene()
    {

    }

    public void setChScenePresenter(NarrativePresenter chScenePresenter)
    {
        this.chScenePresenter=(CharacterScenePresenter)chScenePresenter;
    }


    @Override
    public void setHandler(NarrativePresenter narrativePresenter) {
        scVLeft.setHandler(narrativePresenter);
        this.presenter=(ScenePresenter)narrativePresenter;
    }

    @Override
    public void created(Scene o) {

    }

    @Override
    public void updated(Scene o) {
        for(Component c:charLayout)
        {
            CharacterSceneView cs= (CharacterSceneView)c;
            chScenePresenter.addCharacterInScene(((CharacterSceneView) c).getCharacter().getId(),scene.getId());                                              //TODO ici faire le notification .show? error rapport envoyé...
        }

        for(Object ob:(Set)select.getValue())
        {
            Character c=(Character)ob;
            if(preselected.contains(c))
            {
                preselected.remove(c);
            }
        }
        for(Character c:preselected)
        {
            if(!chScenePresenter.removeCharacterFromScene(c.getId(),scene.getId()))
            {
                Notification.show(Constants.MSG_SYS_ERR,Constants.MSG_REPORT_SENT, Notification.Type.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void deleted(Scene o) {

    }
}

