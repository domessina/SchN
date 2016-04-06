package be.beme.schn.vaadin.narrative.view;

import be.beme.schn.Constants;
import be.beme.schn.narrative.component.Character;
import be.beme.schn.narrative.component.Trait;
import be.beme.schn.narrative.component.UserProperty;
import be.beme.schn.vaadin.ImageUploadPanel;
import be.beme.schn.vaadin.CrudListener;
import be.beme.schn.vaadin.narrative.TraitTableCrud;
import be.beme.schn.vaadin.narrative.presenter.CharacterWindowPresenter;
import be.beme.schn.vaadin.narrative.presenter.NarrativePresenter;
import be.beme.schn.vaadin.narrative.presenter.TraitWindowFieldPresenter;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dorito on 26-03-16.
 */

//TODO un URI pour chaque Window?

public class CharacterWindow extends Window implements Button.ClickListener,NarrativeView,CrudListener {

    private Panel propertiesPanel;
    private TraitTableCrud traitTableCrud;
    private List<UserProperty> userPropertyList;
    private Character character;
    private Button buttonReferences;
    private Button buttonSave;
    private Button buttonErase;
    private TextField name;
    private ComboBox type;
    private TextArea textArea;
    private  ImageUploadPanel imageUploadPanel;
    private ArrayList<Trait> traitList;
    private CharacterWindowPresenter characterWindowPresenter;
    private TraitWindowFieldPresenter traitPresenter;
    //private TraitWindowField traitWindowField;


    public CharacterWindow(Character character, TraitWindowFieldPresenter traitPresenter)
    {
        super("Character Informations");
        this.character= character;
        this.traitPresenter = traitPresenter;
        setResizable(false);
        setModal(true);
        setId("CharacterWindow");
        setHeight(99,Unit.PERCENTAGE);
        setWidth(30, Unit.EM);
        setContent(buildContent());

    }

    private Layout buildContent()
    {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(true);
        verticalLayout.setSizeFull();
       //imageUploadPanel= new ImageUploadPanel( "C:\\Users\\Dorito\\TFEImages\\NarrativeScheme\\Users\\1\\Diagrams\\"+this.character.getDiagram_id()+"\\Characters\\"+this.character.getId()+"\\");
        //TODO ici changer le dir user 1
        imageUploadPanel= new ImageUploadPanel(Constants.BASE_DIR+"Users\\1"+"\\Diagrams\\"+this.character.getDiagram_id()+"\\Characters\\",this.character.getPicture());
        buttonSave = new Button("Save");
        buttonSave.addClickListener(this);
        buttonSave.setStyleName(ValoTheme.BUTTON_FRIENDLY);

        buttonErase = new Button("Erase");
        buttonErase.addClickListener(this);
        buttonErase.setStyleName(ValoTheme.BUTTON_DANGER,true);
        buttonErase.setStyleName(ValoTheme.BUTTON_TINY,true);

        HorizontalLayout horizontalLayout= new HorizontalLayout();
        horizontalLayout.addComponent(buttonErase);
        horizontalLayout.setComponentAlignment(buttonErase,Alignment.MIDDLE_CENTER);
        horizontalLayout.addComponent(buttonSave);
        horizontalLayout.setComponentAlignment(buttonSave,Alignment.MIDDLE_RIGHT);
        horizontalLayout.setWidth(100,Unit.PERCENTAGE);

        verticalLayout.addComponent(imageUploadPanel);
       verticalLayout.setExpandRatio(imageUploadPanel,5);
        verticalLayout.addComponent(getPropertiesPanel());
       verticalLayout.setExpandRatio(propertiesPanel,5);
        verticalLayout.addComponent(horizontalLayout);



        return verticalLayout;
    }

    private Panel getPropertiesPanel()
    {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(true);
        verticalLayout.addComponent(new Label("<h3>Simple Properties</h3>",ContentMode.HTML));
        verticalLayout.addComponent(getFormLayout());
        textArea= new TextArea("Notes");
        textArea.setValue(character.getNote());
        textArea.setNullRepresentation("");
        textArea.setWidth(100,Unit.PERCENTAGE);
        verticalLayout.addComponent(textArea);
        verticalLayout.addComponent(new Label("<h3>User Properties</h3>", ContentMode.HTML));
        verticalLayout.addComponent(getFormLayoutUserProperties());
        propertiesPanel = new Panel(verticalLayout);
        propertiesPanel.setSizeFull();
        return propertiesPanel;
    }

