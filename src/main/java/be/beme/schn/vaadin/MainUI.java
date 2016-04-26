package be.beme.schn.vaadin;

import be.beme.schn.Constants;
import be.beme.schn.narrative.component.Chapter;
import be.beme.schn.narrative.component.Character;
import be.beme.schn.narrative.component.Scene;
import be.beme.schn.persistence.daoimpl.DiagramDaoImpl;
import be.beme.schn.vaadin.dd.DDGridLayout;
import be.beme.schn.vaadin.dd.GridLayoutDropEvent;
import be.beme.schn.vaadin.dd.CustomDragAndDropWrapper;
import be.beme.schn.vaadin.narrative.ChapterPVLayout;
import be.beme.schn.vaadin.narrative.NWrapperPanel;
import be.beme.schn.vaadin.narrative.presenter.*;
import be.beme.schn.vaadin.narrative.view.ChapterView;
import be.beme.schn.vaadin.narrative.view.CharacterView;
import be.beme.schn.vaadin.narrative.view.SceneView;
import be.beme.schn.vaadin.narrative.view.SceneViewExtended;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dorito on 17-03-16.
 */
@Theme("mytheme")
@Title("Narrative Diagram")
//@PreserveOnRefresh  Attention est ce que les trucs qui sont reliés à l'url comme URI, query parameters seront gardés?
//Push renseigne toi y  https://blog.oio.de/2014/01/13/overview-vaadin-7-annotations/
@SpringUI                                                                                                               //TODO rajouter une grande scrollbar verticale pour quand on rapetissie la page
public class MainUI extends UI implements TabSheet.SelectedTabChangeListener{                                       //TODO lock le ui à chaque fois que l'on sauvegarde ou erase , car accès à la Db peut etre lent

    @Autowired
    DiagramDaoImpl diagramService;

    @Autowired
    CharacterPresenter characterPresenter;

    @Autowired
    TraitCrudPresenter traitPresenter;

    @Autowired
    ChapterPresenter chapterPresenter;

    @Autowired
    ScenePresenter scenePresenter;

    @Autowired
    CharacterScenePresenter characterScenePresenter;

    public short phaseSelected;
    public short diagramId;
    private final TabSheet tabSheet;
    private final ChapterUI chapterUI;
    private final CharacterUI characterUI;
    private final SceneUI sceneUI;


    public MainUI()
    {

        chapterUI= new ChapterUI();
        characterUI= new CharacterUI();
        sceneUI= new SceneUI();
        tabSheet= new TabSheet();
    }


    @Override
    protected void init(VaadinRequest vaadinRequest) {



        diagramId=3;

        VaadinSession.getCurrent().setAttribute("diagramId",3);
        VaadinSession.getCurrent().setAttribute("userId",1);
        VaadinSession.getCurrent().setAttribute("characterDirectory",Constants.BASE_DIR+"Users\\"+ VaadinSession.getCurrent().getAttribute("userId")+"\\Diagrams\\"+VaadinSession.getCurrent().getAttribute("diagramId")+"\\Characters\\");
        System.out.println(VaadinSession.getCurrent().getCsrfToken());


        tabSheet.setSizeFull();
        tabSheet.setImmediate(true);
        tabSheet.addSelectedTabChangeListener(this);    //must be set before
        chapterUI.init();
        chapterUI.initTabSheet();

        tabSheet.setSelectedTab(0);


        setContent(buildContent());
    }

