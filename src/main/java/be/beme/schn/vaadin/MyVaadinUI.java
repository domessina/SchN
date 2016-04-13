package be.beme.schn.vaadin;

import be.beme.schn.narrative.component.Chapter;
import be.beme.schn.narrative.component.Scene;
import be.beme.schn.persistence.daoimpl.DiagramDaoImpl;
import be.beme.schn.vaadin.narrative.ChapterPHLayout;
import be.beme.schn.vaadin.narrative.NWrapperPanel;
import be.beme.schn.vaadin.narrative.presenter.ChapterPresenter;
import be.beme.schn.vaadin.narrative.presenter.CharacterWindowPresenter;
import be.beme.schn.vaadin.narrative.presenter.TraitCrudPresenter;
import be.beme.schn.vaadin.narrative.view.ChapterView;
import be.beme.schn.vaadin.narrative.view.CharacterView;
import be.beme.schn.narrative.component.Character;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dorito on 17-03-16.
 */
@Theme("valo")
@Title("Narrative Diagram")
//@PreserveOnRefresh  Attention est ce que les trucs qui sont reliés à l'url comme URI, query parameters seront gardés?
//Push renseigne toi y  https://blog.oio.de/2014/01/13/overview-vaadin-7-annotations/
@SpringUI
public class MyVaadinUI extends UI implements TabSheet.SelectedTabChangeListener, CrudListener<Chapter>{

    @Autowired
    DiagramDaoImpl diagramService;

    @Autowired
    CharacterWindowPresenter characterPresenter;


    @Autowired
    private TraitCrudPresenter traitPresenter;

    @Autowired
    private ChapterPresenter chapterPresenter;

    List<Chapter> chapterList;
    public static int diagramId=3;
    public static short phaseSelected;
    Panel[] panelArray;
    ChapterPHLayout[] chapterLArray;
    private TabSheet tabSheet;
    ArrayList<Scene> scenes;

    @Override
    protected void init(VaadinRequest vaadinRequest) {


        VerticalLayout verticalLayout = new VerticalLayout();
        panelArray = new Panel[6];
        chapterLArray= new ChapterPHLayout[6];

        scenes= new ArrayList<Scene>();
        Scene scene;

        for(int i=0;i<15;i++)
        {
            scene= new Scene();
            scene.setPicture("12.jpg");
            scene.setId(i);
            scenes.add(scene);
        }







        for(int i=0;i<6;i++)
        {
            chapterLArray[i]=new ChapterPHLayout();
            chapterLArray[i].setImmediate(true);
            chapterLArray[i].setHeight(100,Unit.PERCENTAGE);
//        chapterLayout.setMargin(true);
            chapterLArray[i].setSpacing(true);
            chapterLArray[i].setHeight(100,Unit.PERCENTAGE);
        }


        for(int i=0;i<6;i++)
        {
            panelArray[i]=new Panel();
            panelArray[i].setContent(chapterLArray[i]);
            panelArray[i].setHeight(100,Unit.PERCENTAGE);
            panelArray[i].setId(String.valueOf(i));
        }

        this.tabSheet=buildTabSheet();




        verticalLayout.setSizeFull();
        HorizontalLayout hl= new HorizontalLayout(new Button("new Chapter",clickEvent -> {

            Chapter chapter =new Chapter();
            chapter.setPhase(phaseSelected);
            chapter.setDiagramId(diagramId);
            chapter.setPosition((short)chapterLArray[phaseSelected].getComponentCount());

            ChapterView chapterView= new ChapterView(chapter);
            chapterView.setHandler(chapterPresenter);
            chapterView.addCrudListener(this);
//            chapterPresenter.setView(chapterView);
            NWrapperPanel wrapper=new NWrapperPanel(chapterView);
            chapterView.wrap(wrapper);

            Window window=new Window("New Chapter",wrapper);
            window.setModal(true);
            window.setDraggable(false);
            window.setResizable(false);

            this.addWindow(window);
        }));
        hl.setSizeFull();



        verticalLayout.addComponent(hl);

        verticalLayout.addComponent(tabSheet);

        verticalLayout.setMargin(true);
        verticalLayout.setExpandRatio(tabSheet,9);
        verticalLayout.setExpandRatio(hl,1);

//        tabSheet.setTabIndex for Tab Key

        TextField tf= new TextField("Character Id");
        hl.addComponent(tf);
        hl.addComponent(new Button("show character",event1 -> {
            Character character = characterPresenter.getDaoService().getCharacterById(Integer.valueOf(tf.getValue()));
            CharacterView characterView = new CharacterView(character, traitPresenter);
            characterView.setHandler(characterPresenter);
            characterPresenter.setView(characterView);
            NWrapperPanel wrapper= new NWrapperPanel(characterView);
            wrapper.setSizeFull();
            characterView.wrap(wrapper);

            Window window = new Window("New Character",wrapper);
            window.setModal(true);
            window.setDraggable(false);
            window.setResizable(false);
            window.setHeight(99,Unit.PERCENTAGE);

            window.setWidth( 31,Unit.EM);
            this.addWindow(window);
        }));

        hl.addComponent(new Button("new Character", event -> {
            Character character= new Character();
            character.setDiagram_id(diagramId);

            CharacterView characterView = new CharacterView(character, traitPresenter);
            characterView.setHandler(characterPresenter);
            characterPresenter.setView(characterView);
            NWrapperPanel wrapper= new NWrapperPanel(characterView);
            wrapper.setSizeFull();
            characterView.wrap(wrapper);

            Window window = new Window("New Character",wrapper);
            window.setModal(true);
            window.setDraggable(false);
            window.setResizable(false);
            window.setHeight(99,Unit.PERCENTAGE);
            window.addCloseListener(characterView);

            window.setWidth( 31,Unit.EM);
            this.addWindow(window);
        }));


        setContent(verticalLayout);
    }


