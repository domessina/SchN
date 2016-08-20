package be.beme.schn.vaadin.narrative.view;

import be.beme.schn.narrative.component.Diagram;
import com.vaadin.ui.*;

import java.util.List;

/**
 * Created by Dorito on 25-03-16.
 */
public class DiagramChoiceView extends Window implements Button.ClickListener{


    private List<Diagram> diagramList;
    private GridLayout gridLayout;
    private int diagramId;

    public DiagramChoiceView(List<Diagram> diagramList)
    {
        super("Choose a narrative diagram");
        setModal(true);
        setResizable(false);
        setClosable(false);
        setDraggable(false);
        setHeight(23,Unit.EM);
        setWidth(30,Unit.EM);
        this.diagramList=diagramList;


       Panel panel= new Panel();
        panel.setSizeFull();
        panel.setContent(buildChoices());
        panel.setSizeUndefined();
        VerticalLayout verticalLayout= new VerticalLayout();

        if(diagramList.size()<=16)
        {
            verticalLayout.setSizeFull();
        }
        else
        {
            verticalLayout.setSizeUndefined();
        }
        verticalLayout.setSizeFull();
        verticalLayout.addComponent(panel);
        verticalLayout.setComponentAlignment(panel,Alignment.MIDDLE_CENTER);
        setContent(verticalLayout);


    }

    private Layout buildChoices()
    {
        int row_column=(int)Math.ceil(Math.sqrt(diagramList.size()));

        gridLayout = new GridLayout(row_column,row_column);
        gridLayout.setSizeFull();
        gridLayout.setSpacing(true);
//        gridLayout

        for(Diagram d: diagramList)
        {
            Button btn=new Button(d.getTitle(),this);
            btn.setId(Integer.toString(d.getId()));
            gridLayout.addComponent(btn);
        }
        return  gridLayout;

    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        this.diagramId=Integer.valueOf(event.getButton().getId());
        this.close();
    }

    public int getDiagramId() {
        return diagramId;
    }
}
