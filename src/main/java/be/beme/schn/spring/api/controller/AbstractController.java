package be.beme.schn.spring.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Dotista on 16-05-16.
 */

//gerer ici les exception handlers et le cache (voir Abstractcontroller swelly)


public abstract class AbstractController {

    @Autowired
    MessageSource messageSource;
}