    private TabSheet buildTabSheet()
    {
        TabSheet tabSheet= new TabSheet();
        tabSheet.setSizeFull();
        tabSheet.setImmediate(true);
        tabSheet.addSelectedTabChangeListener(this);
        tabSheet.addTab(panelArray[0],"Exposition");
        tabSheet.addTab(panelArray[1],"Conflict");
        tabSheet.addTab(panelArray[2],"Rising Action");
        tabSheet.addTab(panelArray[3],"Climax");
        tabSheet.addTab(panelArray[4],"Falling Action");
        tabSheet.addTab(panelArray[5],"Resolution");
        tabSheet.setSelectedTab(0);
        return tabSheet;
    }

    private void loadChapters()
    {
        chapterList= chapterPresenter.getDaoService().getAllChaptersByPhase(phaseSelected,diagramId);
        chapterLArray[phaseSelected].removeAllComponents();

        if(!chapterList.isEmpty())
        {
            for(Chapter chapter:chapterList)
            {

                chapter.setScenes(scenes);
                addChapterView(chapter);
            }
        }
        else{
            System.out.println("No chapter fot this phase");
        }
    }

    private void addChapterView(Chapter chapter){
        ChapterView chapterW = new ChapterView(chapter);
        chapterW.setHandler(chapterPresenter);
        chapterW.addCrudListener(this);
        NWrapperPanel wrapper=new NWrapperPanel(chapterW);
        wrapper.setSizeFull();
        wrapper.setId(String.valueOf(chapter.getId()));
        chapterW.wrap(wrapper);
        chapterLArray[phaseSelected].addComponent(wrapper);
        wrapper.setCaption("Chapter "+chapterLArray[phaseSelected].getComponentCount());

    }

    @Override
    public void selectedTabChange(TabSheet.SelectedTabChangeEvent selectedTabChangeEvent) {
        phaseSelected=Short.valueOf(selectedTabChangeEvent.getTabSheet().getSelectedTab().getId());
        loadChapters();
    }

    @Override
    public void created(Chapter o) {
             addChapterView(o);
            for(Window w: this.getWindows())
            {
                if(w.getCaption().equals("New Chapter"))
                {
                    w.close();
                }
            }

    }

    @Override
    public void updated(Chapter o) {

        chapterLArray[phaseSelected].replaceComponent(chapterLArray[phaseSelected].getComponent(o.getPosition()),chapterLArray[phaseSelected].getComponent(o.getPosition()+1));
    }

    @Override
    public void deleted(Chapter o) {

     chapterLArray[(int)phaseSelected].removeChapter(o);

    }
}