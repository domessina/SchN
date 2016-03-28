package be.beme.schn.vaadin.narrative.component;

import be.beme.schn.narrative.object.Diagram;
import com.vaadin.ui.*;

import java.util.List;

/**
 * Created by Dorito on 25-03-16.
 */
public class ChoiceDiagramWindow extends Window implements Button.ClickListener{


    private List<Diagram> diagramList;
    private GridLayout gridLayout;

    public ChoiceDiagramWindow(List<Diagram> diagramList)
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

        for(int i=0;i<diagramList.size();i++)
        {
          gridLayout.addComponent(new Button(diagramList.get(i).getTitle(),this));
        }
        return  gridLayout;

    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        this.close();
    }
}
