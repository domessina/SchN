package be.beme.schn.spring.api;


import be.beme.schn.narrative.component.E_NarrativeComponent;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by Dotista on 30-06-16.
 *
 * Returns 404 if writing error in url for character, scene, diagram, trait, chapter, all preceded by "/nc/"
 * Because these types are considered as path variables and not uri segments, a writing error throws specifics exceptions
 *
 */
public class NComponentBadTypeInterceptor extends HandlerInterceptorAdapter {
    int ac=4;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    {
       String uri = request.getRequestURI();

        for(E_NarrativeComponent nc:E_NarrativeComponent.values())
        {
            if(uri.contains(nc.getType()))
            {
                return true;                                            //then, it proceed with the next interceptor in the chain
            }
        }
        response.setStatus(405); //it'e 405 and not 404.
        return false;

    }
}
