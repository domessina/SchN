package be.beme.schn.vaadin.narrative.component;

import be.beme.schn.narrative.object.Character;
import be.beme.schn.narrative.object.Trait;
import be.beme.schn.narrative.object.UserProperty;
import be.beme.schn.vaadin.ImageReceiver;
import com.vaadin.event.Action;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.File;
import java.util.List;

/**
 * Created by Dorito on 26-03-16.
 */
public class CharacterWindow extends Window implements Button.ClickListener{

    private Panel imagePanel;
    private Panel propertiesPanel;
    private Table tableTrait;
    private Image image;
    private List<UserProperty> userPropertyList;
    private Character character;
    private Button buttonReferences;
    private Button buttonSave;
    private Button buttonErase;
    private final static Action ACTION_ADD= new Action("Add");
    private final static Action ACTION_DELETE=new Action("Delete");
    private final static Action ACTION_RENAME=new Action("Rename");
    private Trait traitFromTraitWindow;

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
        verticalLayout.setSpacing(true);
        verticalLayout.setMargin(true);
        verticalLayout.setSizeFull();

        Upload upload= new Upload("",new ImageReceiver());
        upload.setButtonCaption("☑");
        upload.addSucceededListener(event -> {

            //changing the imageSource refresh the image in the browser
           this.image.setSource(((ImageReceiver)upload.getReceiver()).getFileResource());
        });


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

        verticalLayout.addComponent(getImagePanel());
       verticalLayout.setExpandRatio(imagePanel,3.5f);
        verticalLayout.addComponent(upload);
        verticalLayout.addComponent(getPropertiesPanel());
       verticalLayout.setExpandRatio(propertiesPanel,6.5f);
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
        TextField type = new TextField("Type");
        type.setValue(this.character.getType());
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


    private Component getImagePanel()
    {
        imagePanel = new Panel();
        imagePanel.setSizeFull();
        imagePanel.setScrollLeft(1111110);
        File file = new File("C:\\Users\\Dorito\\TFEImages\\NarrativeScheme\\Users\\Diagrams\\"+this.character.getDiagram_id()+"\\Characters\\"+this.character.getId()+"\\image.jpg");
        Resource resource= new FileResource(file);
        image = new Image(null, resource);
        image.setImmediate(true);
        imagePanel.setContent(image);
        return imagePanel;

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

    }
}
