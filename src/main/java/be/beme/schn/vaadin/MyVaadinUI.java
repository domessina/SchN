package be.beme.schn.vaadin;

import be.beme.schn.narrative.component.Character;
import be.beme.schn.narrative.component.Diagram;
import be.beme.schn.narrative.component.Trait;
import be.beme.schn.persistence.daoimpl.DiagramDaoImpl;
import be.beme.schn.vaadin.narrative.presenter.CharacterWindowPresenter;
import be.beme.schn.vaadin.narrative.presenter.TraitCrudPresenter;
import be.beme.schn.vaadin.narrative.view.CharacterWindow;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
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
public class MyVaadinUI extends UI {

    @Autowired
    DiagramDaoImpl diagramService;

    @Autowired
    CharacterWindowPresenter characterPresenter;


    @Autowired
    private TraitCrudPresenter traitPresenter;

    @Override
    protected void init(VaadinRequest vaadinRequest) {


        VerticalLayout verticalLayout = new VerticalLayout();

        Label label=new Label();
        Button button= new Button("here victory!");
        button.addClickListener(event ->{



            Character character = characterPresenter.getCharacterService().getCharacterById(10);
            //Character character= new Character();
//            character.setDiagram_id(2);
           // character.setId(4);
            /*character.setName("Christero");
            character.setType("Principal");
            character.setNote("Des informations complémentaires");
            ArrayList<UserProperty> arrayList = new ArrayList();
            arrayList.add(new UserProperty("name","value"));
            arrayList.add(new UserProperty("name","value"));
            arrayList.add(new UserProperty("name","value"));
            arrayList.add(new UserProperty("name","value"));
            character.setUserPropertyList(arrayList);*/

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
        setContent(verticalLayout);
    }
}