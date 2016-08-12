package be.beme.schn.spring.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestMapping;



//gerer ici le cache (voir Abstractcontroller swelly)

//@RequestMapping("/v2/api")
/**
 * Created by Dotista on 16-05-16.
 */
public abstract class AbstractController {

    @Autowired
    protected MessageSource messageSource;
}
