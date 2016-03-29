package be.beme.schn.vaadin.narrative.presenter;

import be.beme.schn.narrative.component.Character;
import be.beme.schn.persistence.daoimpl.CharacterDaoImpl;
import be.beme.schn.vaadin.narrative.view.CharacterWindow;
import be.beme.schn.vaadin.narrative.view.NarrativeView;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Dorito on 29-03-16.
 */
public class CharacterWindowPresenter implements WindowPresenter {

    //TODO y a til des variables ou une méthode ou l'autre qui se répètent entre les windowPresenter? Si ou ialors transformer cet interface en classe Abstraite, du coup certaines variables seront protected

    private CharacterWindow characterWindow;
    private Character character;

    @Autowired
    private CharacterDaoImpl characterDao;

    @Override
    public void saveInDB() {

        if(character.getId()==0)
        {
            this.characterDao.create(character.getDiagram_id(),character.getName(),character.getType());
        }
        else{

            this.characterDao.update(new Object[]{character.getName(),
                    character.getType(),
                    character.getNote(),
                    character.getDiagram_id(),
                    character.getPicture(),
                    character.getId()});
        }

    }

    @Override
    public void eraseFromDB() {
            this.characterDao.delete(this.character.getId());
    }

    @Override
    public void setView(NarrativeView narrativeView) {
        this.characterWindow=(CharacterWindow) narrativeView;
        this.character=this.characterWindow.getCharacter();
    }
}
