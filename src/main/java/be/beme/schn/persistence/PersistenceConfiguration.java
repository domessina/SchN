package be.beme.schn.persistence;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by Dotista on 15-08-16.
 */
@Configuration
public class PersistenceConfiguration {

    //envr is used by dataSource, so it have to be defined before it
    @Autowired
    private Environment environment;

    @Autowired
    private PGSimpleDataSource dataSource;

    @Bean
    //seen by breakpoint=> spring creates first dataSource, because jdbcTemplate need it
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
}
