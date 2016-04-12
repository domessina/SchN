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
import be.beme.schn.vaadin.narrative.view.CharacterWindow;
import be.beme.schn.narrative.component.Character;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * Created by Dorito on 17-03-16.
 */
@Theme("valo")
@Title("Narrative Diagram")
//@PreserveOnRefresh  Attention est ce que les trucs qui sont reliés à l'url comme URI, query parameters seront gardés?
//Push renseigne toi y  https://blog.oio.de/2014/01/13/overview-vaadin-7-annotations/
@SpringUI
public class MyVaadinUI extends UI implements TabSheet.SelectedTabChangeListener{

    @Autowired
    DiagramDaoImpl diagramService;

    @Autowired
    CharacterWindowPresenter characterPresenter;


    @Autowired
    private TraitCrudPresenter traitPresenter;

    @Autowired
    private ChapterPresenter chapterPresenter;

    @Override
    protected void init(VaadinRequest vaadinRequest) {


        VerticalLayout verticalLayout = new VerticalLayout();
/*
        Label label=new Label();
        Button button= new Button("here victory!");
        button.addClickListener(event ->{



            //Character character = characterPresenter.getDaoService().getCharacterById(23);
            Character character= new Character();
            character.setDiagram_id(2);
           // character.setId(4);
            *//*character.setName("Christero");
            character.setType("Principal");
            character.setNote("Des informations complémentaires");
            ArrayList<UserProperty> arrayList = new ArrayList();
            arrayList.add(new UserProperty("name","value"));
            arrayList.add(new UserProperty("name","value"));
            arrayList.add(new UserProperty("name","value"));
            arrayList.add(new UserProperty("name","value"));
            character.setUserPropertyList(arrayList);*//*

            //character.setPicture("4.jpg");
            //diagramService.createDiagram(1,"depuis spring");
            CharacterWindow characterWindow= new CharacterWindow(character, traitPresenter);
            characterWindow.setHandler(characterPresenter);
            characterPresenter.setView(characterWindow);
                    this.addWindow(characterWindow);
        }

                //label.setValue(String.valueOf(diagramService.createDiagram(1,"un Livre")))
        );

        ArrayList<Diagram> listDiagram = new ArrayList<>();
        Diagram diagram= new Diagram();
        diagram.setTitle("un Titre");
        for(int i=0;i<16;i++)
        {
            listDiagram.add(diagram);
        }

        verticalLayout.addComponent(button);
        verticalLayout.addComponent(label);
        //Jcrop jcrop=new Jcrop();
        //this.addWindow(new DiagramChoiceWindow(listDiagram));
*/
        ArrayList<Scene> scenes= new ArrayList<Scene>();
        Scene scene;

        for(int i=0;i<15;i++)
        {
            scene= new Scene();
            scene.setPicture("12.jpg");
            scene.setId(i);
            scenes.add(scene);
        }

        ArrayList<ChapterView> chapterViews = new ArrayList<>();

        for(int i=1;i<=2;i++)
        {
            Chapter chapter= new Chapter();
            chapter.setId(i);
            chapter.setScenes(scenes);
            chapter.setDiagramId(2);
            chapter.setPhase("si");
            ChapterView chapterW = new ChapterView(chapter);
            chapterW.setHandler(chapterPresenter);
            NWrapperPanel wrapper=new NWrapperPanel(chapterW);
            wrapper.setSizeFull();
            wrapper.setCaption("Chapter");
            chapterW.wrap(wrapper);
            chapterViews.add(chapterW);
//                verticalLayout.addComponent(chapterW);
        }






        TabSheet tabSheet= new TabSheet();
        ChapterPHLayout chapterLayout= new ChapterPHLayout();
        Panel tabPanel= new Panel();

        chapterLayout.setHeight(100,Unit.PERCENTAGE);
//        chapterLayout.setMargin(true);
        chapterLayout.setSpacing(true);
        chapterLayout.setHeight(100,Unit.PERCENTAGE);

        for(ChapterView cp: chapterViews)
        {
            chapterLayout.addComponent(cp.getWrapper());
        }

        tabPanel.setContent(chapterLayout);
        tabPanel.setHeight(100,Unit.PERCENTAGE);

        tabSheet.setSizeFull();
        tabSheet.setImmediate(true);
        tabSheet.addSelectedTabChangeListener(this);
        tabSheet.addTab(tabPanel,"Initial Situation");

        verticalLayout.setSizeFull();
        HorizontalLayout hl= new HorizontalLayout(new Button("new Chapter",clickEvent -> {
            ChapterView chapterView= new ChapterView(new Chapter());
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
            CharacterWindow characterWindow= new CharacterWindow(character, traitPresenter);
            characterWindow.setHandler(characterPresenter);
            characterPresenter.setView(characterWindow);
            NWrapperPanel wrapper= new NWrapperPanel(characterWindow);
            wrapper.setSizeFull();
            characterWindow.wrap(wrapper);

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
            character.setDiagram_id(2);

            CharacterWindow characterWindow= new CharacterWindow(character, traitPresenter);
            characterWindow.setHandler(characterPresenter);
            characterPresenter.setView(characterWindow);
            NWrapperPanel wrapper= new NWrapperPanel(characterWindow);
            wrapper.setSizeFull();
            characterWindow.wrap(wrapper);

            Window window = new Window("New Character",wrapper);
            window.setModal(true);
            window.setDraggable(false);
            window.setResizable(false);
            window.setHeight(99,Unit.PERCENTAGE);

            window.setWidth( 31,Unit.EM);
            this.addWindow(window);
        }));


        setContent(verticalLayout);
    }

    @Override
    public void selectedTabChange(TabSheet.SelectedTabChangeEvent selectedTabChangeEvent) {

    }
}