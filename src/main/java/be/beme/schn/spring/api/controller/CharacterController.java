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


}
