package be.beme.schn.vaadin.narrative.view;

import be.beme.schn.Constants;
import be.beme.schn.narrative.component.Character;
import be.beme.schn.narrative.component.Trait;
import be.beme.schn.narrative.component.UserProperty;
import be.beme.schn.vaadin.crud.CrudListener;
import be.beme.schn.vaadin.crud.CrudNotifier;
import be.beme.schn.vaadin.ImageUploadPanel;
import be.beme.schn.vaadin.narrative.NWrapped;
import be.beme.schn.vaadin.narrative.NWrapper;
import be.beme.schn.vaadin.narrative.NWrapperPanel;
import be.beme.schn.vaadin.crud.TraitTableCrud;
import be.beme.schn.vaadin.narrative.presenter.CharacterPresenter;
import be.beme.schn.vaadin.narrative.presenter.NarrativePresenter;
import be.beme.schn.vaadin.narrative.presenter.TraitCrudAction;
import com.vaadin.server.AbstractErrorMessage;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dorito on 26-03-16.
 */

//TODO un URI pour chaque Window?
//TODO mettre tous les view et presenter en final rien ue parce que dans ton esprit elles n'ont pas été concues pour etre héritées, de plus que c'est un projet interne, pas un projet ver sl'exteiruer comme un framework.

public final class CharacterView extends CustomComponent implements NarrativeView, NWrapped, CrudListener<Trait>, Window.CloseListener, CrudNotifier<Character> {

    private Panel rootPanel;
    private TraitTableCrud traitTableCrud;
    private List<UserProperty> userPropertyList;
    private Character character;
    private Button buttonReferences;
    private TextField name;
    private ComboBox type;
    private TextArea notesTa;
    private ArrayList<Trait> createTraits;
    private ArrayList<Trait> updateTraits;
    private ArrayList<Trait> deleteTraits;
    private ImageUploadPanel imageUploadPanel;
    private CharacterPresenter characterPresenter;
    private TraitCrudAction traitCrudPresenter;
    private VerticalLayout vLayoutProperties;
    private NWrapperPanel wrapper;
    private Button buttonAddSc;
    private Button buttonErase;
    private Button buttonSave;
    private Button buttonSet;
    private ArrayList<CrudListener<Character>> listeners;


    public CharacterView(Character character, TraitCrudAction traitCrudPresenter) {
        this.character = character;
        this.traitCrudPresenter = traitCrudPresenter;
        listeners = new ArrayList<>();

        setId("CharacterView");
        setHeight(99, Unit.PERCENTAGE);
        setWidth(30, Unit.EM);
        setCompositionRoot(buildContent());

    }

    @Override
    public void wrap(NWrapper wrapper) {
        this.wrapper = (NWrapperPanel) wrapper;
        confWrapperBtns();
        if (this.character.getId() == 0) {
            buttonErase.setVisible(false);
            buttonSet.setVisible(false);
        }
    }

    private void confWrapperBtns() {
        buttonErase = wrapper.getButtonErase();
        buttonSave = wrapper.getButtonSave();
        buttonSet = wrapper.getButtonSet();
        buttonErase.addClickListener(this);
        buttonSave.addClickListener(this);
        buttonSet.addClickListener(this);
        buttonSet.setVisible(false);
    }

    private Component buildContent() {
        VerticalLayout verticalLayout = new VerticalLayout();

        imageUploadPanel = new ImageUploadPanel((String) VaadinSession.getCurrent().getAttribute("characterDirectory"), this.character.getPicture());

        verticalLayout.addComponent(imageUploadPanel);
        verticalLayout.setExpandRatio(imageUploadPanel, 4);
        verticalLayout.addComponent(buildPropertiesPanel());
        verticalLayout.setExpandRatio(vLayoutProperties, 6);

        rootPanel = new Panel();
        rootPanel.setSizeFull();
        rootPanel.setContent(verticalLayout);

        return rootPanel;
    }

    private Layout buildPropertiesPanel() {
        vLayoutProperties = new VerticalLayout();
        vLayoutProperties.setSizeFull();
        vLayoutProperties.setMargin(true);
        vLayoutProperties.addComponent(new Label("<h3>Simple Properties</h3>", ContentMode.HTML));
        vLayoutProperties.addComponent(buildFormLayout());
        notesTa = new TextArea("Notes");
        notesTa.setValue(character.getNote());
        notesTa.setNullRepresentation("");
        notesTa.setWidth(100, Unit.PERCENTAGE);
        notesTa.setRows(Constants.TEXTAREA_ROWS_CHARACTER);
        vLayoutProperties.addComponent(notesTa);
       /* vLayoutProperties.addComponent(new Label("<h3>User Properties</h3>", ContentMode.HTML));
        vLayoutProperties.addComponent(buildFormLayoutUserProperties());*/

        return vLayoutProperties;
    }

