package be.beme.schn.vaadin.narrative.view;

import be.beme.schn.Constants;
import be.beme.schn.narrative.component.Chapter;
import be.beme.schn.narrative.component.Scene;
import be.beme.schn.vaadin.CrudListener;
import be.beme.schn.vaadin.CrudNotifier;
import be.beme.schn.vaadin.narrative.NWrapped;
import be.beme.schn.vaadin.narrative.NWrapper;
import be.beme.schn.vaadin.narrative.ChapterPHLayout;
import be.beme.schn.vaadin.narrative.NWrapperPanel;
import be.beme.schn.vaadin.narrative.presenter.ChapterPresenter;
import be.beme.schn.vaadin.narrative.presenter.NarrativePresenter;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.FileResource;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Dotista on 08-04-16.
 */
public class ChapterView extends CustomComponent implements NarrativeView, MouseEvents.ClickListener, NWrapped , CrudNotifier<Chapter>{

    private ChapterPresenter presenter;
    private Chapter chapter;
    private TextField titleTF;
    private Panel pstickers;
    private Panel propertiesPanel;
    private TextArea notes;
    private boolean settingsMode;
    private GridLayout gLayout;
    private Button buttonAddSc;
    private Button buttonErase;
    private Button buttonSave;
    private Button buttonSet;
    private NWrapperPanel wrapper;
    private ArrayList<CrudListener> listeners;

    public ChapterView(Chapter chapter)
    {
        this.chapter=chapter;
        listeners= new ArrayList<>();
        setHeight(100, Unit.PERCENTAGE);
        setWidth(30, Unit.EM);
        setCompositionRoot(buildContent());
    }


    public void wrap(NWrapper wrapper)
    {
        this.wrapper=(NWrapperPanel)wrapper;
        this.wrapper.setId(String.valueOf(this.chapter.getId()));
        confWrapperBtns();

        if(this.chapter.getId()==0)
        {
            toggleSettings();
            buttonErase.setVisible(false);
            buttonSet.setVisible(false);
        }

    }
    private void confWrapperBtns()
    {
        buttonErase=wrapper.getButtonErase();
        buttonSave=wrapper.getButtonSave();
        buttonSet=wrapper.getButtonSet();
        buttonErase.addClickListener(this);
        buttonSave.addClickListener(this);
        buttonSet.addClickListener(this);
        buttonErase.setVisible(false);
        buttonSave.setVisible(false);
    }

    private Layout buildContent()
    {
        VerticalLayout verticalLayout= new VerticalLayout();
        buttonAddSc = new Button("+",this);

        verticalLayout.addComponent(buildScStickers());
        verticalLayout.addComponent(buttonAddSc);
        verticalLayout.setComponentAlignment(buttonAddSc,Alignment.MIDDLE_RIGHT);
        verticalLayout.addComponent(buildProperties());
        verticalLayout.setExpandRatio(pstickers,9);
        verticalLayout.setExpandRatio(buttonAddSc,1);
        verticalLayout.setExpandRatio(propertiesPanel,10);
        verticalLayout.setSizeFull();
        verticalLayout.setSpacing(true);

        return verticalLayout;
    }



    private Panel buildScStickers()
    {
        pstickers = new Panel();
        gLayout= new GridLayout();
        updateGridLayout();
//        gLayout.setSizeFull(); //on ne fait pas setSpacign sinon on perd de la place pour les images. les bords arondit seront suffidants
        gLayout.setWidth(100,Unit.PERCENTAGE);


        pstickers.setSizeFull();
        pstickers.setContent(gLayout);

       return pstickers;
    }


    private Panel buildProperties()
    {

       propertiesPanel= new Panel();
        FormLayout fLayout= new FormLayout();
        VerticalLayout verticalLayout= new VerticalLayout();
        verticalLayout.setMargin(true);
        titleTF= new TextField("Title",this.chapter.getTitle());
        notes= new TextArea("Notes",this.chapter.getNote());

        fLayout.setSpacing(true);
        titleTF.setNullRepresentation("");
        notes.setNullRepresentation("");
        notes.setWidth(100,Unit.PERCENTAGE);
        fLayout.addComponent(titleTF);
        fLayout.setComponentAlignment(titleTF,Alignment.MIDDLE_CENTER);

        verticalLayout.addComponent(new Label("<h3>Simple Properties</h3>", ContentMode.HTML));
        verticalLayout.addComponent(fLayout);
        verticalLayout.addComponent(notes);
        verticalLayout.addComponent(new Label("<h3>User Properties</h3>", ContentMode.HTML));
        verticalLayout.addComponent(buildUserProperties());
        propertiesPanel.setContent(verticalLayout);
        propertiesPanel.setSizeFull();
        propertiesPanel.setEnabled(false);
        return propertiesPanel;
    }

