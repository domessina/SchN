package be.beme.schn.spring.api.v1.interceptor;

import be.beme.schn.narrative.component.NarrativeComponent;
import be.beme.schn.persistence.dao.NarrativeComponentDao;
import be.beme.schn.persistence.daoimpl.synch.DiagramSynchDaoImpl;
import be.beme.schn.spring.api.v1.narrative.NarrativeComponentDaoRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Dorito on 26-08-16.
 */
@Component
public class ConflictResolver {


    public String resolution;
    public String choices;
    private NarrativeComponent nc;
    private DiagramSynchDaoImpl diagramSynchDao;

    private NarrativeComponent responsenc;//atention pour ça faudra remettre le deserialisation json pour narrative compoenent

    @Autowired
    private NarrativeComponentDaoRegistry daoRegistry;

    public ConflictResolver()
    {

    }

    public void resolve(NarrativeComponent nc,String type, String clientAction){
        this.nc=nc;

        if(type.equals("diagram")){
            diagramType(clientAction);
        }
        else{
            otherType(type,clientAction);
        }
    }

    private void otherType(String type, String clientAction){

        NarrativeComponentDao service=daoRegistry.getDao(type);

        String typeCap=capitalize(type);
        int dId=diagramSynchDao.getDiagramId(typeCap,nc.getId());

//        if(diagramSynchDao.isComponentToSynch(typeCap,type,nc.getId()))
        if(diagramSynchDao.isDiagramToSynch(dId))
        {
            //serverid

            String lastAction=diagramSynchDao.getLastSelectedAction(dId);
            switch (lastAction){
                case "S-DELETE":  {
                    service.delete(nc.getId());
                    break;
                }
                case "C-DELETE":  {
                    service.delete(nc.getId());
                    break;
                }
                case "C-UPDATE":  {
                    service.update(nc);
                    break;
                }
                case "S-UPDATE": {
                    service.getNComponent(nc.getId());
                    break;
                }
            }
            resolution=lastAction;
        }
        else{
            resolution=clientAction;
        }
    }


    private void diagramType(String clientAction){

        //whether the diagram was already changed on server side since last synch

        if(diagramSynchDao.isDiagramToSynch(this.nc.getId())){
            String serverAction=diagramSynchDao.getServerAction(this.nc.getId());
            resolution="CLIENT-CHOICE";
            choices="C-"+clientAction+"|"+"S-"+serverAction;

            if(serverAction.equals("DELETE")&&serverAction.equals(clientAction)){
                resolution="DELETE";
            }

        }
        else{
            resolution=clientAction;
        }

    }




    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

}
