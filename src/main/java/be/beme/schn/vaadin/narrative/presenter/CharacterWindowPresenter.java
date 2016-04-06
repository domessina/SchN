package be.beme.schn.vaadin.narrative.presenter;

import be.beme.schn.Constants;
import be.beme.schn.narrative.component.Character;
import be.beme.schn.persistence.daoimpl.CharacterDaoImpl;
import be.beme.schn.vaadin.narrative.view.CharacterWindow;
import be.beme.schn.vaadin.narrative.view.NarrativeView;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Dorito on 29-03-16.
 */
@Component
@UIScope //j'ai test c'est bien résinstancé à chaque fois qu'un UI est ouvert ou fermé puis réouvert
public class CharacterWindowPresenter implements WindowPresenter {

    //TODO y a til des variables ou une méthode ou l'autre qui se répètent entre les windowPresenter? Si ou ialors transformer cet interface en classe Abstraite, du coup certaines variables seront protected

    private CharacterWindow characterWindow;
    private Character character;

    @Autowired
    private CharacterDaoImpl characterService;


    @Override
    public Character save() {

        try
        {
            this.character=this.characterWindow.getCharacter();
            int id;
            if(character.getId()==0)
            {
                id=this.characterService.create(
                        character.getDiagram_id(),
                        character.getName(),
                        character.getType(),
                        character.getNote(),
                        character.getPicture());
                this.character.setId(id);
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
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return this.character;
    }

    @Override
    public boolean erase() {

        try
        {

            this.character=this.characterWindow.getCharacter();
            this.characterService.delete(this.character.getId());
            Files.delete(Paths.get(Constants.BASE_DIR+"Users\\1"+"\\Diagrams\\"+String.valueOf(character.getDiagram_id())+"\\Characters\\"+character.getPicture()));
        }
        catch (Exception e)                 //doens't matter if it's SQLException or other.
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void setView(NarrativeView narrativeView) {
        this.characterWindow=(CharacterWindow) narrativeView;
    }

    public CharacterDaoImpl getCharacterService()
    {
        return characterService;
    }
}