    private Component buildContent()
    {
        HorizontalLayout hl= new HorizontalLayout(new Button("new Chapter",clickEvent -> chapterUI.newChapter()));
        hl.setSizeFull();
        TextField tf= new TextField("Character Id");
        hl.addComponent(tf);
        hl.addComponent(new Button("show character",event1 -> characterUI.showCharacter(Integer.valueOf(tf.getValue()))));
        hl.addComponent(new Button("new Character", event -> characterUI.newCharacter()));

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.addComponent(hl);
        verticalLayout.addComponent(tabSheet);
        verticalLayout.setMargin(true);
        verticalLayout.setExpandRatio(tabSheet,9);
        verticalLayout.setExpandRatio(hl,1);
        return verticalLayout;
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

    public void newScene(int chapterId){                    //TODO dommage
        sceneUI.newScene(chapterId);
    }


    //------------------------SCENE UI MANAGING---------------------------

    private final class SceneUI implements  CrudListener<Scene>, Component.Listener
    {
        private DDGridLayout gLayout;

        public void openScene(Scene scene)
        {
            for(int i=6;i<tabSheet.getComponentCount();i++)
            {
                TabSheet.Tab tab= tabSheet.getTab(i);
                if(tab.getCaption().equals(scene.getTag()))
                {
                    tabSheet.setSelectedTab(tab);
                    return;
                }
            }


            SceneViewExtended scvE= new SceneViewExtended(scene,characterPresenter.getDaoService().getAllCharactersByDiagram((int)VaadinSession.getCurrent().getAttribute("diagramId")));

            List<Character> characterSc =characterPresenter.getDaoService().getAllCharactersByScene(scene.getId());
            scvE.setChScenePresenter(characterScenePresenter);
            scvE.buildChScViews(characterSc);


            scvE.setHandler(scenePresenter);
            scvE.addCrudListener(this);

            tabSheet.addTab(scvE,scene.getTag());
            tabSheet.getTab(tabSheet.getComponentCount()-1).setClosable(true);
            tabSheet.setSelectedTab(tabSheet.getComponentCount()-1);
        }

        public void newScene(int chapterId)
        {
            Scene scene = new Scene();
            scene.setChapterId(chapterId);
            scene.setPlace(gLayout.getComponentCount());
            SceneView scV= new SceneView(scene);
            NWrapperPanel wrapper= new NWrapperPanel(scV);
            wrapper.setSizeFull();
            scV.wrap(wrapper);
            scV.addCrudListener(this);
            scV.setHandler(scenePresenter);

            Window window = new Window("New Scene",wrapper);
            window.setModal(true);
            window.setResizable(false);
            window.setHeight(99,Unit.PERCENTAGE);
            window.addCloseListener(scV);

            window.setWidth( 31,Unit.EM);
            MainUI.this.addWindow(window);
        }


        public Panel loadSceneStickers(Chapter chapter)
        {
            System.err.println("loool");
            Panel pstickers = new Panel();
            pstickers.setStyleName("background-grey");
            gLayout= new DDGridLayout();
            gLayout.addDropListener(this);
            gLayout.layout.setSpacing(true);
            gLayout.addStyleName("no-vertical-drag-hints");
            gLayout.addStyleName("no-horizontal-drag-hints");

            try {
                int rows = (int) Math.ceil(chapter.getScenes().size()/3);
                gLayout.layout.removeAllComponents();

                if(rows==0)
                    rows=1;

                gLayout.layout.setRows(rows);
                gLayout.layout.setColumns(3);

                for(Scene s : chapter.getScenes())
                {
                    Panel sticker= new Panel(s.getTag());

                    if(s.getPicture()!=null)
                    {
                        Image image= new Image(null,new FileResource(new File(Constants.BASE_DIR+"Users\\1\\Diagrams\\"+chapter.getDiagramId()+"\\Scenes\\"+s.getPicture())));
                        image.setHeight(100,Unit.PERCENTAGE);
                        VerticalLayout ver= new VerticalLayout(image);
                        ver.setSizeFull();
                        ver.setComponentAlignment(image,Alignment.MIDDLE_CENTER);
                        sticker.setContent(ver);

                    }

                    sticker.addClickListener(event -> {
                        if(event.isDoubleClick())
                        {
                            this.openScene(s);
                        }
                    });
                    sticker.setWidth(100,Unit.PERCENTAGE);
                    sticker.setHeight(18.5F,Unit.EM);
                    sticker.setStyleName("blue-hover",true);
                    sticker.setId(String.valueOf(s.getId()));        //rajotuer Sc devant parce que vaadin nomme déjà les id par défaut avec des nombres. Faut pas que l'id d'une scène soit égal à l'id d'un autre compoenent Vaadin
                    gLayout.addComponent(sticker);

                }

            }
            catch (NullPointerException e)
            {
                System.out.println("This chapter has no scenes");
            }

            gLayout.layout.setWidth(100,Unit.PERCENTAGE);

            pstickers.setSizeFull();
            pstickers.setContent(gLayout);

            return pstickers;
        }

        @Override
        public void created(Scene o) {
            SceneViewExtended scv= new SceneViewExtended(o,new ArrayList<Character>());
            scv.setHandler(scenePresenter);
            scv.addCrudListener(this);
            tabSheet.addTab(scv,o.getTag());
            tabSheet.getTab(tabSheet.getComponentCount()-1).setClosable(true);
            tabSheet.setSelectedTab(tabSheet.getComponentCount()-1);
        }

        @Override
        public void updated(Scene o) {

        }

        @Override
        public void deleted(Scene o) {
            tabSheet.removeTab(tabSheet.getTab(tabSheet.getSelectedTab()));
        }

        @Override
        public void componentEvent(Event event) {
            if(event instanceof GridLayoutDropEvent)
            {
                GridLayoutDropEvent e =(GridLayoutDropEvent) event;

                CustomDragAndDropWrapper wrappedDrag=(CustomDragAndDropWrapper)gLayout.getLayout().getComponent(e.getX(),e.getY());
                Panel p=(Panel)wrappedDrag.getCompositionRoot();
                scenePresenter.getDaoService().setPlace(Integer.valueOf(p.getId()),e.getIndex());

            }


        }
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
            window.setResizable(false);
            window.setHeight(99,Unit.PERCENTAGE);
            window.addCloseListener(characterView);

            window.setWidth( 31,Unit.EM);
            MainUI.this.addWindow(window);
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
            MainUI.this.addWindow(window);
        }

    }




