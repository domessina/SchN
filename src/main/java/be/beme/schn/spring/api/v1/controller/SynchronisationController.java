package be.beme.schn.spring.api.v1.controller;

import be.beme.schn.narrative.component.Diagram;
import be.beme.schn.narrative.component.NarrativeComponent;
import be.beme.schn.persistence.dao.DiagramDao;
import be.beme.schn.persistence.dao.NarrativeComponentDao;
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
@RequestMapping("/v1/api/sync/{userId}")
public class SynchronisationController extends AbstractController {

    @Autowired
    DiagramDao dao;


    @RequestMapping(value="/pushDiagram", method = RequestMethod.POST)
    public ResponseEntity<ActionDoneResponse> pushDiagrams(@RequestBody Diagram diagram, @RequestParam String action){

        ActionDoneResponse response=new ActionDoneResponse();

        //created on renvoie l'id diagram serveur et que c'est accepté
        if(action.equals("CREATE")){
            response.serverId=dao.create(diagram);
            response.action="CREATED";
            dao.setNeedSynch(false,response.serverId);
        }
        else if(action.equals("UPDATE")||action.equals("DELETE")){
            ConflictResolver filter= new ConflictResolver();
            filter.resolve(diagram,"diagram",action);
            response.action=filter.resolution;

            //user-choice on renvoie les choix
            if(response.action.equals("CLIENT-CHOICE")){
                response.choices=filter.choices;
                return new ResponseEntity<>(response,HttpStatus.CONFLICT);
            }

            //updated on renvoie que c'est accepté
            else if(response.action.equals("UPDATE")){
                dao.update(diagram);
            }
            //deleted on ne renvoie que c'est accepté
            else if(response.action.equals("DELETE")){
                dao.delete(diagram.getId());
            }
        }
        //no need to set clientId in the response because client alredy has it, and wait for server response
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }


    @RequestMapping(value="/performUserChoice", method = RequestMethod.POST)
    public ResponseEntity<Diagram> performUserChoice(@RequestBody Diagram diagram, @RequestParam String clientAction){

        Diagram dResponse=new ClientChoicePerformer(diagram,clientAction).perform();
        dao.setNeedSynch(false,diagram.getId());
        return new ResponseEntity<>(dResponse, HttpStatus.OK);
    }

    @RequestMapping(value="/pullDiagrams",method = RequestMethod.GET)
    public ResponseEntity<?> pullDiagrams(@RequestParam int userId){
        //chercher ceux au need synch = true  et action = create en stipulant l'userid. pou rça créer une méthode dans diagramDao
        //cherche ceux au need synch = ture et action = update...etc
        //cgercge need synch true et acitn= dlete
        //rnvoyer ce trois listes
        //mais comme je ne sais pas comment je désérialiserait ça en java coté andro, je le ferai plus tard
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









