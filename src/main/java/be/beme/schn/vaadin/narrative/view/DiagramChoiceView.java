package be.beme.schn.vaadin.narrative.view;

import be.beme.schn.FileUtil;
import be.beme.schn.narrative.component.Diagram;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.ui.*;
import lombok.Getter;

import java.util.List;

/**
 * Created by Dorito on 25-03-16.
 */
public final class DiagramChoiceView extends Window implements MouseEvents.ClickListener{


    private List<Diagram> diagramList;
    private GridLayout gridLayout;
    private DiagramSelectListener selectListener;
    private final int WINDOW_WITH_PERCENT=75;

    public DiagramChoiceView(List<Diagram> diagramList)
    {
        super("Choose a narrative diagram");
        setModal(true);
        setResizable(false);
        setDraggable(false);
        setHeight(80,Unit.PERCENTAGE);
        setWidth(WINDOW_WITH_PERCENT,Unit.PERCENTAGE);
        this.diagramList=diagramList;

        //Panel needed for scrolling
       Panel panel= new Panel();
        panel.setSizeUndefined();
        panel.setContent(buildChoices());
        panel.setSizeUndefined();

        setContent(panel);


    }

    private Layout buildChoices()
    {
        int column=5;
        int row=(int)Math.ceil(diagramList.size()/column);
//        int row=(int)Math.ceil(Math.sqrt(diagramList.size()));
        if(row==0)
            row=1;
        gridLayout = new GridLayout(column,row);

        int pageWidth=Page.getCurrent().getBrowserWindowWidth();
        int windowWidth=(pageWidth*75)/100;
        int imgWidth=(windowWidth/column)-5;

        for(Diagram d: diagramList)
        {
            Resource resource= new FileResource(FileUtil.getDiagramPicture(d));
            Image img= new Image();
            img.setSource(resource);
            img.setData(d.getId());
            img.addClickListener(this);
            img.setWidth(imgWidth,Unit.PIXELS);
            img.setStyleName("enlarge-hover",true);
            gridLayout.addComponent(img);
        }
        return  gridLayout;

    }

    @Override
    public void click(MouseEvents.ClickEvent event) {
        Image img=((Image)event.getComponent());
        this.close();
        if(selectListener!=null)
            selectListener.onDiagramSelected((int)img.getData());
    }

    public void setDiagramSelectListener(DiagramSelectListener listener){
        this.selectListener=listener;
    }


    public interface DiagramSelectListener{
        void onDiagramSelected(int diagramId);
    }


    
}
