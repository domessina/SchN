package be.beme.schn.spring.api.v1.controller;

import be.beme.schn.narrative.component.NarrativeComponent;
import be.beme.schn.persistence.dao.NarrativeComponentDao;
import be.beme.schn.spring.api.v1.exception.BadParamException;
import be.beme.schn.spring.api.v1.narrative.NarrativeComponentDaoRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Locale;

/**
 * Created by Dotista on 18-05-16.
 */
@RestController
@RequestMapping("/v1/api/nc/{type}")
public class NarrativeComponentController extends AbstractController {

    @Autowired
    private NarrativeComponentDaoRegistry daoRegistry;

@RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Integer> create(@PathVariable String type, @RequestBody NarrativeComponent component)
    {
        NarrativeComponentDao service=daoRegistry.getDao(type);
        int responseId=service.create(component);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCharURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseId).toUri();
        responseHeaders.setLocation(newCharURI);
        return new ResponseEntity<>(responseId,responseHeaders, HttpStatus.CREATED);
    }


    @RequestMapping(method = RequestMethod.PUT) //empecher de changer l'id, le diagramId
    public ResponseEntity<?> update(@PathVariable String type,@RequestBody NarrativeComponent component)
    {
        NarrativeComponentDao service=daoRegistry.getDao(type);
        if(service.update(component)==0)
        {
            throw new BadParamException(messageSource.getMessage("bad.id.message",new Object[]{component.getId()}, Locale.ENGLISH));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO tous le snarrative compoenent doivent avoir un diagramId variable pour qu'ainsi via l'api qqn ne peut aps put un trait ) un perso qui n'es pas dans on diagram

    //THE DELETE METHOD IS UNUSED
    @RequestMapping(value ="/{id}",method = RequestMethod.DELETE)  //catcher l'excpetion de si il existe pas
    public ResponseEntity<?> delete(@PathVariable String type,@PathVariable int id)
    {
        NarrativeComponentDao service=daoRegistry.getDao(type);
        if(service.delete(id)==0)                                       //if jdbc says no row was affected
        {
            throw new BadParamException(messageSource.getMessage("bad.id.message",new Object[]{id}, Locale.ENGLISH));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
