package be.beme.schn.spring;

import com.vaadin.spring.annotation.EnableVaadin;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by Dorito on 17-03-16.
 */
@org.springframework.context.annotation.Configuration
@EnableVaadin                      //importe VaadinConfiguration
@PropertySource("classpath:application.properties")
public  class AppConfiguration {

   /*  @Value("${jdbc.username}")                                                                                        fonctionne que si pas dans une classe de configuration
    private String user;*/


    @Bean
    public MessageSource messageSource(){
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource(); //TODO créer un bean conflict checker, pour la scynch, il gère en fait le passage dans la chaine...
        messageSource.setBasename("i18n/messages");
        return messageSource;
    }
}
