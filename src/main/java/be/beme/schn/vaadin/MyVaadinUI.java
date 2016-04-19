package be.beme.schn.vaadin;

import be.beme.schn.narrative.component.Chapter;
import be.beme.schn.narrative.component.Character;
import be.beme.schn.narrative.component.Scene;
import be.beme.schn.persistence.daoimpl.DiagramDaoImpl;
import be.beme.schn.vaadin.narrative.ChapterPHLayout;
import be.beme.schn.vaadin.narrative.NWrapperPanel;
import be.beme.schn.vaadin.narrative.presenter.ChapterPresenter;
import be.beme.schn.vaadin.narrative.presenter.CharacterWindowPresenter;
import be.beme.schn.vaadin.narrative.presenter.TraitCrudPresenter;
import be.beme.schn.vaadin.narrative.view.ChapterView;
import be.beme.schn.vaadin.narrative.view.CharacterView;
import be.beme.schn.vaadin.narrative.view.SceneView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
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
@SpringUI                                                                                                               //TODO rajouter une grande scrollbar verticale pour quand on rapetissie la page
public class MyVaadinUI extends UI implements TabSheet.SelectedTabChangeListener{

    @Autowired
    DiagramDaoImpl diagramService;

    @Autowired
    CharacterWindowPresenter characterPresenter;


    @Autowired
    private TraitCrudPresenter traitPresenter;

    @Autowired
    private ChapterPresenter chapterPresenter;

    public short phaseSelected;
    public short diagramId;

    private TabSheet tabSheet;
    private ArrayList<Scene> scenes;
    private ChapterUI chapterUI;
    private CharacterUI characterUI;


    @Override
    protected void init(VaadinRequest vaadinRequest) {


        VerticalLayout verticalLayout = new VerticalLayout();

        chapterUI= new ChapterUI();
        characterUI= new CharacterUI();
        tabSheet= new TabSheet();

        scenes= new ArrayList<Scene>();

        diagramId=3;
        VaadinSession.getCurrent().setAttribute("diagramId",3);
        System.out.println(VaadinSession.getCurrent().getCsrfToken());


        Scene scene;

        for(int i=0;i<15;i++)
        {
            scene= new Scene();
            scene.setPicture("12.jpg");
            scene.setId(i);
            scenes.add(scene);
        }


        chapterUI.init();
        tabSheet.setSizeFull();
        tabSheet.setImmediate(true);
        tabSheet.addSelectedTabChangeListener(this);

        chapterUI.fillTabSheet();
        tabSheet.setSelectedTab(0);


        verticalLayout.setSizeFull();
        HorizontalLayout hl= new HorizontalLayout(new Button("new Chapter",clickEvent -> chapterUI.newChapter()));
        hl.setSizeFull();


        verticalLayout.addComponent(hl);

        verticalLayout.addComponent(tabSheet);

        verticalLayout.setMargin(true);
        verticalLayout.setExpandRatio(tabSheet,9);
        verticalLayout.setExpandRatio(hl,1);

//        tabSheet.setTabIndex for Tab Key

        TextField tf= new TextField("Character Id");
        hl.addComponent(tf);
        hl.addComponent(new Button("show character",event1 -> characterUI.showCharacter(Integer.valueOf(tf.getValue()))));

        hl.addComponent(new Button("new Character", event -> characterUI.newCharacter()));


        setContent(verticalLayout);
    }



    @Override
    public void selectedTabChange(TabSheet.SelectedTabChangeEvent selectedTabChangeEvent) {


        try{
            phaseSelected=Short.valueOf(selectedTabChangeEvent.getTabSheet().getSelectedTab().getId());             //auycun id de compoentn dans un tab ne peuve têtre qu'un chiffre , autrement mauvais assignation dephase
            chapterUI.loadChapters();
        }
        catch(NumberFormatException e)
        {
            System.out.println("This tab is not a phase");
        }



    }




    //------------------------SCENE UI MANAGING---------------------------