    //--------------------------CHAPTER UI MANAGING--------------------------



    private final class ChapterUI implements CrudListener<Chapter>
    {
        private List<Chapter> chapterList;
        private HorizontalSplitPanel[] panelSplitArray;                //qui est le root du tab
        private Panel[] panelArray;                     //qui est dansun split panel
        private ChapterPVLayout[] chapterLArray;        //qui est dans un panel

        public void init()
        {
            panelSplitArray= new HorizontalSplitPanel[6];
            panelArray = new Panel[6];
            chapterLArray= new ChapterPVLayout[6];



            for(int i=0;i<6;i++)
            {
                chapterLArray[i]=new ChapterPVLayout();
                chapterLArray[i].setImmediate(true);
                chapterLArray[i].setSpacing(true);
            }


            for(int i=0;i<6;i++)
            {
                panelArray[i]=new Panel();
                panelArray[i].setContent(chapterLArray[i]);
                panelArray[i].setWidth(100,Unit.PERCENTAGE);
                panelArray[i].setId(String.valueOf(i));             //Every Panel has the number of it's phase
            }

            for(int i=0;i<6;i++)
            {
                panelSplitArray[i]=new HorizontalSplitPanel(panelArray[i],null);
                panelSplitArray[i].setSizeFull();
                panelSplitArray[i].setId(Integer.toString(i));
                panelSplitArray[i].setMinSplitPosition(20,Unit.PERCENTAGE);
                panelSplitArray[i].setMaxSplitPosition(45,Unit.PERCENTAGE);
            }
            VaadinSession.getCurrent().setAttribute("chapterCountCrrntPhase",chapterLArray[phaseSelected].getComponentCount());

        }


        private void newChapter()
        {
            Chapter chapter =new Chapter();
            chapter.setPhase(phaseSelected);
            chapter.setDiagramId(diagramId);
            Integer position = (Integer) VaadinSession.getCurrent().getAttribute("chapterCountCrrntPhase");
            chapter.setPosition(position.shortValue());

            ChapterView chapterView= new ChapterView(chapter);
            chapterView.setHandler(chapterPresenter);
            chapterView.addCrudListener(this);
            NWrapperPanel wrapper=new NWrapperPanel(chapterView);
            chapterView.wrap(wrapper);

            Window window=new Window("New Chapter",wrapper);
            window.setModal(true);
            window.setResizable(false);

            MainUI.this.addWindow(window);
        }

