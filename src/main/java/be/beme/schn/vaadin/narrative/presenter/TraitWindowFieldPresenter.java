package be.beme.schn.vaadin.narrative.presenter;

import be.beme.schn.narrative.component.Trait;
import be.beme.schn.persistence.daoimpl.TraitDaoImpl;
import be.beme.schn.vaadin.narrative.view.NarrativeView;
import be.beme.schn.vaadin.narrative.view.TraitWindowField;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Dorito on 01-04-16.
 */
@Component
@UIScope
public class TraitWindowFieldPresenter implements WindowPresenter {

   @Autowired
    TraitDaoImpl traitService;

    private TraitWindowField traitView;
    private Trait trait;


    @Override
    public void setView(NarrativeView narrativeView) {
        this.traitView=(TraitWindowField)narrativeView;
    }

    @Override
    public Trait save() {
        this.trait=this.traitView.getTrait();
        try
        {
           if(trait.getId()==0)
           {
               int id= traitService.create(trait.getCharacterId(),trait.getName());
               this.trait.setId(id);
           }
            else
           {
               traitService.update(new Object[]{trait.getCharacterId(),trait.getName(),trait.getId()});
           }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return this.trait;
    }

    @Override
    public boolean erase() {

        try
        {
            this.traitService.delete(this.trait.getId());
        }
        catch (Exception e)
        {
         e.printStackTrace();
            return false;
        }

        return false;
    }

   public void setCurrentTrait(Trait trait){
       this.trait=trait;
   }


    public TraitWindowField getTraitView()
    {
        return this.traitView;
    }
}
