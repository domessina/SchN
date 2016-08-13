package be.beme.schn.spring.api.controller.v1;

import be.beme.schn.narrative.component.Character;
import be.beme.schn.persistence.dao.CharacterDao;
import be.beme.schn.spring.api.controller.AbstractController;
import be.beme.schn.spring.api.exception.BadParamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

/**
 * Created by Dotista on 16-05-16.
 */
@RestController
@RequestMapping("/v1/api/nc/character")
public class CharacterSpecificController extends AbstractController {

    @Autowired
    CharacterDao dao;


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Character> get(@PathVariable int id)
    {

        Character component;
        try {
            component = dao.getNComponent(id);
        }
        catch(EmptyResultDataAccessException e)
        {
            throw new BadParamException(messageSource.getMessage("bad.id.message",new Object[]{id}, Locale.ENGLISH));
        }

        return new ResponseEntity<>(component, HttpStatus.OK);
    }

}
