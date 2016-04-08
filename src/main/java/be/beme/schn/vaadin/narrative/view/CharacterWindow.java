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
import be.beme.schn.vaadin.narrative.presenter.TraitCrudPresenter;
import com.vaadin.server.AbstractErrorMessage;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.Sizeable;
import com.vaadin.server.UserError;
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

public class CharacterWindow extends WindowView implements CrudListener, Window.CloseListener {

    private Panel propertiesPanel;
    private TraitTableCrud traitTableCrud;
    private List<UserProperty> userPropertyList;
    private Character character;
    private Button buttonReferences;
    private TextField name;
    private ComboBox type;
    private TextArea textArea;
    private ArrayList<Trait> createTraits;
    private ArrayList<Trait> updateTraits;
    private ArrayList<Trait> deleteTraits;
    private  ImageUploadPanel imageUploadPanel;
    private CharacterWindowPresenter characterWindowPresenter;
    private TraitCrudPresenter traitPresenter;


    public CharacterWindow(Character character, TraitCrudPresenter traitPresenter)
    {
        super("Character Informations");
        this.character= character;
        this.traitPresenter = traitPresenter;

        setId("CharacterWindow");
        setHeight(99, Unit.PERCENTAGE);
        setWidth(30, Unit.EM);
        addCloseListener(this);
        setWrappedContent(buildContent());

    }

    private Layout buildContent()
    {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();

        imageUploadPanel= new ImageUploadPanel(Constants.BASE_DIR+"Users\\1"+"\\Diagrams\\"+this.character.getDiagram_id()+"\\Characters\\",this.character.getPicture());

        if(this.character.getId()==0)
        {
            buttonErase.setEnabled(false);
        }

        verticalLayout.addComponent(imageUploadPanel);
       verticalLayout.setExpandRatio(imageUploadPanel,4);
        verticalLayout.addComponent(buildPropertiesPanel());
       verticalLayout.setExpandRatio(propertiesPanel,6);

        return verticalLayout;
    }

    private Panel buildPropertiesPanel()
    {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(true);
        verticalLayout.addComponent(new Label("<h3>Simple Properties</h3>",ContentMode.HTML));
        verticalLayout.addComponent(buildFormLayout());
        textArea= new TextArea("Notes");
        textArea.setValue(character.getNote());
        textArea.setNullRepresentation("");
        textArea.setWidth(100,Unit.PERCENTAGE);
        verticalLayout.addComponent(textArea);
        verticalLayout.addComponent(new Label("<h3>User Properties</h3>", ContentMode.HTML));
        verticalLayout.addComponent(buildFormLayoutUserProperties());
        propertiesPanel = new Panel(verticalLayout);
        propertiesPanel.setSizeFull();
        return propertiesPanel;
    }

    private Component buildFormLayout()
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
        formLayout.addComponent(buildTraitTableCrud());
        buttonReferences = new Button("Show relations");
        buttonReferences.addClickListener(this);
        formLayout.addComponent(buttonReferences);
        return formLayout;
    }

    private Component buildFormLayoutUserProperties()
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

    private Table buildTraitTableCrud()
    {
        traitTableCrud = new TraitTableCrud("List of traits");
        traitTableCrud.addCrudListener(this);
        traitTableCrud.fillTable(traitPresenter.getTraitService().getAllTraitsByCharacter(this.character.getId()));
        createTraits=new ArrayList<>();
        updateTraits=new ArrayList<>();
        deleteTraits=new ArrayList<>();
        return traitTableCrud;
    }




    @Override
    public void buttonClick(Button.ClickEvent event) {

        name.setComponentError(null);
        type.setComponentError(null);
        if(name.isEmpty()||type.isEmpty())
        {
            name.setComponentError(new UserError("Required fields not filled", AbstractErrorMessage.ContentMode.TEXT, ErrorMessage.ErrorLevel.INFORMATION));
            type.setComponentError(new UserError("Required fields not filled", AbstractErrorMessage.ContentMode.TEXT, ErrorMessage.ErrorLevel.INFORMATION));
        }
        else {

            this.character.setName(name.getValue());
            this.character.setType(type.getValue().toString());
            this.character.setNote(textArea.getValue());
            this.character.setPicture(imageUploadPanel.getFileName());
//            this.character.setAllTraits(traitTableCrud.getAllTraits());                          //reinit Character's list of traits

             boolean eraseCok=false;
            try {
                switch (event.getButton().getCaption()) {

                    case "Save": {
                        this.character = this.characterWindowPresenter.save();


                        //TODO gérer si exception durant crud Traits
                        if (this.character != null) {
                            createTraits();
                            updateTraits();
                            deleteTraits();
                            imageUploadPanel.commit();
                            close();
                        } else {
                            Notification.show(Constants.SYS_ERR,Constants.REPORT_SENT, Notification.Type.ERROR_MESSAGE);
                        }

                        break;
                    }
                    case "Erase": {
                       // eraseTsok = traitPresenter.deleteAllTraitsByCharacter(this.character.getId());
                        eraseCok = this.characterWindowPresenter.erase();
                        if (eraseCok) {
                            imageUploadPanel.deleteImage();
                            close();
                        } else {
                            Notification.show(Constants.SYS_ERR,Constants.REPORT_SENT, Notification.Type.ERROR_MESSAGE);
                        }
                        break;
                    }
                    case "Show relations": {
                        break;
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
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
    public void created(Object o) {

        createTraits.add((Trait)o);

    }

    private void createTraits()
    {
        for(Trait t:createTraits)
        {
            t.setCharacterId(this.character.getId());
            traitPresenter.create(t);
        }
    }

    @Override
    public void updated(Object o) {
        updateTraits.add((Trait)o);
    }

    private void updateTraits()
    {
        for(Trait t:updateTraits)
        {
            traitPresenter.update(t);
        }
    }

    @Override
    public void deleted(Object o) {
        deleteTraits.add((Trait)o);
    }

    private void deleteTraits()
    {
        for(Trait t:deleteTraits)
        {
            traitPresenter.delete(t);
        }
    }


    @Override
    public void windowClose(CloseEvent e) {
        imageUploadPanel.deleteTmpDir();
    }
}
