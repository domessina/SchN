package be.beme.schn.vaadin;

import be.beme.schn.Constants;
import be.beme.schn.narrative.component.Chapter;
import be.beme.schn.narrative.component.Character;
import be.beme.schn.narrative.component.Diagram;
import be.beme.schn.narrative.component.Scene;
import be.beme.schn.vaadin.crud.CrudListener;
import be.beme.schn.vaadin.dd.SwapDDGridLayout;
import be.beme.schn.vaadin.dd.SwapGridLayoutDropEvent;
import be.beme.schn.vaadin.dd.SwapDragAndDropWrapper;
import be.beme.schn.vaadin.narrative.ChapterPVLayout;
import be.beme.schn.vaadin.narrative.NWrapperPanel;
import be.beme.schn.vaadin.narrative.presenter.*;
import be.beme.schn.vaadin.narrative.view.*;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import de.steinwedel.messagebox.MessageBox;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dorito on 17-03-16.
 */
@Theme("mytheme")
@Title("Narrative Diagram")
//@PreserveOnRefresh  //Attention est ce que les trucs qui sont reliés à l'url comme URI, query parameters seront gardés?
//Push renseigne toi y  https://blog.oio.de/2014/01/13/overview-vaadin-7-annotations/
@SpringUI
//TODO rajouter une grande scrollbar verticale pour quand on rapetissie la page
public class MainUI extends UI implements TabSheet.SelectedTabChangeListener, CrudListener<Character> {                                       //TODO lock le ui à chaque fois que l'on sauvegarde ou erase , car accès à la Db peut etre lent

    @Autowired
    DiagramPresenter diagramPresenter;

    @Autowired
    UserPresenter userPresenter;

    @Autowired
    CharacterPresenter characterPresenter;

    @Autowired
    TraitCrudAction traitCrudPresenter;

    @Autowired
    ChapterPresenter chapterPresenter;

    @Autowired
    ScenePresenter scenePresenter;

    @Autowired
    CharacterScenePresenter characterScenePresenter;

    public short phaseSelected;
    public int diagramId;
    public int userId;
    public int selectedChapterId;
    private final TabSheet tabSheet;
    private final ChapterUI chapterUI;
    private final CharacterUI characterUI;
    private final SceneUI sceneUI;
    private MenuBar.MenuItem charsItem;
    private MenuBar.Command commandOpenChar;


    public MainUI()
    {

        chapterUI= new ChapterUI();
        characterUI= new CharacterUI();
        sceneUI= new SceneUI();
        tabSheet= new TabSheet();
        setErrorHandler(new CustomErrorHandler());
    }


    @Override
    protected void init(VaadinRequest vaadinRequest) {

        boolean presetOk;
        if(Constants.SOCIAL_COMPATIBILITY) {
            userId = Integer.valueOf(vaadinRequest.getParameter(Constants.SOCIAL_HTTP_PARAM_USER_ID));
           presetOk= preset();
        }else{
            userId=1;
            presetOk=preset();
        }
        if(presetOk) {
            tabSheet.setSizeFull();
            tabSheet.setImmediate(true);
            tabSheet.setStyleName("margins");
            tabSheet.addSelectedTabChangeListener(this);    //must be set before
            chapterUI.init();
            chapterUI.initTabSheet();

            tabSheet.setSelectedTab(0);

            setContent(buildContent());
        }
    }

    /**
     * If tests are passed, return true
     * Otherwise preset decides what to display
     *
     * return true if the presets run well
     * **/
    private boolean preset()
    {
        userPresenter.setUserId(userId);
        checkUserCookie(userId);
        int diagramId =userPresenter.getActualDiagram();
        List<Diagram> diagrams=diagramPresenter.getDaoService().getAllDiagramsByUser(userId);
        if(diagrams.size()==0){
           showDiagramEditionWindow(new Diagram(userId),true);
        }
        else if(diagramId==-1){
            DiagramChoiceView dCV=new DiagramChoiceView(diagrams);
            dCV.setClosable(false);
            dCV.setDiagramSelectListener(diaId -> {
                userPresenter.setActualDiagram(diaId);
                Page.getCurrent().reload();
            });
            MainUI.this.addWindow(dCV);
        }
        else{
            this.diagramId=diagramId;
            String diagramBaseDir= Constants.BASE_DIR+"Users\\"+userId+"\\Diagrams\\"+diagramId+"\\";
            initSessionAttributes(userId,diagramId,
                    diagramBaseDir+"Characters\\",
                    diagramBaseDir,
                    diagramBaseDir+"Scenes\\");
            diagramPresenter.setActionDiagramToSynch("UPDATE",diagramId);        //each time the session is opened on a diagram, we suppose the user wil modify it. So it is a diagram to synch with the mobile version
            return true;
        }
        return false;
    }

