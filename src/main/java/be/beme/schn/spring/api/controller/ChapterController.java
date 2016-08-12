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

}
