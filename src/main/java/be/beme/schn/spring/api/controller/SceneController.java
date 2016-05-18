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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Scene> getScene(@PathVariable int id)
    {
        Scene scene= sceneService.getScene(id);
        return new ResponseEntity<>(scene, HttpStatus.OK);
    }



    @RequestMapping(method = RequestMethod.POST)//faire en sorte que quelque soit la place stipulée, c'est la derniere
    public ResponseEntity<Scene> createScene(@RequestBody Scene scene) {
        int id = sceneService.create(scene);
        scene.setId(id);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCharURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id).toUri();
        responseHeaders.setLocation(newCharURI);
        return new ResponseEntity<>(scene,responseHeaders, HttpStatus.CREATED);
    }



   @RequestMapping(value = "/{id}", method = RequestMethod.PUT) //empecher de changer l'id, le diagramId
    public ResponseEntity<?> updateScene(@RequestBody Scene scene) {
       sceneService.update(scene);
        return new ResponseEntity<>(HttpStatus.OK);
    }




    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)  //catcher l'excpetion de si il existe pas
    public ResponseEntity<?> deleteScene(@PathVariable int id) {
       sceneService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}