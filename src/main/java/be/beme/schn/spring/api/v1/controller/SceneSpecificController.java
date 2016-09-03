package be.beme.schn.spring.api.v1.controller;

import be.beme.schn.narrative.component.Scene;
import be.beme.schn.persistence.dao.SceneDao;
import be.beme.schn.spring.api.v1.exception.BadParamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

/**
 * Created by Dotista on 16-05-16.
 */
@RestController
@RequestMapping("/v1/api/nc/scene")
public class SceneSpecificController extends AbstractController {



    @Autowired
    SceneDao dao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Scene> get(@PathVariable int id)
    {

        Scene component;
        try {
            component = dao.getNComponent(id);
        }
        catch(EmptyResultDataAccessException e)
        {
            throw new BadParamException(messageSource.getMessage("bad.id.message",new Object[]{id}, Locale.ENGLISH));
        }

        return new ResponseEntity<>(component, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Scene>> getAllScenesByChapter(@RequestParam(value = "chapterId") int id)
    {
        List<Scene> scenes= dao.getAllScenesByChapter(id);
        return new ResponseEntity<>(scenes, HttpStatus.OK);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<Scene>> getAllScenesByDiagram(@RequestParam int diagramId)
    {

        List<Scene> scenes;
        try {
            scenes = dao.getAllScenesByDiagram(diagramId);
        }
        catch(EmptyResultDataAccessException e)
        {
            throw new BadParamException(messageSource.getMessage("bad.id.message",new Object[]{diagramId}, Locale.ENGLISH));
        }

        return new ResponseEntity<>(scenes, HttpStatus.OK);
    }


}