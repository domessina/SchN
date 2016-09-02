package be.beme.schn.spring.api.v1.interceptor;

import be.beme.schn.narrative.component.Diagram;
import be.beme.schn.persistence.daoimpl.DiagramDaoImpl;
import be.beme.schn.persistence.daoimpl.synch.DiagramSynchDaoImpl;

/**
 * Created by Dorito on 26-08-16.
 */
public class ClientChoicePerformer {


    private String selectedAction;
    private DiagramDaoImpl dao;
    private DiagramSynchDaoImpl synchDao;
    private Diagram diagramClient;
    private Diagram diagramResponse;

    public ClientChoicePerformer(Diagram dClient, String selectedAction){
        dao=new DiagramDaoImpl();
        synchDao=new DiagramSynchDaoImpl();
        this.diagramClient=dClient;
        this.selectedAction=selectedAction;
    }

    public Diagram perform(){
        synchDao.setLastSelectedAction(selectedAction,diagramClient.getId());
        switch (selectedAction){
            case "S-DELETE":  sDelete();     break;
            case "C-DELETE":  cDelete();      break;
            case "C-UPDATE":  cUpdate() ;     break;
            case "S-UPDATE":  sUpdate();    break;
        }
        return diagramResponse;
    }


    private void sDelete(){
        dao.delete(diagramClient.getId());
        diagramResponse=null;
    }


    private void cDelete(){
        dao.delete(diagramClient.getId());
        diagramResponse=null;

    }

    private void cUpdate(){
        dao.update(diagramClient);
        diagramResponse=null;
    }

    private void sUpdate(){
        diagramResponse =dao.getDiagramById(diagramClient.getId());
    }


}
