package be.beme.schn.spring.api.v1.interceptor;

import be.beme.schn.narrative.component.Diagram;
import be.beme.schn.persistence.daoimpl.synch.DiagramSynchDaoImpl;

/**
 * Created by Dorito on 26-08-16.
 */
public class ConflictResolver {


    public String resolution;
    public String clientAction;
    public String choices;
    private Diagram d;
    private DiagramSynchDaoImpl diagramSynchDao;

    public ConflictResolver(Diagram d, String clientAction)
    {
        this.clientAction=clientAction;
        this.d=d;
    }

    public void resolve(){

        //whether the diagram was already changed on server side since last synch
        if(diagramSynchDao.isDiagramToSynch(d.getId())){
            String serverAction=diagramSynchDao.getServerAction(d.getId());
            resolution="CLIENT-CHOICE";
            choices="C-"+clientAction+"|"+"S-"+serverAction;

        }
        else{
            resolution=clientAction;
        }

    }


}
