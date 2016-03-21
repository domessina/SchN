package be.beme.schn.spring;

/**
 * Created by Dorito on 17-03-16.
 */


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloController {

    /*@RequestMapping(method = RequestMethod.GET, value = "/helloworld")
    public String greeting() {
      //  model.addAttribute("name", name);
        return "embedded";
    }*/

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

}