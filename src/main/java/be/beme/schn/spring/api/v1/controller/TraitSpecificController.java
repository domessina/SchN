package be.beme.schn.spring.api.v1.controller;

import be.beme.schn.narrative.component.Trait;
import be.beme.schn.persistence.dao.TraitDao;
import be.beme.schn.spring.api.v1.exception.BadParamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

/**
 * Created by Dotista on 18-05-16.
 */
@RestController
@RequestMapping("/v1/api/nc/trait")
public class TraitSpecificController extends AbstractController {

    @Autowired
    TraitDao dao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Trait> get(@PathVariable int id)
    {

        Trait component;
        try {
            component = dao.getNComponent(id);
        }
        catch(EmptyResultDataAccessException e)
        {
            throw new BadParamException(messageSource.getMessage("bad.id.message",new Object[]{id}, Locale.ENGLISH));
        }

        return new ResponseEntity<>(component, HttpStatus.OK);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<Trait>> getAllTraitsByDiagram(@RequestParam int diagramId)
    {

        List<Trait> traits;
        try {
            traits = dao.getAllTraitsByDiagram(diagramId);
        }
        catch(EmptyResultDataAccessException e)
        {
            throw new BadParamException(messageSource.getMessage("bad.id.message",new Object[]{diagramId}, Locale.ENGLISH));
        }

        return new ResponseEntity<>(traits, HttpStatus.OK);
    }

}