    private int checkUserCookie(int userId){

        Cookie cookie=VaadinUtils.getCookieByName(Constants.CK_DIAGRAM_ID);
        //if does not exist, no problem, because userId is given by socialPlatform via HttpRequest
        if(cookie==null){
            CookieManager.addCookieUserId(String.valueOf(userId));//cookie scope is session
            return 1;
        }else{
            return Integer.valueOf(cookie.getValue());
        }
    }


    private void initSessionAttributes(int userId, int diagramId, String characterDirectory,
                                       String diagramDirectory,String sceneDirectory)
    {
        VaadinSession.getCurrent().setAttribute("diagramId",diagramId);
        VaadinSession.getCurrent().setAttribute("userId",userId);
        VaadinSession.getCurrent().setAttribute("characterDirectory",characterDirectory);
        VaadinSession.getCurrent().setAttribute("diagramDirectory",diagramDirectory);
        VaadinSession.getCurrent().setAttribute("sceneDirectory",sceneDirectory);
    }

    private void showDiagramEditionWindow(Diagram d,final boolean isNew){

        DiagramEditionView dV = new DiagramEditionView(d);
        dV.isNewDiagram=isNew;
        dV.setHandler(diagramPresenter);
        diagramPresenter.setView(dV);

        NWrapperPanel wrapper= new NWrapperPanel(dV);
        wrapper.setSizeFull();
        dV.wrap(wrapper);

        Window window = new Window("New Diagram",wrapper);
        window.setModal(true);
        window.setResizable(false);
        window.setHeight(99,Unit.PERCENTAGE);
        window.setWidth( 31,Unit.EM);
        if(isNew)
            window.setClosable(false);
        window.addCloseListener(e->{
            diagramEditClosed(dV);
            if(isNew)
                Page.getCurrent().reload();
        });
        MainUI.this.addWindow(window);
    }



    private Component buildContent()               //TODO dire au forums que mettre un command dans un sous menu ne marche pas

    {

                ArrayList<Character> c=new ArrayList<>();
        c.add(new Character());
        MenuBar menuBar=buildMenu();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.addComponent(menuBar);
        verticalLayout.setExpandRatio(menuBar,1);
        verticalLayout.addComponent(tabSheet);
        verticalLayout.setExpandRatio(tabSheet,14);
        return verticalLayout;
    }

    private MenuBar buildMenu()
    {
        commandOpenChar = selectedItem2 ->characterUI.showCharacter(Integer.valueOf(selectedItem2.getDescription()));

        MenuBar menuBar=new MenuBar();
        menuBar.setWidth(100,Unit.PERCENTAGE);

        //NEW
        MenuBar.MenuItem newz = menuBar.addItem("New...",null, null);
        newz.addItem("Diagram",selectedItem2 -> openNewDiagram());
        newz.addItem("Chapter",selectedItem ->chapterUI.newChapter());
        newz.addItem("Scene",selectedItem1 -> {
            if(selectedChapterId!=-1)
                sceneUI.newScene(selectedChapterId);
        });
        newz.addItem("Character",selectedItem ->  characterUI.newCharacter());

        //OPEN
        MenuBar.MenuItem open= menuBar.addItem("Open...",null,null);
        final Integer s=null;
        open.addItem("Diagram...",selectedItem1 -> {
            DiagramChoiceView dCV= new DiagramChoiceView(diagramPresenter.getDaoService().getAllDiagramsByUser(userId));
            dCV.setDiagramSelectListener(diaId -> {
//                s.toString();
                userPresenter.setActualDiagram(diaId);
                Page.getCurrent().reload();
            });
            MainUI.this.addWindow(dCV);
        });

        charsItem=open.addItem("Character",null);
        for(Character c:characterPresenter.getDaoService().getAllCharactersByDiagram(diagramId))
        {
            charsItem.addItem(c.getName(), commandOpenChar).setDescription(Integer.toString(c.getId()));
        }

        //SETTINGS
        MenuBar.MenuItem settings=menuBar.addItem("Settings",null);
        settings.addItem("Edit diagram",selectedItem -> showDiagramEditionWindow(diagramPresenter.getDaoService().getDiagramById(diagramId),false));
        MenuBar.MenuItem blank=menuBar.addItem("            ",null);
        blank.setEnabled(false);

        //QUIT
        //Don't invalidade de session because it stops all background vaadin process. it's better to let him do it himself
        menuBar.addItem("Quit",selectedItem -> quitToSocialPlatform());
        //get it via param ent by social platform
        MenuBar.MenuItem user=menuBar.addItem(userPresenter.getUserPseudo(),null);
        user.setEnabled(false);


        return menuBar;
    }

