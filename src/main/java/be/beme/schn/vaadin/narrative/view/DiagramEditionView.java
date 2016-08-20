package be.beme.schn.vaadin.narrative.view;

import be.beme.schn.Constants;
import be.beme.schn.narrative.component.Diagram;
import be.beme.schn.vaadin.narrative.NWrapped;
import be.beme.schn.vaadin.narrative.NWrapper;
import be.beme.schn.vaadin.narrative.NWrapperPanel;
import be.beme.schn.vaadin.narrative.presenter.DiagramPresenter;
import be.beme.schn.vaadin.narrative.presenter.NarrativePresenter;
import com.vaadin.server.AbstractErrorMessage;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import de.steinwedel.messagebox.MessageBox;
import lombok.Getter;


/**
 * Created by Dotista on 20-08-16.
 */
public class DiagramEditionView extends CustomComponent implements NarrativeView,NWrapped {

    private NWrapperPanel wrapper;
    private DiagramPresenter presenter;
    @Getter
    private Diagram diagram;
    private Button buttonErase;
    private Button buttonSave;
    private TextField titleF;
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
        layout.addComponent(titleF);
        layout.setMargin(true);
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
                if(isNewDiagram)
                   diagram=presenter.save();
                else
                    presenter.update();
            }
        }
        else if(event.getButton().getCaption().equals("Erase")){
            showDeleteDialog();
        }
        wrapper.closeIfWindow();
    }

    public boolean saveCheck(){
        titleF.setComponentError(null);
        diagram.setTitle(titleF.getValue());
        if(titleF.isEmpty()){
            titleF.setComponentError(new UserError("Required fields not filled", AbstractErrorMessage.ContentMode.TEXT, ErrorMessage.ErrorLevel.INFORMATION));
            return false;
        }
        return true;
    }


    private void showDeleteDialog(){
        MessageBox.createQuestion().withCaption("WARNING")
                .withMessage(Constants.WINDOW_DELETE_DIAGRAM_MSG)
                .withYesButton(()->{
                    presenter.erase();
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

}
