package be.beme.schn.vaadin.narrative.view;

import be.beme.schn.Constants;
import be.beme.schn.narrative.component.Scene;
import be.beme.schn.vaadin.crud.CrudListener;
import be.beme.schn.vaadin.crud.CrudNotifier;
import be.beme.schn.vaadin.ImageUploadPanel;
import be.beme.schn.vaadin.narrative.NWrapped;
import be.beme.schn.vaadin.narrative.NWrapper;
import be.beme.schn.vaadin.narrative.NWrapperPanel;
import be.beme.schn.vaadin.narrative.presenter.NarrativePresenter;
import be.beme.schn.vaadin.narrative.presenter.ScenePresenter;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

import java.util.ArrayList;

public final class SceneView extends CustomComponent implements NWrapped, NarrativeView, CrudNotifier<Scene>, Window.CloseListener
{

    private ImageUploadPanel imageUploadPanel;
    private TextField tagField;
    private TextArea notes;
    private Panel propertiesPanel;
    private NWrapperPanel wrapper;
    private Scene scene;
    private ArrayList<CrudListener<Scene>> listeners;
    private Button buttonErase;
    private Button buttonSave;
    private Button buttonSet;
    private ScenePresenter presenter;
    private Panel rootPanel;

    public SceneView(Scene scene)
    {
        this.scene=scene;
        this.listeners=new ArrayList<>();
    }

    @Override
    public void wrap(NWrapper wrapper) {

        this.wrapper=(NWrapperPanel)wrapper;
        setSizeFull();
        this.setCompositionRoot(this.buildContent());
        confWrapperBtns();
    }
    private void confWrapperBtns()
    {
        buttonErase=wrapper.getButtonErase();
        buttonSave=wrapper.getButtonSave();
        buttonSet=wrapper.getButtonSet();
        buttonErase.addClickListener(this);
        buttonSave.addClickListener(this);
        buttonSet.addClickListener(this);
        if(this.scene.getId()==0)
        {
            buttonErase.setVisible(false);
            buttonSet.setVisible(false);
        }
        else
        {
            toggleButtons();
        }
    }

    private void toggleButtons()
    {
        buttonErase.setVisible(!buttonErase.isVisible());
        buttonSave.setVisible(!buttonSave.isVisible());
    }

    private Component buildContent()
    {
        VerticalLayout verticalLayout= new VerticalLayout();

        imageUploadPanel= new ImageUploadPanel(VaadinSession.getCurrent().getAttribute("sceneDirectory").toString() ,this.scene.getPicture());


        verticalLayout.addComponent(imageUploadPanel);
        verticalLayout.addComponent(this.buildProperties());
        verticalLayout.setExpandRatio(imageUploadPanel,4);
        verticalLayout.setExpandRatio(propertiesPanel,6);

        rootPanel= new Panel();
        rootPanel.setSizeFull();
        rootPanel.setContent(verticalLayout);

        return rootPanel;
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
        notes.setRows(Constants.TEXTAREA_ROWS_SCENE);
        fLayout.addComponent(tagField);
        fLayout.setComponentAlignment(tagField,Alignment.MIDDLE_CENTER);

        verticalLayout.addComponent(new Label("<h3>Simple Properties</h3>", ContentMode.HTML));
        verticalLayout.addComponent(fLayout);
        verticalLayout.addComponent(notes);
       /* verticalLayout.addComponent(new Label("<h3>User Properties</h3>", ContentMode.HTML));
        verticalLayout.addComponent(buildUserProperties());*/
        propertiesPanel.setContent(verticalLayout);
        propertiesPanel.setSizeFull();
        return propertiesPanel;
    }

    private Component buildUserProperties(){
        return new Button("Yolo");
    }
    @Override
    public NWrapper getWrapper() {
        return null;
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {

        Button btn=event.getButton();
        if(btn.equals(this.buttonSave))
        {
         if(verifyFields())
         {
             saveUpdate();
             toggleButtons();
         }
        }
        else if(btn.equals(this.buttonErase))
        {
            erase();
        }
        else if(btn.equals(this.buttonSet))
        {
            toggleButtons();
            rootPanel.setEnabled(!rootPanel.isEnabled());
        }
    }

    private void saveUpdate()
    {
        boolean isNewScene=(scene.getId()==0);

        this.scene.setTag(tagField.getValue());
        this.scene.setPicture(imageUploadPanel.getFileName());
        this.scene.setNote(notes.getValue());

        if(isNewScene){
            this.scene.setDiagramId((int)VaadinSession.getCurrent().getAttribute("diagramId"));
        }

        presenter.setView(this);
        this.scene=presenter.save();

        if(scene!=null)
        {
            imageUploadPanel.commit();
            wrapper.closeIfWindow();
        }
        else
        {
            Notification.show(Constants.MSG_SYS_ERR,Constants.MSG_REPORT_SENT, Notification.Type.ERROR_MESSAGE);
            return;
        }

        if(isNewScene)
        {
            notifyCreated(this.scene);
        }
        else
        {
            notifyUpdated(this.scene);

        }
        imageUploadPanel.deleteTmpDir();
    }

    private void erase()
    {
//        imageUploadPanel.deleteImage();
        presenter.setView(this);
        this.scene.setPlace(presenter.getPlace(scene.getId()));
        if(presenter.erase())
        {
            imageUploadPanel.deleteTmpDir();

            notifyDeleted(this.scene);
        }
        else
        {
            Notification.show(Constants.MSG_SYS_ERR,Constants.MSG_REPORT_SENT, Notification.Type.ERROR_MESSAGE);
            return ;
        }
    }
    private boolean verifyFields()
    {
      tagField.setComponentError(null);
        if(tagField.isEmpty())
        {
            tagField.setComponentError(new UserError("Required field not filled"));     //TODO créer constante pour requiered field not filled
            return false;
        }

        return true;
    }

    @Override
    public void setHandler(NarrativePresenter narrativePresenter) {
        this.presenter=(ScenePresenter)narrativePresenter;
    }

    @Override
    public void addCrudListener(CrudListener<Scene> listener) {
        listeners.add(listener);
    }

    @Override
    public void notifyCreated(Scene target) {
        for(CrudListener listener:listeners)
        {
            listener.created(target);
        }
    }

    @Override
    public void notifyUpdated(Scene target) {
        for(CrudListener listener:listeners)
        {
            listener.updated(target);
        }
    }

    @Override
    public void notifyDeleted(Scene target) {
        for(CrudListener listener:listeners)
        {
            listener.deleted(target);
        }
    }

    public Scene getScene()
    {
        return this.scene;
    }

    @Override
    public void windowClose(Window.CloseEvent e) {
        imageUploadPanel.deleteTmpDir();
    }
}
