package be.beme.schn.spring.api.controller;

import be.beme.schn.narrative.component.Chapter;
import be.beme.schn.persistence.dao.ChapterDao;
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
@RequestMapping("/api/chapter")
public class ChapterController extends AbstractController {

    @Autowired
    ChapterDao chapterService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Chapter> getScene(@PathVariable int id)
    {
        Chapter chapter= chapterService.getChapterById(id);
        return new ResponseEntity<>(chapter, HttpStatus.OK);
    }



    @RequestMapping(method = RequestMethod.POST)//faire en sorte que quelque soit la place stipulée, c'est la derniere
    public ResponseEntity<Chapter> createScene(@RequestBody Chapter chapter) {
        int id = chapterService.create(chapter);
        chapter.setId(id);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCharURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id).toUri();
        responseHeaders.setLocation(newCharURI);
        return new ResponseEntity<>(chapter,responseHeaders, HttpStatus.CREATED);
    }



    @RequestMapping(value = "/{id}", method = RequestMethod.PUT) //empecher de changer l'id, le diagramId
    public ResponseEntity<?> updateScene(@RequestBody Chapter chapter) {
        chapterService.update(chapter);
        return new ResponseEntity<>(HttpStatus.OK);
    }




    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)  //catcher l'excpetion de si il existe pas
    public ResponseEntity<?> deleteScene(@PathVariable int id) {
        chapterService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
