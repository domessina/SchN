package be.beme.schn.spring.api.v1.interceptor;

import be.beme.schn.narrative.component.Diagram;
import be.beme.schn.persistence.dao.DiagramDao;
import be.beme.schn.persistence.daoimpl.synch.DiagramSynchDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Dorito on 26-08-16.
 */
@Component
public class ClientChoicePerformer {


    private String selectedAction;
    @Autowired
    private DiagramDao dao;
    @Autowired
    private DiagramSynchDaoImpl synchDao;
    private Diagram diagramClient;
    private Diagram diagramResponse;



    public Diagram perform(Diagram dClient, String selectedAction){

        this.diagramClient=dClient;
        this.selectedAction=selectedAction;
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
//        FileUtil.deleteDiagramTree(diagramResponse.getUserId(),diagramClient.getId());
        diagramResponse=null;
    }


    private void cDelete(){
        dao.delete(diagramClient.getId());
//        FileUtil.deleteDiagramTree(diagramClient.getUserId(),diagramResponse.getId());
        diagramResponse=null;

    }

    private void cUpdate(){
        dao.update(diagramClient);
        //in case of c-update:s-delete
        dao.setDiagramEnabled(diagramClient.getId(),true);
        diagramResponse=null;
    }

    private void sUpdate(){
        diagramResponse =dao.getDiagramById(diagramClient.getId());
    }


}
