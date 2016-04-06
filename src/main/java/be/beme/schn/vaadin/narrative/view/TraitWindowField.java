package be.beme.schn.vaadin.narrative.view;

import be.beme.schn.narrative.component.Trait;
import be.beme.schn.vaadin.VaadinUtils;
import be.beme.schn.vaadin.narrative.presenter.NarrativePresenter;
import be.beme.schn.vaadin.narrative.presenter.TraitWindowFieldPresenter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.UserError;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Created by Dorito on 28-03-16.
 */
public class TraitWindowField extends Window implements NarrativeView {

    private TextField textField;


    private TraitWindowFieldPresenter traitPresenter;
    private Trait trait;

    public TraitWindowField(Trait trait)
    {
        super("New Trait");
        this.trait=trait;
        configureWindow();
    }


    private void configureWindow()
    {
        setModal(true);
        setClosable(true);
        setResizable(false);


        textField= new TextField("Name",trait.getName());
        textField.setNullRepresentation("");
        textField.focus();
        VerticalLayout verticalLayout = new VerticalLayout(textField);
        verticalLayout.setMargin(true);
        setContent(verticalLayout);

        addShortcutListener(new ShortcutListener("Close", ShortcutAction.KeyCode.ENTER,null) {
            @Override
            public void handleAction(Object sender, Object target) {

                CharacterWindow parentWindow=(CharacterWindow) VaadinUtils.findWindowById("CharacterWindow");
                if(textField.getValue().isEmpty())
                {
                    textField.setComponentError(new UserError("Must be filled",null, ErrorMessage.ErrorLevel.INFORMATION));
                }

                else
                {//si trait =0 alors c'est un insert
                    //si trait!=0 alors upadte
                   trait.setName(textField.getValue());
                //   parentWindow.addTraitFromTraitWindow(trait);
                    close();
                }
            }
        });

    }

    public Trait getTrait()
    {
        return this.trait;
    }

    @Override
    public void setHandler(NarrativePresenter narrativePresenter) {
        this.traitPresenter=(TraitWindowFieldPresenter)narrativePresenter;
    }
}
