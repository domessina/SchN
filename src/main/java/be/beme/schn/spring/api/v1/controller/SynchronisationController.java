package be.beme.schn.spring.api.v1.controller;

import be.beme.schn.narrative.component.Diagram;
import be.beme.schn.persistence.dao.DiagramDao;
import be.beme.schn.spring.api.v1.interceptor.ClientChoicePerformer;
import be.beme.schn.spring.api.v1.interceptor.ConflictResolver;
import be.beme.schn.spring.api.v1.response.ActionDoneResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Dorito on 26-08-16.
 */


@RestController
@RequestMapping("/v1/api/sync/{userId}")
public class SynchronisationController extends AbstractController {

    @Autowired
    DiagramDao dao;


    @RequestMapping(value="/pushDiagrams", method = RequestMethod.POST)
    public ResponseEntity<ActionDoneResponse> pushDiagrams(@RequestBody Diagram diagram, @RequestParam String action){

        ActionDoneResponse reponse=new ActionDoneResponse();

        //created on renvoie l'id diagram serveur et que c'est accepté
        if(action.equals("CREATE")){
            reponse.serverId=dao.create(diagram);
            reponse.action="CREATED";
        }
        else if(action.equals("UPDATE")||action.equals("DELETE")){
            ConflictResolver filter= new ConflictResolver(diagram,action);
            filter.resolve();
            reponse.action=filter.resolution;

            //user-choice on renvoie les choix
            if(reponse.action.equals("CLIENT-CHOICE")){
                reponse.choices=filter.choices;
                return new ResponseEntity<>(reponse,HttpStatus.CONFLICT);
            }

            //updated on renvoie que c'est accepté

            //deleted on ne renvoie que c'est accepté
        }
        //no need to set clientId in the response because client alredy has it, and wait for server response
        return new ResponseEntity<>(reponse, HttpStatus.ACCEPTED);
    }


    @RequestMapping(value="/performUserChoice", method = RequestMethod.POST)
    public ResponseEntity<Diagram> performUserChoice(@RequestBody Diagram diagramClient, @RequestParam String clientAction){

        Diagram dResponse=new ClientChoicePerformer(diagramClient,clientAction).perform();
        return new ResponseEntity<>(dResponse, HttpStatus.OK);
    }
    /*@RequestMapping(value="/pushChapters", method = RequestMethod.POST)
    public ResponseEntity<ActionDoneResponse> pushDiagrams(@RequestBody Chapter chapter, @RequestParam String action){



    }*/
}









