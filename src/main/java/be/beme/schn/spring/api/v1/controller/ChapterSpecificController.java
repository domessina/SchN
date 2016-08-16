package be.beme.schn.spring.api.v1.controller;

import be.beme.schn.narrative.component.Chapter;
import be.beme.schn.persistence.dao.ChapterDao;
import be.beme.schn.spring.api.v1.exception.BadParamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

/**
 * Created by Dotista on 18-05-16.
 */
@RestController
@RequestMapping("/v1/api/nc/chapter")
public class ChapterSpecificController extends AbstractController {

    @Autowired
    ChapterDao chapterService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Chapter> get(@PathVariable int id)
    {

        Chapter component;
        try {
            component = chapterService.getNComponent(id);
        }
        catch(EmptyResultDataAccessException e)
        {
            throw new BadParamException(messageSource.getMessage("bad.id.message",new Object[]{id}, Locale.ENGLISH));
        }

        return new ResponseEntity<>(component, HttpStatus.OK);
    }



}
