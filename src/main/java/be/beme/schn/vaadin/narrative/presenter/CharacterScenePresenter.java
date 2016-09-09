package be.beme.schn.vaadin.narrative.presenter;


import be.beme.schn.narrative.component.Trait;
import be.beme.schn.persistence.dao.CharacterDao;
import be.beme.schn.persistence.dao.CharacterSceneDao;
import be.beme.schn.persistence.dao.TraitDao;
import be.beme.schn.persistence.dao.TraitSceneDao;
import be.beme.schn.vaadin.narrative.view.CharacterSceneView;
import be.beme.schn.vaadin.narrative.view.NarrativeView;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Dotista on 19-04-16.
 */
@Component
@UIScope
public class CharacterScenePresenter implements NarrativePresenter {



    @Autowired
    private CharacterSceneDao chScDao;

    @Autowired
    private TraitSceneDao traitSceneDao;

    @Autowired
    private TraitDao traitService;

    @Autowired
    private CharacterDao charDao;

/*    @Autowired
    private LinkDao linkDao;*/

    private CharacterSceneView view;



    @Override
    public void setView(NarrativeView narrativeView) {
        this.view=(CharacterSceneView)narrativeView;
    }

    @Override
    public CharacterSceneDao getDaoService() {
        return chScDao;
    }

    public boolean save(){
        try{
            int sceneId= this.view.getSceneId();

            for(Trait t:this.view.getTraitsChallenged())
            {
                try{
                    traitSceneDao.create(t.getId(),sceneId);
                }
                catch (DuplicateKeyException e)
                {
                    System.out.println("Trait "+t.getId() +" in Scene "+sceneId+" already exists");
                }
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean delete()
    {
        try{
            int sceneId= this.view.getSceneId();

            for(Trait t:this.view.getTraitsChallengedToDelete())
            {
                if(t!=null)
                {
                    traitSceneDao.delete(t.getId(), sceneId);

                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /*public boolean saveLink(){
        try{
            int sceneId= this.view.getSceneId();

            for(Character t:this.view.getLinkList())
            {
                try{
//                    linkDao.createLink(t.getFromCharacterId(),t.getToCharacterId(),sceneId);
                }
                catch (DuplicateKeyException e)
                {
                    System.out.println("Trait "+t.getId() +" in Scene "+sceneId+" already exists");
                }
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }*/

    /*public boolean deleteLink()
    {
       *//* try{
            int sceneId= this.view.getSceneId();

            for(Link t:this.view.getLinkToDelete())
            {
                if(t!=null)
                {
                    linkDao.delete(t.getId());

                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }*//*
        return false;
    }

    public List<Link> getLinksByScAndCh(int characterId, int sceneId)
    {
        return linkDao.getLinksBy(characterId,sceneId);
    }*/


    public List<Trait> getTraitsByScene(int sceneId)
    {
        return traitSceneDao.getTraitByScene(sceneId);
    }


    public void addCharacterInScene(int characterId, int sceneId)
    {
       try{
           int diagramId=charDao.getNComponent(characterId).getDiagram_id();
                   //TODO A FAIRE AVANT D UTILISER : retirer la colonne diagramId de characterScene. ainsi que des méthodes characterSceneDao
           chScDao.addCharacterInScene(diagramId,characterId,sceneId);
       }
       catch (DuplicateKeyException e)
       {
           System.out.println("character "+characterId+" and "+sceneId+" are already associated");
       }
       catch(Exception e)
       {
           e.printStackTrace();
       }
    }

    public boolean removeCharacterFromScene(int characterId, int sceneId)
    {
        try{
            chScDao.removeCharacterFromScene(characterId,sceneId);
        }
                catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Trait> getTraitsByCharacter(int id)
    {
        return    traitService.getTraitsByCharacter(id);
    }


}
