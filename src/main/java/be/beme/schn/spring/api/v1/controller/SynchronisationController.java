package be.beme.schn.spring.api.v1.controller;

import be.beme.schn.FileUtil;
import be.beme.schn.narrative.component.Diagram;
import be.beme.schn.narrative.component.NarrativeComponent;
import be.beme.schn.persistence.dao.DiagramDao;
import be.beme.schn.persistence.dao.UserDao;
import be.beme.schn.spring.api.v1.interceptor.ClientChoicePerformer;
import be.beme.schn.spring.api.v1.interceptor.ConflictResolver;
import be.beme.schn.spring.api.v1.response.ActionDoneResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Dorito on 26-08-16.
 */


@RestController
@RequestMapping("/v1/api/sync")
public class SynchronisationController extends AbstractController {

    @Autowired
    DiagramDao diagramDao;

    @Autowired
    UserDao userDao;

    @Autowired
    ConflictResolver resolver;

    @Autowired
    ClientChoicePerformer cCPerformer;



    @RequestMapping(value="/pushUserChoice", method= RequestMethod.POST)
    public ResponseEntity<Diagram> test(@RequestBody Diagram diagram,@RequestParam String action){
        Diagram dResponse=cCPerformer.perform(diagram,action);
        diagramDao.setNeedSynch(false,diagram.getId());
        return new ResponseEntity<>(dResponse,HttpStatus.ACCEPTED);
    }

    @RequestMapping(value="/pushDiagram", method = RequestMethod.POST)
    public ResponseEntity<ActionDoneResponse> pushDiagrams(@RequestBody Diagram diagram, @RequestParam String action){

        ActionDoneResponse response=new ActionDoneResponse();

        //created on renvoie l'id diagram serveur et que c'est accepté
        if(action.equals("CREATE")||(diagram.getId()==-1&&action.equals("UPDATE"))){
            response.serverId= diagramDao.create(diagram);
            response.action=action;
            FileUtil.createDiagramTree(diagram.getUser_id(),response.serverId);
            diagramDao.createDiagramToSynch(diagram.getUser_id(),response.serverId);
            diagramDao.setNeedSynch(false,response.serverId);

            if(userDao.getNbDiagrams(diagram.getUser_id())==0){
                userDao.setActualDiagram(diagram.getUser_id(), response.serverId);
            }
            userDao.increaseNumberOfDiagrams(diagram.getUser_id());
        }
        if(action.equals("UPDATE")||action.equals("DELETE")){

            resolver.resolve(diagram,"diagram",action);
            response.action=resolver.resolution;
            if(diagram.getId()!=-1)
                response.serverId= diagram.getId();

            //user-choice on renvoie les choix
            if(response.action.equals("CLIENT-CHOICE")){
                response.choices=resolver.choices;
                return new ResponseEntity<>(response,HttpStatus.CONFLICT);
            }

            //updated on renvoie que c'est accepté
            else if(response.action.equals("UPDATE")){
                diagramDao.update(diagram);
            }
            //deleted on ne renvoie que c'est accepté
            else if(response.action.equals("DELETE")){
                diagramDao.delete(diagram.getId());
                FileUtil.deleteDiagramTree(diagram.getUser_id(),diagram.getUser_id());
                userDao.decreaseNumberOfDiagrams(diagram.getUser_id());
            }
        }
        //no need to set clientId in the response because client alredy has it, and wait for server response
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }




    @RequestMapping(value="/pullDiagrams",method = RequestMethod.GET)
    public ResponseEntity<List<Diagram>> pullDiagrams(@RequestParam int userId, @RequestParam String action){


        //chercher ceux au need synch = true  et action = create en stipulant l'userid. pou rça créer une méthode dans diagramDao
        //cherche ceux au need synch = ture et action = update...etc
        //cgercge need synch true et acitn= dlete
        //rnvoyer ce trois listes
        //mais comme je ne sais pas comment je désérialiserait ça en java coté andro, je le ferai plus tard

        return null;
    }


    //---------------------------------------------------

    @RequestMapping(value="/pushComponent",method = RequestMethod.POST)
    public ResponseEntity<ActionDoneResponse> pushComponent(@RequestParam String type,@RequestBody NarrativeComponent component, @RequestParam String clientAction)
    {
        ConflictResolver resolver=new ConflictResolver();
        resolver.resolve(component,type,clientAction);
        ActionDoneResponse response= new ActionDoneResponse();
        response.action=resolver.resolution;

        return new ResponseEntity<>(response,HttpStatus.OK);
    }



}









