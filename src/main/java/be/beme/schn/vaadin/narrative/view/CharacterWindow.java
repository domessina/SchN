package be.beme.schn.vaadin.narrative.view;

import be.beme.schn.narrative.component.Character;
import be.beme.schn.narrative.component.Trait;
import be.beme.schn.narrative.component.UserProperty;
import be.beme.schn.vaadin.ImageUploadPanel;
import be.beme.schn.vaadin.narrative.presenter.CharacterWindowPresenter;
import be.beme.schn.vaadin.narrative.presenter.NarrativePresenter;
import com.vaadin.event.Action;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

/**
 * Created by Dorito on 26-03-16.
 */

//TODO un URI pour chaque Window?
public class CharacterWindow extends Window implements Button.ClickListener,NarrativeView{

    private Panel propertiesPanel;
    private Table tableTrait;
    private List<UserProperty> userPropertyList;
    private Character character;
    private Button buttonReferences;
    private Button buttonSave;
    private Button buttonErase;
    private final static Action ACTION_ADD= new Action("Add");
    private final static Action ACTION_DELETE=new Action("Delete");
    private final static Action ACTION_RENAME=new Action("Rename");
    private CharacterWindowPresenter characterWindowPresenter;

    public CharacterWindow(Character character)
    {
        super("Character Informations");
        setResizable(false);
        setModal(true);
        setId(this.getClass().getName());
        setHeight(99,Unit.PERCENTAGE);
        setWidth(30, Unit.EM);
        this.character= character;


        setContent(buildContent());

    }

    private Layout buildContent()
    {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(true);
        verticalLayout.setSizeFull();

        ImageUploadPanel imageUploadPanel= new ImageUploadPanel();

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
        TextArea textArea= new TextArea("Notes");
        textArea.setValue(character.getNote());
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
        TextField name = new TextField("Name");
        name.setValue(this.character.getName());
        ComboBox type= new ComboBox("Type");
        type.addItems(new Object[]{"Principal","Secondary","Hero","Auxiliary","Opponent","Group"});
        type.setFilteringMode(FilteringMode.OFF);
        //type.setReadOnly(true); disable Editing?
        type.setValue(this.character.getType());//If the type set is not known , blanc is displayed
        formLayout.addComponent(name);
        formLayout.addComponent(type);
        formLayout.addComponent(getTraitTable());
        buttonReferences = new Button("Show relations");
        buttonReferences.addClickListener(this);
        formLayout.addComponent(buttonReferences);
        return formLayout;
    }

    public Component getFormLayoutUserProperties()
    {
        userPropertyList = character.getUserPropertyList();
        FormLayout formLayout= new FormLayout();
        for(UserProperty userProperty : userPropertyList)
        {
           formLayout.addComponent(new TextField(userProperty.getName(), userProperty.getValue()));
        }

        return formLayout;

    }

    private Table getTraitTable()
    {
        tableTrait= new Table("List of traits");
        tableTrait.setPageLength(3);
        tableTrait.setWidth(100,Unit.PERCENTAGE);
        tableTrait.addContainerProperty("Trait",Trait.class,null);
        for(Trait trait : this.character.getTraitList())
        {
            tableTrait.addItem(new Object[]{trait},trait.getId());
        }

        tableTrait.addActionHandler(new Action.Handler() {
            @Override
            public Action[] getActions(Object target, Object sender) {
                return new Action[]{ACTION_ADD,ACTION_RENAME,ACTION_DELETE};
            }

            @Override
            public void handleAction(Action action, Object sender, Object target) {
                    if(action == ACTION_ADD)
                    {
                        UI.getCurrent().addWindow(new TraitWindow(new Trait()));
                    }
                    else if(action==ACTION_RENAME)
                    {
                        UI.getCurrent().addWindow(new TraitWindow(
                                (Trait)tableTrait.getItem(target).getItemProperty("Trait").getValue()));
                    }
                else if(action== ACTION_DELETE)
                    {
                        tableTrait.removeItem(target);
                    }
            }
        });
        return tableTrait;
    }

    public void setTraitFromTraitWindow(Trait trait)    //TODO si je retire le public, eset ce que cela sera public pour le package et private à l'extérieur? si oui alors faire ainsi pour ne limiter l'accès de cette méthode à TraitWindow
    {
        this.tableTrait.addItem(new Object[]{trait},trait.getId());
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {

        if(event.getButton().equals(buttonSave))
        {
            this.characterWindowPresenter.saveInDB();
        }
        else if(event.getButton().equals(buttonErase))
        {
            this.characterWindowPresenter.eraseFromDB();
        }

        close();

    }

    public Character getCharacter() {
        return character;
    }

    @Override
    public void setHandler(NarrativePresenter narrativePresenter) {
        this.characterWindowPresenter=(CharacterWindowPresenter)narrativePresenter;
    }
}
