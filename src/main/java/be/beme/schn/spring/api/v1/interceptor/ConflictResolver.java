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
    private ChapterSynchDaoImpl chapterSynchDao;
    private SceneSynchDaoImpl sceneDynchDao;
    private CharacterSynchDaoImpl characterSynchDao;
    private TraitSynchDaoImpl traitSynchDao;
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

        type=capitalize(type);
        if(diagramSynchDao.isComponentToSynch(type,nc.getId()))
        {
            //serverid
            int dId=diagramSynchDao.getDiagramId(type,nc.getId());
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
                    service.getNComponent(nc.getId());//attention c id serveur qu on a besoin, du coup faut tout faire avec id serveur
                    break;
                }
            }
        }
    }


    private void diagramType(String clientAction){

        //whether the diagram was already changed on server side since last synch

        if(diagramSynchDao.isDiagramToSynch(this.nc.getId())){
            String serverAction=diagramSynchDao.getServerAction(this.nc.getId());
            resolution="CLIENT-CHOICE";
            choices="C-"+clientAction+"|"+"S-"+serverAction;

        }
        else{
            resolution=clientAction;
        }

    }



    private void cDelete(String type){
        NarrativeComponentDao service=daoRegistry.getDao(type);

        int srvId=dao.getIdFromClientId(diagramClient.getId());
        dao.delete(srvId);
        diagramResponse=null;

    }

    private void cUpdate(String type){
        NarrativeComponentDao service=daoRegistry.getDao(type);

        dao.update(diagramClient);
        diagramResponse=null;
    }

    private void sUpdate(String typz){
        NarrativeComponentDao service=daoRegistry.getDao(type);

        diagramResponse =dao.getDiagramByClientId(diagramClient.getId());
    }


    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

}
