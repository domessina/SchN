package be.beme.schn.spring.api.v1.configuration;

import be.beme.schn.spring.api.v1.interceptor.NComponentBadTypeInterceptor;
import be.beme.schn.spring.api.v1.jackson.JsonViewResolver;
import com.vaadin.spring.annotation.VaadinSessionScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import java.util.Arrays;

/**
 * Created by Dotista on 16-05-16.
 */
@Configuration
@EnableWebMvc                                                                                                           //pour importer la configuration bewmvc pour injecter dans les paramètres de nos méhtodes
//@VaadinSessionScope :-) for info
public class ApiConfiguration extends WebMvcConfigurerAdapter {


    /*
  * Configure ContentNegotiationManager
  * URL extension to help determine the media types. Also, we have set the default media type to TEXT_HTML
  * in absence of file extension or when the filetype is unknown, that means JSP view resolver will be used when no [known] URL extension found.
  */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.ignoreAcceptHeader(true).defaultContentType(
                MediaType.APPLICATION_JSON);
    }

    /*
     * Configure ContentNegotiatingViewResolver
     */
    @Bean
    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(manager);
        resolver.setViewResolvers(Arrays.asList(jsonViewResolver()));                                                   //possibilité de problèeme  1
        return resolver;
    }


    /*
     * Configure View resolver to provide JSON output using JACKSON library to
     * convert object in JSON format.
     */
    @Bean
    public ViewResolver jsonViewResolver() {
        return new JsonViewResolver();
    }


    /*
    * Enables cors specification for swagger-ui requests
    * */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/v2/api-docs");
        registry.addMapping("/api*");
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new NComponentBadTypeInterceptor()).addPathPatterns("/controller/api/nc/**");
    }

}