    private Component getFormLayout()
    {
        FormLayout formLayout = new FormLayout();
        formLayout.setSizeFull();
        name = new TextField("Name");
        name.setRequired(true);
        name.setNullRepresentation("");
        name.setValue(this.character.getName());
        type= new ComboBox("Type");
        type.setRequired(true);
        type.addItems(new Object[]{"Principal","Secondary","Hero","Auxiliary","Opponent","Group"});
        type.setFilteringMode(FilteringMode.OFF);
        //type.setReadOnly(true); disable Editing?
        type.setValue(this.character.getType());//If the type set is not known , blanc is displayed
        formLayout.addComponent(name);
        formLayout.addComponent(type);
        formLayout.addComponent(getTraitTableCrud());
        buttonReferences = new Button("Show relations");
        buttonReferences.addClickListener(this);
        formLayout.addComponent(buttonReferences);
        return formLayout;
    }

    public Component getFormLayoutUserProperties()
    {
        userPropertyList = character.getUserPropertyList();
        FormLayout formLayout= new FormLayout();
        try{
            for(UserProperty userProperty : userPropertyList)
            {
                formLayout.addComponent(new TextField(userProperty.getName(), userProperty.getValue()));
            }
        }
        catch (NullPointerException e){
            System.out.println("This character has no user properties");                 //TODO à bn'afficher que pou run certain niveau de debug
        }

        return formLayout;
    }

    private Table getTraitTableCrud()
    {

        traitList = new ArrayList<>(this.character.getAllTraits());
        traitTableCrud = new TraitTableCrud("List of traits",traitList);
        traitTableCrud.addCrudListener(this);
        return traitTableCrud;
    }




    @Override
    public void buttonClick(Button.ClickEvent event) {

        if(name.isEmpty()||type.isEmpty())
        {
            //dès qu'il apparait il ne part plus :/
           // setComponentError(new UserError("Required fields not filled", AbstractErrorMessage.ContentMode.TEXT, ErrorMessage.ErrorLevel.INFORMATION));
        }
        else {

            this.character.setName(name.getValue());
            this.character.setType(type.getValue().toString());
            this.character.setNote(textArea.getValue());
            this.character.setPicture(imageUploadPanel.getFileName());
           // this.character.setAllTraits(null);                          //reinit Character's list of traits
            //for(int i=0; i < tableTrait.

             boolean eraseOK=false;
                switch (event.getButton().getCaption()){

                    case "Save":{
                        this.character= this.characterWindowPresenter.save();
                        this.traitPresenter.save();
                        break;
                    }
                    case "Erase":{ eraseOK=this.characterWindowPresenter.erase();break;}
                    case "Show relations":{ break;}
                }
            if(eraseOK||(this.character!=null))
            {
                close();
            }
            else
            {
                Notification.show(Constants.SYS_ERR,Constants.REPORT_SENT, Notification.Type.ERROR_MESSAGE);
            }

        }
    }

    public Character getCharacter() {
        return character;
    }

    @Override
    public void setHandler(NarrativePresenter narrativePresenter) {
        this.characterWindowPresenter=(CharacterWindowPresenter)narrativePresenter;
    }

    @Override
    public void created(Object o) {              //it's better to get itemId, in this case the programmer can choose witch itemProperty to work on. here , there is only one "Trait", but there could be more.
       // traitList.add(traitTableCrud.getTraitFromItemId(itemId));

    }

    @Override
    public void updated(Object o) {
       /* Trait trait=traitTableCrud.getTraitFromItemId(itemId);
        for(Trait trait2:traitList)
        {
            if(trait2==trait)
            {
                trait2
            }
        }   */

    }

    @Override
    public void deleted(Object o) {
       // traitList.remove(traitTableCrud.getTraitFromItemId(itemId));

    }


}
