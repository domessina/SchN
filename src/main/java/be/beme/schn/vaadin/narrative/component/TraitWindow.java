package be.beme.schn.vaadin.narrative.component;

import be.beme.schn.narrative.object.Trait;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.*;

import java.util.ArrayList;

/**
 * Created by Dorito on 28-03-16.
 */
public class TraitWindow extends Window {

    private TextField textField;

    public TraitWindow(Trait trait)
    {
        super("New Trait");
        configureWindow(trait);
    }

    private void configureWindow(Trait trait)
    {
        setModal(true);
        setClosable(true);
        setResizable(false);
        //setAssistiveRole(WindowRole.ALERTDIALOG);
        addShortcutListener(new ShortcutListener("Close", ShortcutAction.KeyCode.ENTER,null) {
            @Override
            public void handleAction(Object sender, Object target) {

                if(textField.getValue().isEmpty())
                {
                    Notification.show("Must be filled", Notification.Type.ERROR_MESSAGE);
                }

                else
                {
                    ArrayList<Window> arrayList=new ArrayList<>(UI.getCurrent().getWindows());
                    for(Window window: arrayList)
                    {
                        if(window.getId().contains("CharacterWindow"))
                        {
                            if(trait.getId()==0)
                            {
                                trait.setName(textField.getValue());
                                trait.setId(100);
                            }
                            else
                            {
                                trait.setName(textField.getValue());
                            }
                            ((CharacterWindow)window).setTraitFromTraitWindow(trait);
                            close();
                        }
                    }
                }

            }
        });
        if(trait.getId()==0)
        {
            textField= new TextField("Name","");
        }
        else
        {
            textField= new TextField("Name",trait.getName());
        }
        VerticalLayout verticalLayout = new VerticalLayout(textField);
        verticalLayout.setMargin(true);
        setContent(verticalLayout);
    }
}
