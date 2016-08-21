package be.beme.schn.vaadin.narrative.view;

import be.beme.schn.Constants;
import be.beme.schn.narrative.component.Chapter;
import be.beme.schn.vaadin.crud.CrudListener;
import be.beme.schn.vaadin.crud.CrudNotifier;
import be.beme.schn.vaadin.narrative.ChapterPVLayout;
import be.beme.schn.vaadin.narrative.NWrapped;
import be.beme.schn.vaadin.narrative.NWrapper;
import be.beme.schn.vaadin.narrative.NWrapperPanel;
import be.beme.schn.vaadin.narrative.presenter.ChapterPresenter;
import be.beme.schn.vaadin.narrative.presenter.NarrativePresenter;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

import java.util.ArrayList;

/**
 * Created by Dotista on 08-04-16.
 */
public class ChapterView extends CustomComponent implements NarrativeView, MouseEvents.ClickListener, NWrapped , CrudNotifier<Chapter>, Window.CloseListener {

    private ChapterPresenter presenter;
    private Chapter chapter;
    private TextField titleTF;
    private Panel propertiesPanel;
    private TextArea notes;
    private boolean settingsMode;
    private Button buttonErase;
    private Button buttonSave;
    private Button buttonSet;
    private Button buttonLeft;
    private Button buttonRight;
    private HorizontalLayout buttonHLayout;
    private NWrapperPanel wrapper;
    private ArrayList<CrudListener> listeners;

    public ChapterView(Chapter chapter) {
        this.chapter = chapter;
        listeners = new ArrayList<>();
        setSizeFull();
        setCompositionRoot(buildContent());
    }

    @Override
    public void wrap(NWrapper wrapper) {
        this.wrapper = (NWrapperPanel) wrapper;
        confWrapperBtns();

        if (this.chapter.getId() == 0) {
            toggleSettings();
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
        buttonErase.setVisible(false);
        buttonSave.setVisible(false);
    }

    private Layout buildContent() {
        VerticalLayout verticalLayout = new VerticalLayout();


//        verticalLayout.addComponent(buildScStickers());
        verticalLayout.addComponent(buildMiddleButtons());
        verticalLayout.addComponent(buildProperties());
//        verticalLayout.setExpandRatio(pstickers,9);
        verticalLayout.setExpandRatio(buttonHLayout, 1);
        verticalLayout.setExpandRatio(propertiesPanel, 10);
        verticalLayout.setSizeFull();
        verticalLayout.setSpacing(true);

        return verticalLayout;
    }


    private Layout buildMiddleButtons() {
        buttonHLayout = new HorizontalLayout();

        buttonLeft = new Button(FontAwesome.ARROW_UP);
        buttonLeft.addClickListener(event1 -> changePosition(-1));

        buttonRight = new Button(FontAwesome.ARROW_DOWN);
        buttonRight.addClickListener(event1 -> changePosition(1));


        buttonHLayout.addComponent(buttonLeft);
        buttonHLayout.addComponent(buttonRight);
        buttonHLayout.setComponentAlignment(buttonLeft, Alignment.MIDDLE_LEFT);
        buttonHLayout.setComponentAlignment(buttonRight, Alignment.MIDDLE_RIGHT);
        buttonHLayout.setSizeFull();

        return buttonHLayout;
    }


    private Panel buildProperties() {

        propertiesPanel = new Panel();
        FormLayout fLayout = new FormLayout();
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(true);
        titleTF = new TextField("Title", this.chapter.getTitle());
        notes = new TextArea("Notes", this.chapter.getNote());

        fLayout.setSpacing(true);
        titleTF.setNullRepresentation("");
        notes.setNullRepresentation("");
        notes.setWidth(100, Unit.PERCENTAGE);
        notes.setRows(Constants.TEXTAREA_ROWS_CHAPTER);
        fLayout.addComponent(titleTF);
        fLayout.setComponentAlignment(titleTF, Alignment.MIDDLE_CENTER);

        verticalLayout.addComponent(new Label("<h3>Simple Properties</h3>", ContentMode.HTML));
        verticalLayout.addComponent(fLayout);
        verticalLayout.addComponent(notes);
        /*verticalLayout.addComponent(new Label("<h3>User Properties</h3>", ContentMode.HTML));
        verticalLayout.addComponent(buildUserProperties());*/
        propertiesPanel.setContent(verticalLayout);
        propertiesPanel.setSizeFull();
        propertiesPanel.setEnabled(false);
        return propertiesPanel;
    }

    private Component buildUserProperties() {
        return new Button("Yolo");
    }

    private void changePosition(int offset) {


        short position = (short) (chapter.getPosition() + offset);
        int chapter_count = (int) VaadinSession.getCurrent().getAttribute("chapterCountCrrntPhase");
        if ((position != -1) && (position != chapter_count)) {
            this.chapter.setPosition(position);
            presenter.setView(this);
            presenter.save();
            notifyUpdated(chapter);
        }


    }


    @Override
    public void buttonClick(Button.ClickEvent event) {

        this.chapter.setTitle(titleTF.getValue());
        this.chapter.setNote(notes.getValue());
        this.presenter.setView(this);

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
                    Notification.show(Constants.MSG_SYS_ERR,Constants.MSG_REPORT_SENT, Notification.Type.ERROR_MESSAGE);
                    return;
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
                Notification.show(Constants.MSG_SYS_ERR,Constants.MSG_REPORT_SENT, Notification.Type.ERROR_MESSAGE);
                return;
            }
        }
        else if(event.getButton().equals(this.buttonSet))
        {
           toggleSettings();
        }
    }

    private void toggleSettings()
    {
        settingsMode=!settingsMode;
        try
        {
            ((ChapterPVLayout)this.wrapper.getParent()).enableAllChildren(!settingsMode);
        }
        catch (NullPointerException e)
        {
            System.out.println("There is no chapter for this phase yet");
        }
        catch ( ClassCastException e)
        {
            System.out.println("This wrapper is not in a ChapterPVLayout");
        }


        buttonErase.setVisible(settingsMode);
        buttonSave.setVisible(settingsMode);
        propertiesPanel.setEnabled(settingsMode);
        buttonLeft.setEnabled(!settingsMode);
        buttonRight.setEnabled(!settingsMode);


        wrapper.setEnabled(true);
    }


    private boolean verifyFields()
    {
        titleTF.setComponentError(null);
        if(titleTF.isEmpty())
        {
            titleTF.setComponentError(new UserError("Required field not filled"));
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
        System.err.println("si cette méthod est encore utilisée alor il ya un problemem");

    }

    public void setChapterPlace(short newPlace)
    {
        this.chapter.setPosition(newPlace);
        presenter.setView(this);
        presenter.save();
    }


    @Override
    public NWrapper getWrapper() {
        return this.wrapper;
    }

    @Override
    public void addCrudListener(CrudListener<Chapter> listener) {
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

    @Override
    public void windowClose(Window.CloseEvent e) {
      //  updateGridLayout();
    }
}