    private Component buildFormLayout()                                                                                    //TODO redimmensionner toutes les images pour économie de place
    {
        FormLayout formLayout = new FormLayout();
        formLayout.setSizeFull();
        name = new TextField("Name");
        name.setRequired(true);
        name.setNullRepresentation("");
        name.setValue(this.character.getName());
        type = new ComboBox("Type");
        type.setRequired(true);
        type.addItems(new Object[]{"Principal", "Secondary", "Hero", "Auxiliary", "Opponent", "Group"});
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

    private Component buildFormLayoutUserProperties()                                                                   //TODO activer le bouton save que quand modification pour ne pas faire du traffic pour rien, si l'user au lieu de cliquer sur croix clique tjrs sur save
    {
       /* userPropertyList = character.getUserProperties();
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

        return formLayout;*/
        return new FormLayout();
    }

    private Table buildTraitTableCrud() {
        traitTableCrud = new TraitTableCrud("List of traits");
        traitTableCrud.addCrudListener(this);
        traitTableCrud.fillTable(traitCrudPresenter.getTraitService().getTraitsByCharacter(this.character.getId()));
        createTraits = new ArrayList<>();
        updateTraits = new ArrayList<>();
        deleteTraits = new ArrayList<>();
        return traitTableCrud;
    }


    @Override
    public void buttonClick(Button.ClickEvent event) {


        try {
            switch (event.getButton().getCaption()) {

                case "Save": {
                    save();
                    break;
                }
                case "Erase": {
                    erase();
                    break;
                }
                case "Show relations": {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void save() {
        boolean isNew = true;

        if (saveCheck()) {
            if (this.character.getId() != 0)
                isNew = false;

            this.character.setType(type.getValue().toString());
            this.character.setNote(notesTa.getValue());
            this.character.setPicture(imageUploadPanel.getFileName());
//            this.character.setTraits(traitTableCrud.getTraits());                          //reinit Character's list of traits


            this.character = this.characterPresenter.save();

            if (this.character != null) {
                createTraits();
                updateTraits();
                deleteTraits();
                imageUploadPanel.commit();

                if (isNew)
                    notifyCreated(this.character);
                else
                    notifyUpdated(this.character);

                wrapper.closeIfWindow();


            } else {
                Notification.show(Constants.MSG_SYS_ERR, Constants.MSG_REPORT_SENT, Notification.Type.ERROR_MESSAGE);
                return;
            }
        }


    }

    private boolean saveCheck() {
        name.setComponentError(null);
        type.setComponentError(null);
        this.character.setName(name.getValue());
        if (name.isEmpty() || type.isEmpty()) {
            name.setComponentError(new UserError("Required fields not filled", AbstractErrorMessage.ContentMode.TEXT, ErrorMessage.ErrorLevel.INFORMATION));
            type.setComponentError(new UserError("Required fields not filled", AbstractErrorMessage.ContentMode.TEXT, ErrorMessage.ErrorLevel.INFORMATION));
            return false;
        } else if (characterPresenter.checkExist() && (this.character.getId() == 0)) {
            name.setComponentError(new UserError("This name character already exists", AbstractErrorMessage.ContentMode.TEXT, ErrorMessage.ErrorLevel.INFORMATION));
            return false;
        }
        return true;
    }

    private void erase() {
        boolean eraseCok = false;
        // eraseTsok = traitPresenter.deleteAllTraitsByCharacter(this.character.getId());
        eraseCok = this.characterPresenter.erase();
        if (eraseCok) {
            imageUploadPanel.deleteImage();
            notifyDeleted(this.character);
            wrapper.closeIfWindow();
        } else {
            Notification.show(Constants.MSG_SYS_ERR, Constants.MSG_REPORT_SENT, Notification.Type.ERROR_MESSAGE);
            return;
        }
    }

    public Character getCharacter() {
        return character;
    }

    @Override
    public void setHandler(NarrativePresenter narrativePresenter) {
        this.characterPresenter = (CharacterPresenter) narrativePresenter;
    }

    @Override
    public void created(Trait o) {

        o.setDiagramId((int)VaadinSession.getCurrent().getAttribute("diagramId"));
        createTraits.add(o);

    }

    private void createTraits() {
        for (Trait t : createTraits) {
            t.setCharacterId(this.character.getId());
            traitCrudPresenter.create(t);
        }
    }

    @Override
    public void updated(Trait o) {
        updateTraits.add(o);
    }

    private void updateTraits() {
        for (Trait t : updateTraits) {
            traitCrudPresenter.update(t);
        }
    }

    @Override
    public void deleted(Trait o) {
        deleteTraits.add(o);
    }

    private void deleteTraits() {
        for (Trait t : deleteTraits) {
            traitCrudPresenter.delete(t);
        }
    }


    @Override
    public void windowClose(Window.CloseEvent e) {
        imageUploadPanel.deleteTmpDir();
    }


    @Override
    public NWrapper getWrapper() {
        return wrapper;
    }


    @Override
    public void addCrudListener(CrudListener<Character> listener) {
        listeners.add(listener);
    }

    @Override
    public void notifyCreated(Character target) {
        for (CrudListener l : listeners) {
            l.created(target);
        }
    }

    @Override
    public void notifyUpdated(Character target) {
        for (CrudListener l : listeners) {
            l.updated(target);
        }
    }

    @Override
    public void notifyDeleted(Character target) {
        for (CrudListener l : listeners) {
            l.deleted(target);
        }
    }
}
