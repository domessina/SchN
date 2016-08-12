package be.beme.schn.narrative.component;

import be.beme.schn.persistence.dao.NarrativeComponentDao;
import be.beme.schn.spring.api.NComponentBadTypeInterceptor;
import be.beme.schn.spring.api.NarrativeComponentDaoRegistry;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Dorito on 22-03-16.
 */
@Getter
@Setter
public class Element extends  NarrativeComponent {

    private String note;
    private String name;
    private int diagramId;


}


