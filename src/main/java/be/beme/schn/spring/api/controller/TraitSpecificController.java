package be.beme.schn.spring.api.controller;

import be.beme.schn.persistence.dao.TraitDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Dotista on 18-05-16.
 */
@RestController
@RequestMapping("/api/trait")
public class TraitSpecificController extends AbstractController {

    @Autowired
    TraitDao traitService;
}