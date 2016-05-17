package be.beme.schn.vaadin.narrative.presenter;

import be.beme.schn.narrative.component.Trait;
import be.beme.schn.persistence.dao.TraitDao;
import be.beme.schn.persistence.daoimpl.TraitDaoImpl;
import be.beme.schn.vaadin.CrudPresenter;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Dorito on 01-04-16.
 */
@Component
@UIScope
public class TraitCrudPresenter implements CrudPresenter<Trait> {

    @Autowired
    TraitDao traitService;

    @Override
    public boolean create(Trait trait) {

        try {
            traitService.create(trait);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean update(Trait trait) {
        try {
            traitService.update(trait);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public boolean delete(Trait trait) {

        try {
            this.traitService.delete(trait.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public TraitDao getTraitService()
    {
        return traitService;
    }

    public boolean deleteAllTraitsByCharacter(int characterId) {
        try {
            traitService.deleteAllTraitsByCharacter(characterId);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