    private void diagramEditClosed(DiagramEditionView dEV){
        dEV.wrapperClosed();
        int diid=dEV.getDiagram().getId();
        userPresenter.setActualDiagram(diid);
    }


    private void openNewDiagram(){
        MessageBox.createQuestion().withCaption("Important")
                .withMessage(Constants.MSG_SAVE_CONTENT_BEFORE)
                .withYesButton(()-> showDiagramEditionWindow(new Diagram(userId),true))
                .withCancelButton()
                .open();
    }

    private void quitToSocialPlatform(){
        MessageBox.createQuestion().withCaption("Important")
                .withMessage(Constants.MSG_SAVE_CONTENT_BEFORE)
                .withYesButton(()-> {
                    MainUI.this.close();
                    this.getPage().setLocation(Constants.SOCIAL_URL);})
                .withCancelButton()
                .open();
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

    @Override
    public void created(Character o) {

       charsItem.addItem(o.getName(), commandOpenChar).setDescription(Integer.toString(o.getId()));
    }

    @Override
    public void updated(Character o) {

        for(MenuBar.MenuItem item: charsItem.getChildren())
        {
            if(item.getDescription().equals(String.valueOf(o.getId())))                                                        //we update the character's name showed in the menu
            {
                item.setText(o.getName());
            }
        }
    }

    @Override
    public void deleted(Character o) {
        List<MenuBar.MenuItem> list=charsItem.getChildren();
        List<MenuBar.MenuItem> iToRemove=new ArrayList<>();
        for(MenuBar.MenuItem mi:list)
        {
            if(mi.getText().equals(o.getName()))
            {
                iToRemove.add(mi);
            }
        }
        for(MenuBar.MenuItem mi:iToRemove)
        {
            charsItem.removeChild(mi);
        }
        iToRemove.clear();
    }


    //------------------------SCENE UI MANAGING---------------------------

    private final class SceneUI implements  CrudListener<Scene>, Component.Listener
    {
        private SwapDDGridLayout ddGLayout;

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


            SceneViewExtended scvE= new SceneViewExtended(scene,characterPresenter.getDaoService().getAllCharactersByDiagram(diagramId));

            List<Character> characterSc =characterScenePresenter.getDaoService().getAllCharactersByScene(scene.getId());
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
            scene.setPlace(scenePresenter.getDaoService().getNumberOfScenes(chapterId));
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
            selectedChapterId=chapter.getId();
            Panel pstickers = new Panel();
            pstickers.setStyleName("background-grey");
            ddGLayout = new SwapDDGridLayout();
//            ddGLayout.setImmediate(true);                                                                                 //for ddGLayout.getComponentCount being up to date.
            ddGLayout.addDropListener(this);
            ddGLayout.getGridLayout().setSpacing(true);
            ddGLayout.addStyleName("no-vertical-drag-hints");
            ddGLayout.addStyleName("no-horizontal-drag-hints");

            try {
                int nbScenes=scenePresenter.getDaoService().getNumberOfScenes(chapter.getId());
                int rows = (int) Math.ceil(nbScenes/3);
                ddGLayout.getGridLayout().removeAllComponents();

                if(rows==0)
                    rows=1;

                ddGLayout.getGridLayout().setRows(rows);
                ddGLayout.getGridLayout().setColumns(3);

                List<Scene> scenes =scenePresenter.getDaoService().getAllScenesByChapter(chapter.getId());

                for(Scene s : scenes)
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
                    ddGLayout.addComponent(sticker);

                }

            }
            catch (NullPointerException e)
            {
                System.out.println("This chapter has no scenes");
            }

            ddGLayout.getGridLayout().setWidth(100,Unit.PERCENTAGE);

            pstickers.setSizeFull();
            pstickers.setContent(ddGLayout);

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

            int index=o.getPlace();

            //from deleted scene the position of following scenes are reduced of 1
            List<Scene> scenes=scenePresenter.getDaoService().getAllScenesByChapter(o.getChapterId());

            for(int i=index;i<scenes.size();i++)
            {
                scenePresenter.changePosition(-1,Integer.valueOf(scenes.get(i).getId()));
            }

            tabSheet.removeTab(tabSheet.getTab(tabSheet.getSelectedTab()));

        }

        @Override
        public void componentEvent(Event event) {
            if(event instanceof SwapGridLayoutDropEvent)
            {
                //the stickers are already switched in the layout
                SwapGridLayoutDropEvent e =(SwapGridLayoutDropEvent) event;

                //the scene id moved by the user
                int idSceneDragged=Integer.valueOf(getSceneStickerId(e.getXdest(),e.getYdest()));
                int idSceneMovedByForce=Integer.valueOf(getSceneStickerId(e.getXsrc(),e.getYsrc()));                    //the moved by force sticker is now at the place where the sticker dragged wa before to be dropped
                                                                                                                        //the stickers dragged is the source of this event (which is in GridLayout)
                scenePresenter.switchPosition(idSceneDragged,idSceneMovedByForce);
            }

        }