    public void openScene(Scene scene)
    {
       tabSheet.addTab(new SceneView(scene),"New Scene");
       tabSheet.setSelectedTab(tabSheet.getComponentCount()-1);
    }


    //----------------------CHARACTER UI MANAGING--------------------------

    private final class CharacterUI                 //TODO tu n'as même pas de variables de classe pour ces classes internes, lesr etirer non?
    {
        private void newCharacter()
        {
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
            MyVaadinUI.this.addWindow(window);
        }

        private void showCharacter(int id)
        {
            Character character = characterPresenter.getDaoService().getCharacterById(id);
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
            MyVaadinUI.this.addWindow(window);
        }

    }




    //--------------------------CHAPTER UI MANAGING--------------------------



    private final class ChapterUI implements CrudListener<Chapter>
    {
        private List<Chapter> chapterList;
        private Panel[] panelArray;
        private ChapterPHLayout[] chapterLArray;

        public void init()
        {
            panelArray = new Panel[6];
            chapterLArray= new ChapterPHLayout[6];



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
                panelArray[i].setId(String.valueOf(i));             //Every Panel has the number of it's phase
            }

        }


        private void newChapter()
        {
            Chapter chapter =new Chapter();
            chapter.setPhase(phaseSelected);
            chapter.setDiagramId(diagramId);
            chapter.setPosition((short) VaadinSession.getCurrent().getAttribute("chapterCountCrrntPhase"));

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

            MyVaadinUI.this.addWindow(window);
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

        private void fillTabSheet()
        {
            tabSheet.addTab(panelArray[0],"Exposition");
            tabSheet.addTab(panelArray[1],"Conflict");
            tabSheet.addTab(panelArray[2],"Rising Action");
            tabSheet.addTab(panelArray[3],"Climax");
            tabSheet.addTab(panelArray[4],"Falling Action");
            tabSheet.addTab(panelArray[5],"Resolution");
        }

        private void addChapterView(Chapter chapter){

//        chapter.setPosition((short)(chapterLArray[phaseSelected].getComponentCount()+1));

            ChapterView chapterW = new ChapterView(chapter);
            chapterW.setHandler(chapterPresenter);
            chapterW.addCrudListener(this);
            NWrapperPanel wrapper=new NWrapperPanel(chapterW);
            wrapper.setSizeFull();
            wrapper.setId(String.valueOf(chapter.getId()));
            chapterW.wrap(wrapper);
            chapterLArray[phaseSelected].addComponent(wrapper);
            VaadinSession.getCurrent().setAttribute("chapterCountCrrntPhase",chapterLArray[phaseSelected].getComponentCount());
            wrapper.setCaption("Chapter "+ chapterLArray[phaseSelected].getComponentCount());

        }




        @Override
        public void created(Chapter o) {
            chapterUI.addChapterView(o);
            for(Window w: MyVaadinUI.this.getWindows())
            {
                if(w.getCaption().equals("New Chapter"))
                {
                    w.close();
                }
            }

        }

        @Override
        public void updated(Chapter o) {

            NWrapperPanel wrapperReplaced=(NWrapperPanel)chapterLArray[phaseSelected].getComponent(o.getPosition());
            Component wrapperChanged=chapterLArray[phaseSelected].getComponentByChapter(o.getId());
            short oldPlace=(short)chapterLArray[phaseSelected].getComponentIndex(wrapperChanged);
            ((ChapterView)wrapperReplaced.getWrappedComponent()).setChapterPlace(oldPlace);
//        chapterLArray[phaseSelected].replaceComponent(chapterLArray[phaseSelected].getComponent(o.getPosition()),chapterLArray[phaseSelected].getComponentByChapter(o.getId()));            //because WrapperPanels have as id the id of the their chapter
            chapterLArray[phaseSelected].replaceComponent(wrapperReplaced,wrapperChanged);            //because WrapperPanels have as id the id of the their chapter


        }

        @Override
        public void deleted(Chapter o) {

            chapterLArray[(int)phaseSelected].removeChapter(o.getId());

        }

    }

}