        private void loadChapters()
        {
            chapterList= chapterPresenter.getDaoService().getAllChaptersByPhase(phaseSelected,diagramId);
            chapterLArray[phaseSelected].removeAllComponents();

            if(!chapterList.isEmpty())
            {
                for(Chapter chapter:chapterList)
                {

                    chapter.setScenes(scenePresenter.getDaoService().getAllScenesByChapter(chapter.getId()));
                    addChapterView(chapter);
                }

                panelSplitArray[phaseSelected].setSecondComponent(sceneUI.loadSceneStickers(chapterList.get(0)));                   //load scene for the first chapter displayed at starting

            }
            else{
                System.out.println("No chapter fot this phase");
            }
        }

        //The valuechangeListener of TabSheet must be added before
        private void initTabSheet()
        {
            tabSheet.addTab(panelSplitArray[0],"Exposition");
            tabSheet.addTab(panelSplitArray[1],"Conflict");
            tabSheet.addTab(panelSplitArray[2],"Rising Action");
            tabSheet.addTab(panelSplitArray[3],"Climax");
            tabSheet.addTab(panelSplitArray[4],"Falling Action");
            tabSheet.addTab(panelSplitArray[5],"Resolution");
        }

        private void addChapterView(Chapter chapter){

            ChapterView chapterW = new ChapterView(chapter);
            chapterW.setHandler(chapterPresenter);
            chapterW.addCrudListener(this);
            NWrapperPanel wrapper=new NWrapperPanel(chapterW);
            wrapper.setSizeFull();
            wrapper.setId(String.valueOf(chapter.getId()));
            wrapper.setStyleName("blue-hover",true);
            wrapper.addClickListener(event -> panelSplitArray[phaseSelected].setSecondComponent(sceneUI.loadSceneStickers(chapter)));
            chapterW.wrap(wrapper);
            chapterLArray[phaseSelected].addComponent(wrapper);
            VaadinSession.getCurrent().setAttribute("chapterCountCrrntPhase",chapterLArray[phaseSelected].getComponentCount());
            wrapper.setCaption("Chapter "+ chapterLArray[phaseSelected].getComponentCount());

        }




        @Override
        public void created(Chapter o) {
            addChapterView(o);
            for(Window w: MainUI.this.getWindows())
            {
                if(w.getCaption().equals("New Chapter"))
                {
                    w.close();
                }
            }

            panelSplitArray[phaseSelected].setSecondComponent(sceneUI.loadSceneStickers(o));
        }

        @Override
        public void updated(Chapter o) {

            NWrapperPanel wrapperReplaced=(NWrapperPanel)chapterLArray[phaseSelected].getComponent(o.getPosition());
            Component wrapperChanged=chapterLArray[phaseSelected].getComponentByChapter(o.getId());
            short oldPlace=(short)chapterLArray[phaseSelected].getComponentIndex(wrapperChanged);
            ((ChapterView)wrapperReplaced.getWrappedComponent()).setChapterPlace(oldPlace);
            chapterLArray[phaseSelected].replaceComponent(wrapperReplaced,wrapperChanged);            //because WrapperPanels have as id the id of the their chapter


        }

        @Override
        public void deleted(Chapter o) {

            chapterLArray[(int)phaseSelected].removeChapter(o.getId());

            if(o.getScenes()!=null)
            {
                for (Scene s : o.getScenes())
                {
                    try{

                        scenePresenter.getDaoService().deleteImage(s.getPicture(), 1, Integer.valueOf(VaadinSession.getCurrent().getAttribute("diagramId").toString()));
                    }
                    catch (IOException e)
                    {
                        System.out.println("The scene "+s.getId()+" had no image");
                    }
                }

            }

            for(int i=(tabSheet.getComponentCount()-1);i>=6;i--)
            {
                tabSheet.removeTab(tabSheet.getTab(i));
            }
            panelSplitArray[phaseSelected].setSecondComponent(null);


        }

    }

}


