package be.beme.schn.vaadin;

import be.beme.schn.dao.DiagramDao;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Dorito on 17-03-16.
 */
@Theme("valo")
@SpringUI
public class MyVaadinUI extends UI {

    @Autowired
    DiagramDao diagramService;

    @Override
    protected void init(VaadinRequest vaadinRequest) {



        Button button= new Button("here victory!");
        button.addClickListener(event ->
        diagramService.createDiagram(1,3));

        setContent(button);
    }
}