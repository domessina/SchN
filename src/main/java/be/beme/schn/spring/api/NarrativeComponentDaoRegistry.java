package be.beme.schn.spring.api;

import be.beme.schn.narrative.component.E_NarrativeComponent;
import be.beme.schn.persistence.dao.*;
import be.beme.schn.spring.api.exception.BadParamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Dotista on 18-05-16.
 */
@Component
public final class NarrativeComponentDaoRegistry {

    private final ConcurrentHashMap<String,NarrativeComponentDao> registry;                                    //an immutable Hashmap( not updated) is threadsafe. but even in read-ony it's maybe not
                                                                                                               //immutable! (tricky subject in java)
                                                                                                                //CrrntHM doesn't allow null values
    @Autowired
    public NarrativeComponentDaoRegistry(ChapterDao chapterDao,
                                         CharacterDao characterDao,
                                         SceneDao sceneDao,
                                         TraitDao traitDao)
    {
        registry=new ConcurrentHashMap<>(4);                                              //normally it's 3.(0.75*4) but I set 4 for to be sure there will be enough space
        registry.put(E_NarrativeComponent.NC_Chapter.getType(),  chapterDao);
        registry.put(E_NarrativeComponent.NC_Scene.getType(),  sceneDao);
        registry.put(E_NarrativeComponent.NC_Character.getType(),  characterDao);
        registry.put(E_NarrativeComponent.NC_Trait.getType(),  traitDao);
    }

    public NarrativeComponentDao getDao(String type)
    {
       if(registry.get(type)==null){
           throw new BadParamException("Bad narrative component type");
       }
        return registry.get(type);
    }
}
