package be.beme.schn.vaadin.narrative.presenter;

import be.beme.schn.narrative.component.Character;
import be.beme.schn.persistence.daoimpl.CharacterDaoImpl;
import be.beme.schn.vaadin.narrative.view.CharacterWindow;
import be.beme.schn.vaadin.narrative.view.NarrativeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Dorito on 29-03-16.
 */
@Component
public class CharacterWindowPresenter implements WindowPresenter {

    //TODO y a til des variables ou une méthode ou l'autre qui se répètent entre les windowPresenter? Si ou ialors transformer cet interface en classe Abstraite, du coup certaines variables seront protected

    private CharacterWindow characterWindow;
    private Character character;

    @Autowired
    private CharacterDaoImpl characterService;

    @Override
    public void saveInDB() {

        this.character=this.characterWindow.getCharacter();
        if(character.getId()==0)
        {
            this.characterService.create(
                    character.getDiagram_id(),
                    character.getName(),
                    character.getType(),
                    character.getNote(),
                    character.getPicture());
        }
        else{

            this.characterService.update(new Object[]{
                    character.getName(),
                    character.getType(),
                    character.getNote(),
                    character.getPicture(),
                    character.getId()});
        }

    }

    @Override
    public void eraseFromDB() {

        this.character=this.characterWindow.getCharacter();
        this.characterService.delete(this.character.getId());
    }

    @Override
    public void setView(NarrativeView narrativeView) {
        this.characterWindow=(CharacterWindow) narrativeView;
    }
}
