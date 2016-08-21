package be.beme.schn.vaadin.narrative.view;

import be.beme.schn.Constants;
import be.beme.schn.narrative.component.Diagram;
import be.beme.schn.vaadin.ImageUploadPanel;
import be.beme.schn.vaadin.narrative.NWrapped;
import be.beme.schn.vaadin.narrative.NWrapper;
import be.beme.schn.vaadin.narrative.NWrapperPanel;
import be.beme.schn.vaadin.narrative.presenter.DiagramPresenter;
import be.beme.schn.vaadin.narrative.presenter.NarrativePresenter;
import com.vaadin.server.*;
import com.vaadin.ui.*;
import de.steinwedel.messagebox.MessageBox;
import lombok.Getter;


/**
 * Created by Dotista on 20-08-16.
 */
public class DiagramEditionView extends CustomComponent implements NarrativeView,NWrapped{

    private NWrapperPanel wrapper;
    private DiagramPresenter presenter;
    @Getter
    private Diagram diagram;
    private Button buttonErase;
    private Button buttonSave;
    private TextField titleF;
    private ImageUploadPanel imageUploadPanel;
    public boolean isNewDiagram=false;


    public DiagramEditionView(Diagram diagram){
        this.diagram=diagram;
        setCompositionRoot(buildContent());
    }

    private Component buildContent(){
        VerticalLayout layout= new VerticalLayout();

        titleF=new TextField("Title");
        if(diagram.getTitle()!=null)
            titleF.setValue(diagram.getTitle());

        imageUploadPanel = new ImageUploadPanel((String) VaadinSession.getCurrent().getAttribute("diagramDirectory"),diagram.getPictureId());
        layout.addComponent(titleF);
        layout.addComponent(imageUploadPanel);
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setSizeFull();
        return layout;
    }

    @Override
    public void wrap(NWrapper wrapper) {
        this.wrapper=(NWrapperPanel)wrapper;
        confWrapperBtns();
    }

    private void confWrapperBtns()
    {
        buttonErase=wrapper.getButtonErase();
        buttonSave=wrapper.getButtonSave();
        buttonErase.addClickListener(this);
        buttonSave.addClickListener(this);
        if(diagram.getId()==0)
            buttonErase.setVisible(false);
        wrapper.getButtonSet().setVisible(false);
    }


    @Override
    public void buttonClick(Button.ClickEvent event) {
        if(event.getButton().getCaption().equals("Save")){

            if(saveCheck()){
                diagram.setPictureId(imageUploadPanel.getFileName());
                if(isNewDiagram){
                    diagram=presenter.save();

                    //DiagramEditView always set to imageupload the diagram folder with the id of the
                    //diagram currently displayed. When we want to create a new diagram, we know the id
                    //of the new diagram only when save is pressed. So we must to change the path , for to
                    //indicate the good id
                    imageUploadPanel.setPath(Constants.BASE_DIR+"Users\\"+ diagram.getUser_id()+"\\Diagrams\\"+ diagram.getId()+"\\");
                }
                else{ presenter.update();}

                imageUploadPanel.commit();
                wrapper.closeIfWindow();
            }
        }
        else if(event.getButton().getCaption().equals("Erase")){
            showDeleteDialog();
            wrapper.closeIfWindow();
        }
    }

    public boolean saveCheck(){
        titleF.setComponentError(null);
        diagram.setTitle(titleF.getValue());
        if(titleF.isEmpty()){
            titleF.setComponentError(new UserError(Constants.MSG_REQUIRED_FIELD_INFO, AbstractErrorMessage.ContentMode.TEXT, ErrorMessage.ErrorLevel.INFORMATION));
            return false;
        }
        if(imageUploadPanel.isImageEmpty()){
            imageUploadPanel.setComponentError(new UserError(Constants.MSG_REQUIRED_FIELD_INFO,AbstractErrorMessage.ContentMode.TEXT,ErrorMessage.ErrorLevel.INFORMATION));
            return false;
        }
        return true;
    }


    private void showDeleteDialog(){
        MessageBox.createQuestion().withCaption("WARNING")
                .withMessage(Constants.MSG_DELETE_DIAGRAM)
                .withYesButton(()->{
                    if(presenter.erase())
                        imageUploadPanel.deleteImage();
                    Page.getCurrent().reload();
                })
                .withCancelButton()
                .open();
    }

    @Override
    public NWrapper getWrapper() {
        return this.wrapper;
    }


    @Override
    public void setHandler(NarrativePresenter narrativePresenter) {
        this.presenter=(DiagramPresenter)narrativePresenter;
    }

    public void wrapperClosed() {
        imageUploadPanel.deleteTmpDir();
    }
}
