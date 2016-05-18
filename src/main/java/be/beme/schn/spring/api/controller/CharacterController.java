package be.beme.schn.spring.api.controller;

import be.beme.schn.narrative.component.Character;
import be.beme.schn.persistence.dao.CharacterDao;
import be.beme.schn.persistence.dao.TraitDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Created by Dotista on 16-05-16.
 */
@RestController
@RequestMapping("/api/character")
public class CharacterController extends AbstractController {

    @Autowired
    CharacterDao characterService;


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Character> getCharacter(@PathVariable int id)
    {
        Character character = characterService.getCharacterById(id);
        return new ResponseEntity<>(character, HttpStatus.OK);
    }


    @RequestMapping(value ="/{id}",method = RequestMethod.PUT) //empecher de changer l'id, le diagramId
    public ResponseEntity<?> updateCharacter(@RequestBody Character character)
    {
//        characterService.exist(character.getName(),character.getDiagram_id());
       characterService.update(character);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST)//dire si existe deja
    public ResponseEntity<Character> createCharacter(@RequestBody Character character)
    {
        int id=characterService.create(character);
        character.setId(id);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCharURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id).toUri();
        responseHeaders.setLocation(newCharURI);
        return new ResponseEntity<>(character,responseHeaders, HttpStatus.CREATED);
    }


    @RequestMapping(value ="/{id}",method = RequestMethod.DELETE)  //catcher l'excpetion de si il existe pas
    public ResponseEntity<?> updateCharacter(@PathVariable int id)
    {
        characterService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
