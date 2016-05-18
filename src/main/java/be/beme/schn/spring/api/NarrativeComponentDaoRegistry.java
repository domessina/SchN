package be.beme.schn.spring.api;

import be.beme.schn.persistence.dao.ChapterDao;
import be.beme.schn.persistence.dao.CharacterDao;
import be.beme.schn.persistence.dao.NarrativeComponentDao;
import be.beme.schn.persistence.dao.SceneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by Dotista on 18-05-16.
 */
@Component
public class NarrativeComponentDaoRegistry {

    private HashMap<String,NarrativeComponentDao> registry;

    @Autowired
    public NarrativeComponentDaoRegistry(ChapterDao chapterDao,CharacterDao characterDao,SceneDao sceneDao)
    {
        registry=new HashMap(3);
        registry.put("chapter",  chapterDao);
        registry.put("scene",  sceneDao);
        registry.put("character",  characterDao);
    }

    public NarrativeComponentDao getDao(String type)
    {
       return registry.get(type);
    }
}