        private String getSceneStickerId(int x, int y)
        {
            SwapDragAndDropWrapper wrappedDrag=(SwapDragAndDropWrapper) ddGLayout.getGridLayout().getComponent(x,y);
            Panel p=(Panel)wrappedDrag.getCompositionRoot();
            return p.getId();
        }
    }



    //----------------------CHARACTER UI MANAGING--------------------------

    private final class CharacterUI                 //TODO tu n'as même pas de variables de classe pour ces classes internes, lesr etirer non?
    {
        private void newCharacter()
        {
            Character character= new Character();
            character.setDiagram_id(diagramId);

            CharacterView characterView = new CharacterView(character, traitCrudPresenter);
            characterView.setHandler(characterPresenter);
            characterView.addCrudListener(MainUI.this);
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
            Character character = characterPresenter.getDaoService().getNComponent(id);
            CharacterView characterView = new CharacterView(character, traitCrudPresenter);
            characterView.setHandler(characterPresenter);
            characterView.addCrudListener(MainUI.this);
            characterPresenter.setView(characterView);
            NWrapperPanel wrapper= new NWrapperPanel(characterView);
            wrapper.setSizeFull();
            characterView.wrap(wrapper);

            Window window = new Window(null,wrapper);
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
                panelSplitArray[i].setMaxSplitPosition(45,Unit.PERCENTAGE);
                panelSplitArray[i].setMinSplitPosition(20,Unit.PERCENTAGE);
                panelSplitArray[i].setSplitPosition(20,Unit.PERCENTAGE);
            }
            VaadinSession.getCurrent().setAttribute("chapterCountCrrntPhase",chapterLArray[phaseSelected].getComponentCount());

        }


        private void newChapter()
        {
            Chapter chapter =new Chapter();
            chapter.setPhase(phaseSelected);
            chapter.setDiagramId(diagramId);
//            Integer position = (Integer) VaadinSession.getCurrent().getAttribute("chapterCountCrrntPhase");
            Integer position = chapterLArray[phaseSelected].getComponentCount();
            chapter.setPosition(position.shortValue());

            ChapterView chapterView= new ChapterView(chapter);
            chapterView.setHandler(chapterPresenter);
            chapterView.addCrudListener(this);
            NWrapperPanel wrapper=new NWrapperPanel(chapterView);
            chapterView.wrap(wrapper);

            Window window=new Window("New Chapter",wrapper);
            window.setId("New Chapter");
            window.setModal(true);
            window.setResizable(false);
            window.setWidth(40,Unit.PERCENTAGE);

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
//                    chapter.setScenes(scenePresenter.getDaoService().getAllScenesByChapter(chapter.getId()));
                    addChapterView(chapter);
                }

                panelSplitArray[phaseSelected].setSecondComponent(sceneUI.loadSceneStickers(chapterList.get(0)));                   //load scene for the first chapter displayed at starting

            }
            else{
                selectedChapterId=-1;
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
            /*for(Window w: MainUI.this.getWindows())
            {
                if(w.getCaption().equals("New Chapter"))
                {
                    w.close();
                }
            }*/
            VaadinUtils.findWindowById("New Chapter").close();

            panelSplitArray[phaseSelected].setSecondComponent(sceneUI.loadSceneStickers(o));
            chapterList.add(o);

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

            ChapterPVLayout chapterLArray=this.chapterLArray[(int)phaseSelected];
            int index=chapterLArray.removeChapter(o.getId());                                       //see doc of removeChapter of chapterLArray

            int nbScenes=scenePresenter.getDaoService().getNumberOfScenes(o.getId());
            if(nbScenes!=0)
            {
                List<Scene> scenes =scenePresenter.getDaoService().getAllScenesByChapter(o.getId());
                for (Scene s : scenes)
                {
                    try{

                        if(s.getPicture()!=null)
                        {
                            String target= Constants.BASE_DIR+"Users\\"+VaadinSession.getCurrent().getAttribute("userId")+"\\Diagrams\\"+diagramId+"\\Scenes\\"+s.getPicture();
                            Files.delete(Paths.get(target));
                        }

//                        scenePresenter.getDaoService().deleteImage(s.getPicture(), 1, diagramId);
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
            chapterList.remove(o);

            //when no chapter is selected id =-1
            //value will be non negative again when calling loadStickers()
            selectedChapterId=-1;

            // position of following chapters reduced of 1
            int count=chapterLArray.getComponentCount();
            for(int i=index;i<count;i++)
            {
              chapterPresenter.changePosition(-1,Integer.valueOf(chapterLArray.getComponent(i).getId()));
            }

        }


    }

}


