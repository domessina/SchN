package be.beme.schn.spring.api;

import java.util.Locale;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * Created by Dotista on 16-05-16.
 */
public class JsonViewResolver implements ViewResolver{

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setPrettyPrint(true);          //TODO if production mode , set it to false to economise number of packets sent
        return view;
    }

}