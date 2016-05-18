package be.beme.schn.spring.api.controller;

import be.beme.schn.narrative.component.Scene;
import be.beme.schn.persistence.dao.SceneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Created by Dotista on 16-05-16.
 */
@RestController
@RequestMapping("/api/scene")
public class SceneController extends AbstractController {

    //TODO regarder si liens code commun avec les autres narrativecompoenents pour factoriser le code


    @Autowired
    SceneDao sceneService;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Scene>> getAllScenesByChapter(@RequestParam(value = "chapterId") int id)
    {
        List<Scene> scenes= sceneService.getAllScenesByChapter(id);
        return new ResponseEntity<>(scenes, HttpStatus.OK);
    }

}