    private Component buildUserProperties()
    {
        return new Button("Yolo");
    }


    public void addScene(Scene scene)
    {
        this.chapter.getScenes().add(scene);
        updateGridLayout();

    }
    private void updateGridLayout()
    {
        try {
            int row_column = (int) Math.ceil(Math.sqrt(this.chapter.getScenes().size()));
            gLayout.setRows(row_column);
            gLayout.setColumns(row_column);

            for(Scene s : this.chapter.getScenes())
            {
                Image image= new Image(null,new FileResource(new File(Constants.BASE_DIR+"Users\\1\\Diagrams\\"+this.chapter.getDiagramId()+"\\Scenes\\"+s.getPicture())));
                image.setWidth(100,Unit.PERCENTAGE);
//            image.setStyleName("round-corner");
                image.setId("Sc"+String.valueOf(s.getId()));        //rajotuer Sc devant parce que vaadin nomme déjà les id par défaut avec des nombres. Faut pas que l'id d'une scène soit égal à l'id d'un autre compoenent Vaadin
                image.addListener(this);
                gLayout.addComponent(image);
            }

        }
        catch (NullPointerException e)
        {
            System.out.println("This chapter has no scenes");
        }
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {

        this.chapter.setTitle(titleTF.getValue());
        this.chapter.setNote(notes.getValue());

        if(event.getButton().equals(this.buttonSave))
        {
            if(verifyFields())
            {
                this.chapter=presenter.save();
                if(this.chapter!=null)
                {
                    if(buttonErase.isVisible())
                    {
                        notifyUpdated(this.chapter);
                        toggleSettings();
                    }
                    else
                    {
                        notifyCreated(this.chapter);
                    }
                }
                else
                {
                    Notification.show(Constants.SYS_ERR,Constants.REPORT_SENT, Notification.Type.ERROR_MESSAGE);
                }
            }

        }
        else if(event.getButton().equals(this.buttonErase))
        {
            boolean eraseOk;
            eraseOk=presenter.erase();

            if (eraseOk)
            {
                toggleSettings();
                notifyDeleted(this.chapter);
            }
            else {
                Notification.show(Constants.SYS_ERR,Constants.REPORT_SENT, Notification.Type.ERROR_MESSAGE);
            }
        }
        else if(event.getButton().equals(this.buttonSet))
        {
           toggleSettings();
           this.presenter.setView(this);

        }
    }

    private void toggleSettings()
    {
        settingsMode=!settingsMode;
        try
        {
            ((ChapterPHLayout)this.wrapper.getParent()).enableAllChildren(!settingsMode);
        }
        catch (NullPointerException e)
        {
            System.out.println("There is no chapter for this phase yet");
        }
        catch ( ClassCastException e)
        {
            System.out.println("This wrapper is not in a ChapterPHLayout");
        }


        buttonErase.setVisible(settingsMode);
        buttonSave.setVisible(settingsMode);
        propertiesPanel.setEnabled(settingsMode);
        pstickers.setEnabled(!settingsMode);
        buttonAddSc.setEnabled(!settingsMode);


        wrapper.setEnabled(true);
    }


    private boolean verifyFields()
    {
        titleTF.setComponentError(null);
        if(titleTF.isEmpty())
        {
            titleTF.setComponentError(new UserError("Requied field not filled"));
            return false;
        }

        return true;
    }

    @Override
    public void setHandler(NarrativePresenter narrativePresenter) {
        this.presenter=(ChapterPresenter)narrativePresenter;
    }

    public Chapter getChapter() {
        return chapter;
    }


    @Override
    public void click(MouseEvents.ClickEvent event) {
        if(event.isDoubleClick())
        {

            Image image=(Image)event.getSource();
            String scId=image.getId().substring(2);
            for(Scene sc : chapter.getScenes())
            {
                if(sc.getId()==Integer.valueOf(scId))
                {
                    // sctarget=sc;
                    Notification.show("ingo",scId, Notification.Type.TRAY_NOTIFICATION);
                }
            }
//                UI.getCurrent().addWindow(new SceneWindow(sctarget));*/
        }
    }


    @Override
    public NWrapper getWrapper() {
        return this.wrapper;
    }

    @Override
    public void addCrudListener(CrudListener listener) {
        listeners.add(listener);
    }

    @Override
    public void notifyCreated(Chapter target)
    {
        for(CrudListener listener:listeners)
        {
            listener.created(target);
        }
    }

    @Override
    public void notifyUpdated(Chapter target)
    {
        for(CrudListener listener:listeners)
        {
            listener.updated(target);
        }
    }

    @Override
    public void notifyDeleted(Chapter target)
    {
        for(CrudListener listener:listeners)
        {
            listener.deleted(target);
        }
    }
}
