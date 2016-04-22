package be.beme.schn.vaadin.narrative.presenter;

import be.beme.schn.narrative.component.Trait;
import be.beme.schn.persistence.Dao;
import be.beme.schn.persistence.dao.*;
import be.beme.schn.vaadin.narrative.view.CharacterSceneView;
import be.beme.schn.vaadin.narrative.view.NarrativeView;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Dotista on 19-04-16.
 */
@Component
@UIScope
public class CharacterScenePresenter implements NarrativePresenter {




    @Autowired
    private CharacterDao characterDao;

    @Autowired
    private TraitDao traitDao;

    @Autowired
    private TraitSceneDao traitSceneDao;

    @Autowired
    private LinkDao linkDao;

    private CharacterSceneView view;


    @Override
    public void setView(NarrativeView narrativeView) {
        this.view=(CharacterSceneView)narrativeView;
    }

    @Override
    public Dao getDaoService() {
        return null;
    }

    public List<Trait> getTraitsByCharacter(int id)
    {
      return    traitDao.getTraitsByCharacter(id);
    }

    public List<Trait> getTraitsByScene(int sceneId)
    {
        return traitSceneDao.getTraitByScene(sceneId);
    }


}
