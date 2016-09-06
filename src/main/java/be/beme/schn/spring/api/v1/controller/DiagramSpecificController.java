package be.beme.schn.spring.api.v1.controller;

import be.beme.schn.narrative.component.Diagram;
import be.beme.schn.persistence.dao.DiagramDao;
import be.beme.schn.persistence.dao.synch.DiagramSynchDao;
import be.beme.schn.spring.api.v1.exception.BadParamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/**
 * Created by Dotista on 18-05-16.
 */
@RestController
@RequestMapping("/v1/api/nc/diagram")
public class DiagramSpecificController extends AbstractController {

    @Autowired
    DiagramDao dao;
    @Autowired
    DiagramSynchDao synchDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Diagram> get(@PathVariable int id)
    {

        Diagram component;
        try {
            component = dao.getNComponent(id);
        }
        catch(EmptyResultDataAccessException e)
        {
            throw new BadParamException(messageSource.getMessage("bad.id.message",new Object[]{id}, Locale.ENGLISH));
        }

        return new ResponseEntity<>(component, HttpStatus.OK);
    }


    /*@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<IdResponse> newFromClient(@RequestParam Diagram diagramClient){

        IdResponse idSrv =new IdResponse(diagramDao.create(diagramClient), NarrativeComponentType.NC_Diagram.toString());
        synchDao.setIdClientServer(diagramClient.getId(),idSrv.id);
        return new ResponseEntity<>(idSrv,HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Diagram>> newFromServer(@RequestParam int userId){
        List<Diagram> diagrams=synchDao.newFromServer(userId);
        return new ResponseEntity<>(diagrams, HttpStatus.OK);
    }*/
}
