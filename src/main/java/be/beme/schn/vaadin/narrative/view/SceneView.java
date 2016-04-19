package be.beme.schn.vaadin.narrative.view;

import be.beme.schn.Constants;
import be.beme.schn.narrative.component.*;
import be.beme.schn.narrative.component.Link;
import be.beme.schn.vaadin.CrudListener;
import be.beme.schn.vaadin.CrudNotifier;
import be.beme.schn.vaadin.ImageUploadPanel;
import be.beme.schn.vaadin.narrative.NWrapped;
import be.beme.schn.vaadin.narrative.NWrapper;
import be.beme.schn.vaadin.narrative.presenter.NarrativePresenter;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

import java.util.ArrayList;

/**
 * Created by Dotista on 18-04-16.
 */
public class SceneView extends CustomComponent implements NarrativeView, NWrapped, CrudNotifier<Scene> {

    private Scene scene;
    private ArrayList<CrudListener> listeners;
    private ImageUploadPanel imageUploadPanel;
    private TextField tagField;
    private TextArea notes;
    private FormLayout formLayout;
    private Panel propertiesPanel;

    public SceneView(Scene scene)
    {
        this.scene=scene;
        listeners= new ArrayList<>();
        setSizeFull();
        setCompositionRoot(buildContent());
    }


    private Component buildContent()
    {
              HorizontalSplitPanel horizontalSplitPanel= new HorizontalSplitPanel(buildCmpntLeft(),buildCmpntRight());
        horizontalSplitPanel.setMaxSplitPosition(35,Unit.PERCENTAGE);
        horizontalSplitPanel.setMinSplitPosition(15,Unit.PERCENTAGE);
        horizontalSplitPanel.setSizeFull();




        return horizontalSplitPanel;
    }

    private Panel buildCmpntLeft()
    {
        VerticalLayout verticalLayout= new VerticalLayout();

        notes = new TextArea("Notes",scene.getNote());
        notes.setNullRepresentation("");
        notes.setWidth(100,Unit.PERCENTAGE);


        imageUploadPanel= new ImageUploadPanel(Constants.BASE_DIR+"Users\\1"+"\\Diagrams\\"+ VaadinSession.getCurrent().getAttribute("diagramId")+"\\Scenes\\" ,this.scene.getPicture());


        verticalLayout.addComponent(imageUploadPanel);
        verticalLayout.addComponent(buildProperties());
        verticalLayout.setExpandRatio(imageUploadPanel,4);
        verticalLayout.setExpandRatio(propertiesPanel,6);

        Panel rootLeft= new Panel();
        rootLeft.setSizeFull();
        rootLeft.setContent(verticalLayout);

        return rootLeft;

    }



    private Panel buildProperties()
    {

        propertiesPanel= new Panel();
        FormLayout fLayout= new FormLayout();
        VerticalLayout verticalLayout= new VerticalLayout();
        verticalLayout.setMargin(true);
        tagField= new TextField("Tag",this.scene.getTag());
        notes= new TextArea("Notes",this.scene.getNote());

        fLayout.setSpacing(true);
        tagField.setNullRepresentation("");
        notes.setNullRepresentation("");
        notes.setWidth(100,Unit.PERCENTAGE);
        fLayout.addComponent(tagField);
        fLayout.setComponentAlignment(tagField,Alignment.MIDDLE_CENTER);

        verticalLayout.addComponent(new Label("<h3>Simple Properties</h3>", ContentMode.HTML));
        verticalLayout.addComponent(fLayout);
        verticalLayout.addComponent(notes);
        verticalLayout.addComponent(new Label("<h3>User Properties</h3>", ContentMode.HTML));
        verticalLayout.addComponent(buildUserProperties());
        propertiesPanel.setContent(verticalLayout);
        propertiesPanel.setSizeFull();
        return propertiesPanel;
    }
    private Component buildCmpntRight()
    {
        VerticalLayout charLayout = new VerticalLayout();
        charLayout.setSizeFull();

        Link link= new Link();
        link.setName("Avec quelq'un");
        charLayout.addComponent(new CharacterScene());
        charLayout.setMargin(true);


        Panel rootRight= new Panel();
        rootRight.setSizeFull();
        rootRight.setContent(charLayout);
        return rootRight;
    }

    private Layout buildCharList()
    {
        return null;
    }

    private Component buildUserProperties(){
       return new Button("Yolo");
    }

    @Override
    public void notifyCreated(Scene target) {

    }

    @Override
    public void notifyUpdated(Scene target) {

    }

    @Override
    public void notifyDeleted(Scene target) {

    }

    @Override
    public void addCrudListener(CrudListener listener) {

    }

    @Override
    public void setHandler(NarrativePresenter narrativePresenter) {

    }

    @Override
    public NWrapper getWrapper() {
        return null;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {

    }
}
