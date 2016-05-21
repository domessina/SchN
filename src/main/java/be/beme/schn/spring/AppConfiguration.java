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

                                                                                                                         //envr is used by dataSource, so it have to be defined before it
    @Autowired
    private Environment environment;

    @Autowired
    private PGSimpleDataSource dataSource;

  /*  @Value("${jdbc.username}")                                                                                        fonctionne que si pas dans une classe de configuration
    private String user;*/



    @Bean                                                                                                                //seen by breakpoint; spring creates first dataSource, because jdbcTemplate need it
    public JdbcTemplate jdbcTemplate()
    {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public PGSimpleDataSource getDataSource(){
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(environment.getProperty("jdbc.url",String.class));
        dataSource.setUser(environment.getProperty("jdbc.username",String.class));
        dataSource.setPassword(environment.getProperty("jdbc.password",String.class));
        return dataSource;
    }

    @Bean
    public MessageSource messageSource(){
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n/messages");
        return messageSource;
    }
}
