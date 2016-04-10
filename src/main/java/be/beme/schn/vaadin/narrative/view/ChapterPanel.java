package be.beme.schn.vaadin.narrative.view;

import be.beme.schn.Constants;
import be.beme.schn.narrative.component.Chapter;
import be.beme.schn.narrative.component.Scene;
import be.beme.schn.vaadin.ImagePanel;
import be.beme.schn.vaadin.narrative.presenter.ChapterPresenter;
import be.beme.schn.vaadin.narrative.presenter.NarrativePresenter;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import com.vaadin.event.Action;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

import java.io.File;

/**
 * Created by Dotista on 08-04-16.
 */
public class ChapterPanel extends AbstractPanelView implements NarrativeView, Button.ClickListener {

    private ChapterPresenter presenter;
    private Chapter chapter;
    private TextField titleTF;
    private Panel pstickers;
    private Panel propertiesPanel;
    private TextArea notes;

    public ChapterPanel(Chapter chapter)
    {
        super("Chapter");
        this.chapter=chapter;
        setHeight(40, Unit.EM);
        setWidth(30, Unit.EM);
        setWrappedContent(buildContent());
    }

    private Layout buildContent()
    {
        VerticalLayout verticalLayout= new VerticalLayout();
        Button addScBTN = new Button("+",this);

        if(this.chapter.getId()==0)
        {
            buttonErase.setVisible(false);
        }

        verticalLayout.addComponent(buildScStickers());
        verticalLayout.addComponent(addScBTN);
        verticalLayout.setComponentAlignment(addScBTN,Alignment.MIDDLE_RIGHT);
        verticalLayout.addComponent(buildProperties());
        verticalLayout.setExpandRatio(pstickers,9);
        verticalLayout.setExpandRatio(addScBTN,1);
        verticalLayout.setExpandRatio(propertiesPanel,10);
        verticalLayout.setSizeFull();
        verticalLayout.setSpacing(true);

        return verticalLayout;
    }


    private Panel buildScStickers()
    {
        pstickers = new Panel();
        int row_column=(int)Math.ceil(Math.sqrt(this.chapter.getScenes().size()));
        GridLayout gLayout= new GridLayout(row_column,row_column);
//        gLayout.setSizeFull(); //on ne fait pas setSpacign sinon on perd de la place pour les images. les bords arondit seront suffidants
        gLayout.setWidth(100,Unit.PERCENTAGE);
        for(Scene s:this.chapter.getScenes())
        {
            Image image= new Image(null,new FileResource(new File(Constants.BASE_DIR+"Users\\1\\Diagrams\\"+this.chapter.getDiagramId()+"\\Scenes\\"+s.getPicture())));
            image.setWidth(100,Unit.PERCENTAGE);
//            image.setStyleName("round-corner");
            image.setId("Sc"+String.valueOf(s.getId()));        //rajotuer Sc devant parce que vaadin nomme déjà les id par défaut avec des nombres. Faut pas que l'id d'une scène soit égal à l'id d'un autre compoenent Vaadin
            image.addListener(new MouseEvents.ClickListener() {
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
            });
            gLayout.addComponent(image);
        }

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
        return propertiesPanel;
    }

    private Component buildUserProperties()
    {
        return new Button("Yolo");
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {

        if(event.getButton().equals(this.buttonSave))
        {
            presenter.save();
        }
        else if(event.getButton().equals(this.buttonErase))
        {
            presenter.erase();
        }
    }

    @Override
    public void setHandler(NarrativePresenter narrativePresenter) {
        this.presenter=(ChapterPresenter)narrativePresenter;
    }

    public Chapter getChapter() {
        return chapter;
    }


}
