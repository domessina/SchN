package be.beme.schn.spring.api;

import be.beme.schn.narrative.component.Character;
import be.beme.schn.persistence.dao.CharacterDao;
import be.beme.schn.persistence.dao.TraitDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Dotista on 16-05-16.
 */
@RestController
@RequestMapping("/api")
public class ApiController extends AbstractApiController{

    @Autowired
    CharacterDao characterService;

    @Autowired
    TraitDao traitService;

    @RequestMapping(value = "/character/{id}", method = RequestMethod.GET)
    public ResponseEntity<Character> getCharacter(@PathVariable int id)
    {
        Character character = characterService.getCharacterById(id);
        character.setTraits(traitService.getTraitsByCharacter(id));
        return new ResponseEntity<>(character, HttpStatus.OK);
    }

}
