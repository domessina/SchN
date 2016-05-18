package be.beme.schn.spring.api.controller;

import be.beme.schn.narrative.component.Chapter;
import be.beme.schn.narrative.component.Character;
import be.beme.schn.narrative.component.NarrativeComponent;
import be.beme.schn.persistence.dao.NarrativeComponentDao;
import be.beme.schn.spring.api.NarrativeComponentDaoRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Created by Dotista on 18-05-16.
 */
@RestController
@RequestMapping("/api/{type}")
public class NarrativeComponentController extends AbstractController{

    @Autowired
    private NarrativeComponentDaoRegistry daoRegistry;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<NarrativeComponent> getCharacter(@PathVariable String type,@PathVariable int id)
    {
        NarrativeComponentDao service=daoRegistry.getDao(type);
        NarrativeComponent component=service.getNComponent(id);

        return new ResponseEntity<>(component, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.PUT) //empecher de changer l'id, le diagramId
    public ResponseEntity<?> updateCharacter(@PathVariable String type,@RequestBody NarrativeComponent component)
    {
        NarrativeComponentDao service=daoRegistry.getDao(type);
        service.update(component);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST)//dire si existe deja
    public ResponseEntity<NarrativeComponent> createCharacter(@PathVariable String type,@RequestBody NarrativeComponent component)
    {
        NarrativeComponentDao service=daoRegistry.getDao(type);
        int id=service.create(component);
        component.setId(id);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCharURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id).toUri();
        responseHeaders.setLocation(newCharURI);
        return new ResponseEntity<>(component,responseHeaders, HttpStatus.CREATED);
    }


    @RequestMapping(value ="/{id}",method = RequestMethod.DELETE)  //catcher l'excpetion de si il existe pas
    public ResponseEntity<?> updateCharacter(@PathVariable String type,@PathVariable int id)
    {
        NarrativeComponentDao service=daoRegistry.getDao(type);
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
